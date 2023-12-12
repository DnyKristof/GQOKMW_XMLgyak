package hu.domparse.gqokmw;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.StringJoiner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DOMWriteGQOKMW {

    public static void main(String[] args) {
        try {
            // Create a new Document
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            // Create the root element
            Element rootElement = doc.createElement("GQOKMW_BEADANDO");
            rootElement.setAttribute("xmlns:xs", "http://www.w3.org/2001/XMLSchema-instance");
            rootElement.setAttribute("xs:noNamespaceSchemaLocation", "XMLSchemaGQOKMW.xsd");
            doc.appendChild(rootElement);

            // Add Vasarlok
            addVasarlo(doc, rootElement, "1", "01", "Kiss Ádám", "Miskolc", "Kossuth Lajos utca", "1",
                    "0630-1234567");
            addVasarlo(doc, rootElement, "2", "02", "Nagy Pista", "Szekszárd", "Árpád utca", "5",
                    "0630-3214569");
            addVasarlo(doc, rootElement, "3", "03", "Nagy Máté", "Esztergom", "József Attila út", "5",
                    "0630-1234765", "0630-7654321");

            // Add Megrendelesek
            addMegrendeles(doc, rootElement, "01", "2021-02-03", "2021-02-01", "Teljesítve");
            addMegrendeles(doc, rootElement, "02", "2021-02-07", "2021-02-02", "Szállítás alatt");
            addMegrendeles(doc, rootElement, "03", "2021-03-05", "2021-03-01", "Teljesítve");

            // Add Megrendelt termek
            addMegrendeltTermek(doc, rootElement, "01", "11", "0.2", "2", "25000");
            addMegrendeltTermek(doc, rootElement, "02", "12", "0.2", "2", "25000");
            addMegrendeltTermek(doc, rootElement, "03", "13", "0.2", "2", "25000");

            // Add Uzletek
            addUzlet(doc, rootElement, "21", "11", "Hervis", "Miskolc", "Lajos utca", "6", "0670-5312490", "0630-6587421");
            addUzlet(doc, rootElement, "22", "12", "Intersport", "Miskolc", "Lajos utca", "6", "0670-5312490", "0630-6587421");
            addUzlet(doc, rootElement, "23", "13", "Dechatlon", "Miskolc", "Lajos utca", "6", "0670-5312490", "0630-6587421");

            // Add Ü_D
            addUD(doc, rootElement, "21", "31", "420000");
            addUD(doc, rootElement, "22", "32", "530000");
            addUD(doc, rootElement, "23", "33", "370000");

            // Add Dolgozok
            addDolgozo(doc, rootElement, "31", "Nagy Gergő", "Miskolc", "Oláh Lajos", "6", "0670-5342490", "0630-6596521");
            addDolgozo(doc, rootElement, "32", "Kiss Árpád", "Arnót", "Deaák ferenc", "8", "0670-5643905", "0630-6734524");
            addDolgozo(doc, rootElement, "33", "Szabó Gergő", "Miskolc", "Kossuth Lajos", "56", "0670-5723490", "0630-9654321");

            // Transform and save to file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{https://xml.apache.org/xslt}indent-amount", "2");

            DOMSource source = new DOMSource(doc);
            File myFile = new File("DomWriteOutPut_GQOKMW.xml");
            StreamResult file = new StreamResult(myFile);
            StreamResult consoleResult = new StreamResult(System.out);
            transformer.transform(source, file);
            transformer.transform(source, consoleResult);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Add Vasarlo
    private static void addVasarlo(Document doc, Element rootElement, String v_id, String m_id, String nev,
            String varos, String utca, String hazszam, String... telefonszamok) {
        Element vasarlo = doc.createElement("Vasarlo");
        vasarlo.setAttribute("V_id", v_id);
        vasarlo.setAttribute("M_id", m_id);

        Element nevElement = createElement(doc, "Nev", nev);

        Element cim = doc.createElement("Cim");
        Element utcaElement = createElement(doc, "Utca", utca);
        Element varosElement = createElement(doc, "Varos", varos);
        Element hazszamElement = createElement(doc, "Hazszam", hazszam);

        cim.appendChild(utcaElement);
        cim.appendChild(varosElement);
        cim.appendChild(hazszamElement);

        vasarlo.appendChild(nevElement);
        vasarlo.appendChild(cim);

        for (String telefon : telefonszamok) {
            Element telefonElement = createElement(doc, "Telefonszam", telefon);
            vasarlo.appendChild(telefonElement);
        }

        rootElement.appendChild(vasarlo);
    }

    // Add Megrendeles
    private static void addMegrendeles(Document doc, Element rootElement, String m_id, String hatarido,
            String megrendelesideje, String statusz) {
        Element megrendeles = doc.createElement("Megrendeles");
        megrendeles.setAttribute("M_id", m_id);

        Element hataridoElement = createElement(doc, "Hatarido", hatarido);
        Element megrendelesidejeElement = createElement(doc, "Megrendelesideje", megrendelesideje);
        Element statuszElement = createElement(doc, "Statusz", statusz);

        megrendeles.appendChild(hataridoElement);
        megrendeles.appendChild(megrendelesidejeElement);
        megrendeles.appendChild(statuszElement);

        rootElement.appendChild(megrendeles);
    }

    // Add Megrendelt termek
    private static void addMegrendeltTermek(Document doc, Element rootElement, String m_id, String mt_id, String learazas,
            String mennyiseg, String listaar) {
        Element megrendeltTermek = doc.createElement("Megrendelttermek");
        megrendeltTermek.setAttribute("M_id", m_id);
        megrendeltTermek.setAttribute("MT_id", mt_id);

        Element learazasElement = createElement(doc, "Learazas", learazas);
        Element mennyisegElement = createElement(doc, "Mennyiseg", mennyiseg);
        Element listaarElement = createElement(doc, "Listaar", listaar);

        megrendeltTermek.appendChild(learazasElement);
        megrendeltTermek.appendChild(mennyisegElement);
        megrendeltTermek.appendChild(listaarElement);

        rootElement.appendChild(megrendeltTermek);
    }

    // Add Uzlet
    private static void addUzlet(Document doc, Element rootElement, String u_id, String mt_id, String nev,
            String varos, String utca, String hazszam, String... telefonszamok) {
        Element uzlet = doc.createElement("Üzlet");
        uzlet.setAttribute("Ü_id", u_id);
        uzlet.setAttribute("MT_id", mt_id);

        Element nevElement = createElement(doc, "Nev", nev);

        Element cim = doc.createElement("Cim");
        Element utcaElement = createElement(doc, "Utca", utca);
        Element varosElement = createElement(doc, "Varos", varos);
        Element hazszamElement = createElement(doc, "Hazszam", hazszam);

        cim.appendChild(utcaElement);
        cim.appendChild(varosElement);
        cim.appendChild(hazszamElement);

        
        uzlet.appendChild(cim);
        uzlet.appendChild(nevElement);

        for (String telefon : telefonszamok) {
            Element telefonElement = createElement(doc, "Telefonszam", telefon);
            uzlet.appendChild(telefonElement);
        }

        rootElement.appendChild(uzlet);
    }

    // Add UD
    private static void addUD(Document doc, Element rootElement, String u_id, String d_id, String fizetes) {
        Element ud = doc.createElement("Ü_D");
        ud.setAttribute("Ü_id", u_id);
        ud.setAttribute("D_id", d_id);

        Element fizetesElement = createElement(doc, "Fizetes", fizetes);

        ud.appendChild(fizetesElement);

        rootElement.appendChild(ud);
    }

    // Add Dolgozo
    private static void addDolgozo(Document doc, Element rootElement, String d_id, String nev,
            String varos, String utca, String hazszam, String... telefonszamok) {
        Element dolgozo = doc.createElement("Dolgozo");
        dolgozo.setAttribute("D_id", d_id);

        Element nevElement = createElement(doc, "Nev", nev);

        Element cim = doc.createElement("Cim");
        Element utcaElement = createElement(doc, "Utca", utca);
        Element varosElement = createElement(doc, "Varos", varos);
        Element hazszamElement = createElement(doc, "Hazszam", hazszam);

        cim.appendChild(utcaElement);
        cim.appendChild(varosElement);
        cim.appendChild(hazszamElement);

        
        dolgozo.appendChild(cim);
        dolgozo.appendChild(nevElement);

        for (String telefon : telefonszamok) {
            Element telefonElement = createElement(doc, "Telefonszam", telefon);
            dolgozo.appendChild(telefonElement);
        }

        rootElement.appendChild(dolgozo);
    }

    private static Element createElement(Document doc, String name, String value) {
        Element element = doc.createElement(name);
        element.appendChild(doc.createTextNode(value));
        return element;
    }


}