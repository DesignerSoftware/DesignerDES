package co.com.kiosko.clasesAyuda;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Felipe Trivi�o
 */
public class LeerArchivoXML {

    public List<CadenasKioskos> leerArchivoEmpresasKiosko() {
        try {
            InputStream fXmlFile = getClass().getResourceAsStream("../archivosConfiguracion/cadenasKioskos.xml");
            //File fXmlFile = new File("cadenasKioskos.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("cadenaKiosko");
            List<CadenasKioskos> listaCadenas = new ArrayList<CadenasKioskos>();

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    listaCadenas.add(new CadenasKioskos(eElement.getAttribute("id"), eElement.getElementsByTagName("descripcion").item(0).getTextContent(), eElement.getElementsByTagName("cadena").item(0).getTextContent(), eElement.getElementsByTagName("nit").item(0).getTextContent(), eElement.getElementsByTagName("fondo").item(0).getTextContent()));
                }
            }
            return listaCadenas;
        } catch (ParserConfigurationException e) {
            System.out.println("Error LeerArchivoXML.leerArchivoEmpresasKiosko");
            System.out.println("Error parseando el archivo. " + e);
            return null;
        } catch (SAXException e) {
            System.out.println("Error LeerArchivoXML.leerArchivoEmpresasKiosko");
            System.out.println("Error SAX. " + e);
            return null;
        } catch (IOException e) {
            System.out.println("Error LeerArchivoXML.leerArchivoEmpresasKiosko");
            System.out.println("Error leyendo el archivo. " + e);
            return null;
        } catch (DOMException e) {
            System.out.println("Error LeerArchivoXML.leerArchivoEmpresasKiosko: ");
            System.out.println("Error en DOM. " + e);
            return null;
        }
    }
}
