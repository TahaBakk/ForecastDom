import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

/**
 * Created by x3727349s on 17/10/16.
 */
public class Main {

    public static void main(String[] args) {
        File inputFile = new File("biblio.xml");

        //factoria de constructor de documentos
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder dbBuilder = null;
            dbBuilder = dbFactory.newDocumentBuilder();

            Document db = dbBuilder.parse(inputFile);



            NodeList lista = db.getElementsByTagName( "time" );



            for (int i = 0; i < lista.getLength(); i++) {
                Element eLista = (Element) lista.item(i);


                System.out.println(eLista.getElementsByTagName());




                //System.out.println( lista.item(i).getTextContent() );
               /* System.out.println( "El eLista amb titol " + eLista.getElementsByTagName("titulo").item(0).getTextContent() +
                        " te com autor " + eLista.getElementsByTagName("autor").item(0).getTextContent() + " i va ser publicat l'any " +
                        eLista.getElementsByTagName("fecha").item(0).getTextContent() + ".");*/
            /*item(0) si hi ha sols 1 titol, si haguessin 2 i vulguessim veure el 2n seria 1 pero la resta de llibres donaria error
            * el mateix per als altres camps del XML*/
            }





        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
