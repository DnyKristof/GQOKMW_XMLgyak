package domGQOKMW1115;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerException;

public class DomModifyGQOKMW {
    public static void main(String[] args) {
        try {
            File inputFile = new File(
                    "GQOKMW_1115\\DomQueryGQOKMW\\src\\main\\java\\domGQOKMW1115\\dommodifyGQOKMW1115\\GQOKMW_kurzusfelvetel.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList kurzusNodeList = doc.getElementsByTagName("kurzus");
            for (int i = 0; i < kurzusNodeList.getLength(); i++) {
                Element kurzusNode = (Element) kurzusNodeList.item(i);
                Element oraadoNode = (Element) kurzusNode.getElementsByTagName("oraado").item(0);
                String nyelvAttr = kurzusNode.getAttribute("jovahagyas");

                if (oraadoNode == null) {
                    oraadoNode = doc.createElement("oraado");
                    oraadoNode.appendChild(doc.createTextNode("Dr. Kovács László"));
                    kurzusNode.appendChild(oraadoNode);
                }

                if (nyelvAttr.equals("igen")) {
                    kurzusNode.setAttribute("jovahagyas", "nem");
                }
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult consoleResult = new StreamResult(System.out);
            transformer.transform(source, consoleResult);

            File outputFile = new File(
                    "GQOKMW_1115\\DomQueryGQOKMW\\src\\main\\java\\domGQOKMW1115\\dommodyifyGQOKMW1115\\kurzusfelvetelModify1GQOKMW.xml");
            OutputStream outputStream = new FileOutputStream(outputFile);
            StreamResult fileResult = new StreamResult(outputStream);
            transformer.transform(source, fileResult);
            outputStream.close();

        } catch (ParserConfigurationException | IOException | TransformerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}