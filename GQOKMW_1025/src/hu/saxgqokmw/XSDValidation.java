package hu.saxgqokmw;

import java.io.File;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

public class XSDValidation {

	public static void main(String[] args) {
		String[] files = { "GQOKMW_kurzusfelvetel.xsd", "GQOKMW_kurzusfelvetel.xml" };
		boolean isValid = validateXMLSchema(files[0], files[1]);
		if (isValid) {
			System.out.println("XSD Validation successful");
		} else {
			System.out.println("Unsuccessful validation.");
		}

	}

	public static boolean validateXMLSchema(String xsdPath, String xmlPath) {
		try {
			SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = factory.newSchema(new File(xsdPath));
			Validator validator = schema.newValidator();
			validator.validate(new StreamSource(new File(xmlPath)));
		} catch (IOException e | SAXException s) {
			if(e != null){
				System.out.println(e.printStackTrace)
			}
			else{
				System.out.println(s.printStackTrace)
			}
			return false;
		} 

		return true;

	}
}