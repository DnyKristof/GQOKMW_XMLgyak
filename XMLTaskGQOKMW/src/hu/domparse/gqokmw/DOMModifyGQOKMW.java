package hu.domparse.gqokmw;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


import org.w3c.dom.*;

public class DOMModifyGQOKMW {

	public static void main(String[] args) {
        try {
            File xmlFile = new File("XMLGQOKMW.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            //létrehozom a doc-ot amit később a transformhoz használok fel
            Document doc = dBuilder.parse(xmlFile);

            // lekérjük egy adott típushoz tartozó összes elemet amit egy listában tárolunk el
            NodeList vasarloList = doc.getElementsByTagName("Vasarlo");
            //lekérjük azt az elemet a listából amelyiket módosítani szeretnénk, itt index alapján történik a módosítás
            Element vasarlo = (Element) vasarloList.item(0);
            //az elemnek megkeressük azt a tagjét amit módosítani szeretnénk, majd a tartalmát átállítjuk
            vasarlo.getElementsByTagName("Nev").item(0).setTextContent("Türk Viktor");
            
            NodeList megrendelesList = doc.getElementsByTagName("Megrendeles");
            Element megrendeles = (Element) megrendelesList.item(0);
            megrendeles.getElementsByTagName("Statusz").item(0).setTextContent("Hiba");
            
            NodeList megrendelttermekList = doc.getElementsByTagName("Megrendelttermek");
            Element megrendelttermek = (Element) megrendelttermekList.item(1);
            megrendelttermek.getElementsByTagName("Learazas").item(0).setTextContent("0.6");
            
            NodeList üzletList = doc.getElementsByTagName("Üzlet");
            Element üzlet = (Element) üzletList.item(1);
            üzlet.getElementsByTagName("Varos").item(0).setTextContent("Kiskunfélegyháza");
            
            
            NodeList dolgozoList = doc.getElementsByTagName("Dolgozo");
            Element dolgozo = (Element) dolgozoList.item(1);
            dolgozo.getElementsByTagName("Nev").item(0).setTextContent("Lakatos Rómeo");
            
            //TransformerFactory-val iratom ki a fájlt
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            //Beállítom a transformert
            Transformer transformer = transformerFactory.newTransformer();
            //Megadom a forrás fájlt amit fent létrehoztam
            DOMSource source = new DOMSource(doc);
            //Megnyitom a streamet és konzolra kiíratom sys.out-al a fájlt
            StreamResult consoleResult = new StreamResult(System.out);
            transformer.transform(source, consoleResult);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}