package xpathGQOKMW1122;

import org.w3c.dom.*;
import java.io.*;
import javax.xml.parsers.*;
import javax.xml.xpath.*;

public class xPathGQOKMW {

    public static void main(String[] args) {
        try {
            File inputFile = new File("GQOKMW_1122\\studentGQOKMW.xml");
            // Dokumentum elkészítése
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(inputFile);
            doc.getDocumentElement().normalize();

            // XPath készítése
            XPath xPath = XPathFactory.newInstance().newXPath();

            System.out.println("1.lekérdezés:");
            String INPUT1BLK = "class/student";
            NodeList nodeList1 = (NodeList) xPath.compile(INPUT1BLK).evaluate(doc, XPathConstants.NODESET);
            writeData(nodeList1);

            System.out.println("\n2.lekérdezés:");
            String INPUT2BLK = "class/student[@student_id='02']";
            NodeList nodeList2 = (NodeList) xPath.compile(INPUT2BLK).evaluate(doc, XPathConstants.NODESET);
            writeData(nodeList2);

            System.out.println("\n3.lekérdezés:");
            String INPUT3BLK = "//student";
            NodeList nodeList3 = (NodeList) xPath.compile(INPUT3BLK).evaluate(doc, XPathConstants.NODESET);
            writeData(nodeList3);

            System.out.println("\n4.lekérdezés");
            String INPUT4BLK = "class/student[2]";
            NodeList nodeList4 = (NodeList) xPath.compile(INPUT4BLK).evaluate(doc, XPathConstants.NODESET);
            writeData(nodeList4);

            System.out.println("\n5.lekérdezés");
            String INPUT5BLK = "class/student[last()]";
            NodeList nodeList5 = (NodeList) xPath.compile(INPUT5BLK).evaluate(doc, XPathConstants.NODESET);
            writeData(nodeList5);

            System.out.println("\n6.lekérdezés");
            String INPUT6BLK = "class/student[last()-1]";
            NodeList nodeList6 = (NodeList) xPath.compile(INPUT6BLK).evaluate(doc, XPathConstants.NODESET);
            writeData(nodeList6);

            System.out.println("\n7.lekérdezés");
            String INPUT7BLK = "class/student[position() <= 2]";
            NodeList nodeList7 = (NodeList) xPath.compile(INPUT7BLK).evaluate(doc, XPathConstants.NODESET);
            writeData(nodeList7);

            System.out.println("\n8.lekérdezés");
            String INPUT8BLK = "class/*";
            NodeList nodeList8 = (NodeList) xPath.compile(INPUT8BLK).evaluate(doc, XPathConstants.NODESET);
            writeData(nodeList8);

            System.out.println("\n9.lekérdezés");
            String INPUT9BLK = "//student[@*]";
            NodeList nodeList9 = (NodeList) xPath.compile(INPUT9BLK).evaluate(doc, XPathConstants.NODESET);
            writeData(nodeList9);

            System.out.println("\n10.lekérdezés");
            String INPUT10BLK = "//*";
            NodeList nodeList10 = (NodeList) xPath.compile(INPUT10BLK).evaluate(doc, XPathConstants.NODESET);
            writeData(nodeList10);

            System.out.println("\n11.lekérdezés");
            String INPUT11BLK = "class/student[kor > 20]";
            NodeList nodeList11 = (NodeList) xPath.compile(INPUT11BLK).evaluate(doc, XPathConstants.NODESET);
            writeData(nodeList11);

            System.out.println("\n12.lekérdezés");
            String EZ3YRC = "//student/keresztnev | //student/vezeteknev";
            NodeList nodeList12 = (NodeList) xPath.compile(EZ3YRC).evaluate(doc, XPathConstants.NODESET);
            writeData(nodeList12);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeData(NodeList nodeList) {
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            System.out.println("\nAktuális elem: " + node.getNodeName());

            if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("student")) {
                Element element = (Element) node;

                System.out.println("Hallgató ID: " + element.getAttribute("id"));

                System.out.println("Vezetéknév: " + element.getElementsByTagName("vezeteknev").item(0).getTextContent());
                System.out.println("Keresztnév: " + element.getElementsByTagName("keresztnev").item(0).getTextContent());

                System.out.println("Becenév : " + element.getElementsByTagName("becenev").item(0).getTextContent());

                System.out.println("Kor : " + element.getElementsByTagName("kor").item(0).getTextContent());
            }
        }
    }
}