import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

/**
 * Created by x3727349s on 17/10/16.
 */
public class Main {

    public static void main(String[] args) throws SAXException, IOException, TransformerConfigurationException, TransformerException {
        File inputFile = new File("forecast.xml");

        //factoria de constructor de documentos
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder dbBuilder = null;
            dbBuilder = dbFactory.newDocumentBuilder();

            Document db = dbBuilder.parse(inputFile);
            System.out.println("Root element : " + db.getDocumentElement().getNodeName());

            //Aquet NodeList el creem per extrure nomes el nom de la localització que es troba en l'apartat location
            NodeList lista = db.getElementsByTagName( "location" );

            //Aqui guardem el nom de la localitzqació en una varible
            Element namelista = (Element) lista.item(0);
            String nomLocalitzacio= namelista.getElementsByTagName("name").item(0).getTextContent();

            //Aquet NodeList el creem per extrure totes les dades que te l'apartat temps
            NodeList list = db.getElementsByTagName("time");


            //Recorrem tot el llistat y l'imprimim amb aquet for
            for (int i = 0; i < list.getLength(); i++) {

                Element eLista = (Element) list.item(i);

                //Això es per a saber la velocitat en mps i en mph
                double velocitat =Double.parseDouble(eLista.getElementsByTagName("windSpeed").item(0).getAttributes().getNamedItem("mps").getTextContent());
                double velocitatkph = velocitat*3.6;

                //pasem de double a string per a poder utilitzar la variable per a crear un nou valor al atribut
                String velocitatkphf = String.valueOf(velocitatkph);

                //comprobem el codi del temps i així al podem converti el nom de angles al català
                int codi = Integer.parseInt(eLista.getElementsByTagName("symbol").item(0).getAttributes().getNamedItem("number").getTextContent());
                String tempsString;
                switch (codi) {
                    case 500:  tempsString = "Pluja lleugera";
                        break;
                    case 800:  tempsString = "cel està clar";
                        break;
                    case 801:  tempsString = "pocs núvols";
                        break;
                    case 802:  tempsString = "núvols dispersos";
                        break;
                    case 803:  tempsString = "Ple de núvols dispersos";
                        break;
                    case 804:  tempsString = "cobert amb núvols";
                        break;
                    default: tempsString = "Error amb el codi";
                        break;
                }

                //Aqui imprimim le dades
                System.out.println("La localització és: "+ nomLocalitzacio);
                System.out.println("La temperatura és: "+eLista.getElementsByTagName("temperature").item(0).getAttributes().getNamedItem("value").getTextContent()+" "+eLista.getElementsByTagName("temperature").item(0).getAttributes().getNamedItem("unit").getTextContent());
                System.out.println("El temps és: "+tempsString);
                System.out.println("La velocitat en mps és: "+velocitat+" mps");
                System.out.println("La velocitat en kph és: "+velocitatkph+" kph");
                System.out.println("");
                System.out.println("");

                //cridem al metode  afegir_nouAttribut i li pasem le dades
                afegir_nouAttribut((Element) eLista.getElementsByTagName("windSpeed").item(0), "kph", velocitatkphf);


            }
            //Aquet apartat es per escriure totes le dades del xml al nou xml
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(db);//Li pasem el document que estem fent servir i es guarda en la variable source
            StreamResult result = new StreamResult(new  File("ResultatDomForecast.xml"));//li posem el nom del xml a crear i es guarda en la variable result
            transformer.transform(source, result);//Aqui li diem que el fitxer source el pasarem al result amb tots el canvis, el fitxer si existeix és sobreescriu.


        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }


    }
    //hem creat aquet apart per a poder afegir un nou atribut al windSpeed
    private static void afegir_nouAttribut(Element element, String name, String value){
        element.setAttribute(name, value);
    }

}
