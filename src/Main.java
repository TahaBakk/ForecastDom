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

    public static void main(String[] args) {
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

                String velocitatkphf = String.valueOf(velocitatkph);

                //Aqui imprimim le dades
                System.out.println("La localització és: "+ nomLocalitzacio);
                System.out.println("La temperatura és: "+eLista.getElementsByTagName("temperature").item(0).getAttributes().getNamedItem("value").getTextContent()+" "+eLista.getElementsByTagName("temperature").item(0).getAttributes().getNamedItem("unit").getTextContent());
                System.out.println("El temps és: "+eLista.getElementsByTagName("symbol").item(0).getAttributes().getNamedItem("name").getTextContent());
                System.out.println("La velocitat en mps és: "+velocitat+" mps");
                System.out.println("La velocitat en kph és: "+velocitatkph+" kph");
                System.out.println("");
                System.out.println("");


                afegir_nouAttribut((Element) eLista.getElementsByTagName("windSpeed").item(0), "kph", velocitatkphf);


            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(db);
            StreamResult result = new StreamResult(new  File("ResultatDomForecast.xml"));
            transformer.transform(source, result);


        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }


    }
    //hem creat aquet apart per a poder afegir un nou atribut al windSpeed
    public static void afegir_nouAttribut(Element element, String name, String value){
        element.setAttribute(name, value);
    }

}
