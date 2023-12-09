package xpathGQOKMW;

import java.io.File;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class xPathModify {
    public static void main(String[] args) {
        try {
            // Betöltjük az XML dokumentumot
            File inputFile = new File("GQOKMW_1122\\GQOKMW_kurzusfelvetel.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(inputFile);
            doc.getDocumentElement().normalize();

            // XPath objektum létrehozása
            XPath xPath = XPathFactory.newInstance().newXPath();

            // Módosítások végrehajtása
            addNewCourse(doc, xPath);
            deleteCourse(doc, xPath);
            modifyCourse(doc, xPath);

            // Eredmények kiírása a konzolra
            printToConsole(doc);

            // Eredmények kiírása egy fájlba
            saveToFile(doc, "GQOKMW_1122\\kurzusfelvetelGQOKMW1.xml");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Új kurzus hozzáadása
    private static void addNewCourse(Document doc, XPath xPath) throws XPathExpressionException {
        Element kurzusokElement = (Element) xPath.compile("/GQOKMW_kurzusfelvetel/kurzusok").evaluate(doc,
                XPathConstants.NODE);
        Element newCourse = doc.createElement("kurzus");

        newCourse.setAttribute("id", "GBAKOKSZVNE21");
        Element kurzusnev = doc.createElement("kurzusnev");
        kurzusnev.appendChild(doc.createTextNode("Kurzus"));
        newCourse.appendChild(kurzusnev);

        Element kredit = doc.createElement("kredit");
        kredit.appendChild(doc.createTextNode("4"));
        newCourse.appendChild(kredit);

        Element hely = doc.createElement("hely");
        hely.appendChild(doc.createTextNode("34. előadó"));
        newCourse.appendChild(hely);

        Element idopont = doc.createElement("idopont");
        idopont.appendChild(doc.createTextNode("Hétfő 10:00-13:00"));
        newCourse.appendChild(idopont);

        Element oktato = doc.createElement("oktato");
        oktato.appendChild(doc.createTextNode("Miszter Tamás"));
        newCourse.appendChild(oktato);

        kurzusokElement.appendChild(newCourse);
    }

    // Egy kurzus törlése
    private static void deleteCourse(Document doc, XPath xPath) throws XPathExpressionException {
        Node courseToDelete = (Node) xPath.compile("/GQOKMW_kurzusfelvetel/kurzusok/kurzus[@id='GTVVE6001B-B']")
                .evaluate(doc, XPathConstants.NODE);
        courseToDelete.getParentNode().removeChild(courseToDelete);
    }

    // Egy kurzus adatainak módosítása
    private static void modifyCourse(Document doc, XPath xPath) throws XPathExpressionException {
        Node courseToModify = (Node) xPath.compile("/GQOKMW_kurzusfelvetel/kurzusok/kurzus[@id='MEIOKSZVNE21']")
                .evaluate(doc, XPathConstants.NODE);
        Element courseElement = (Element) courseToModify;

        courseElement.getElementsByTagName("kredit").item(0).setTextContent("4");
        courseElement.getElementsByTagName("hely").item(0).setTextContent("A1 203");
        courseElement.getElementsByTagName("idopont").item(0).setTextContent("Szerda 12:00-14:00");
    }

    // Eredmények kiírása a konzolra
    private static void printToConsole(Document doc) throws TransformerException {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        StreamResult consoleResult = new StreamResult(new StringWriter());
        DOMSource source = new DOMSource(doc);
        transformer.transform(source, consoleResult);
        System.out.println(consoleResult.getWriter().toString());
    }

    // Eredmények kiírása fájlba
    private static void saveToFile(Document doc, String fileName) throws TransformerException {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        StreamResult fileResult = new StreamResult(new File(fileName));
        DOMSource source = new DOMSource(doc);
        transformer.transform(source, fileResult);

        System.out.println("Eredmény fájlba kiírva: " + fileName);
    }
}
