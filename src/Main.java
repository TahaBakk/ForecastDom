import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
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
        File inputFile = new File("forecast.xml");

        //factoria de constructor de documentos
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder dbBuilder = null;
            dbBuilder = dbFactory.newDocumentBuilder();

            Document db = dbBuilder.parse(inputFile);
            System.out.println("Root element : " + db.getDocumentElement().getNodeName());

            NodeList lista = db.getElementsByTagName( "location" );

            Element namelista = (Element) lista.item(0);
            System.out.println("El lloc és: "+namelista.getElementsByTagName("name").item(0).getTextContent());

            NodeList list = db.getElementsByTagName("time");

            for (int i = 0; i < list.getLength(); i++) {

                Element eLista = (Element) list.item(i);

                System.out.println("La temperatura és: "+eLista.getElementsByTagName("temperature").item(0).getAttributes().getNamedItem("value").getTextContent()+"graus celsius");
                System.out.println("El temps és: "+eLista.getElementsByTagName("symbol").item(0).getAttributes().getNamedItem("name").getTextContent());

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
