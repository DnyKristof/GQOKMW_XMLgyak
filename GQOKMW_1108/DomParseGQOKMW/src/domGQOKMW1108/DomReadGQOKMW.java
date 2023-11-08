package domGQOKMW1108;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DomReadGQOKMW {

	public static void main(String[] args) throws SAXException, ParserConfigurationException, IOException {
		
		File xmlFile = new File("GQOKMW_orarend.xml");
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = factory.newDocumentBuilder();
		
		Document doc = dBuilder.parse(xmlFile);
		
		doc.getDocumentElement().normalize();
		
		System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
		
		NodeList nList = doc.getElementsByTagName("subject");
		
		for (int i = 0; i < nList.getLength(); i++) {
			
			Node nNode = nList.item(i);
			
			System.out.println("\nCurrent Element: " + nNode.getNodeName());
			
			if (nNode.getNodeType() == Node.ELEMENT_NODE)
			{
				Element elem = (Element) nNode;
				String sid = elem.getAttribute("id");
				
				Node node1 = elem.getElementsByTagName("subjectname").item(0);
				String sname = node1.getTextContent();
				
				Node node2 = elem.getElementsByTagName("time").item(0);
				String time = node2.getTextContent();
				
				Node node3 = elem.getElementsByTagName("teacher").item(0);
				String teach = node3.getTextContent();
				
				Node node4 = elem.getElementsByTagName("place").item(0);
				String place = node4.getTextContent();
				
				System.out.println("Subject id: " + sid);
				System.out.println("Sucject name: " +sname);
				System.out.println("Time: " + time);
				System.out.println("Teacher: " +teach);
				System.out.println("Place: " +place);
			}
		}
	}

}
