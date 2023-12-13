package hu.domparse.gqokmw;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


import org.w3c.dom.*;

public class DOMQueryGQOKMW {

	 public static void main(String[] args) {
        try {
	            // XML fájl beolvasása és DOM létrehozása
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File("XMLGQOKMW.xml"));

            // 1. Lekérdezés: "13"-as ID-s Megrendelttermek minden adata
            String MT_Id = "13";
            NodeList mttermekList = document.getElementsByTagName("Megrendelttermek");
            for (int i = 0; i < mttermekList.getLength(); i++) {
            	
                Element mttermek = (Element) mttermekList.item(i);
                String mttermekId = mttermek.getAttribute("MT_id");
                if (mttermekId.equals(MT_Id)) {
                    String learazas = mttermek.getElementsByTagName("Learazas").item(0).getTextContent();
                    String mennyiseg = mttermek.getElementsByTagName("Mennyiseg").item(0).getTextContent();
                    String listaar = mttermek.getElementsByTagName("Listaar").item(0).getTextContent();
                    System.out.println("Lekérdezés 1:");
                    System.out.println("Az '" + MT_Id + "' ID-jű megrendelt termék leárazása: " + learazas);
                    System.out.println("Megrendelés mennyisége: " + mennyiseg);
                    System.out.println("Az eredeti listaára: " + listaar);
                    System.out.println();
                    break;
                }
            }
         // Lekérdezés 2: Az "21"-es ID-s Üzlet címe
            String Ü_Id = "21";
            NodeList üzletList = document.getElementsByTagName("Üzlet");
            for (int i = 0; i < üzletList.getLength(); i++) {
            	
                Element vasarlo = (Element) üzletList.item(i);
                if (vasarlo.getAttribute("Ü_id").equals(Ü_Id)) {

                    Element cim = (Element) vasarlo.getElementsByTagName("Cim").item(0);
                    String varos = cim.getElementsByTagName("Varos").item(0).getTextContent().trim();
                    String utca = cim.getElementsByTagName("Utca").item(0).getTextContent().trim();
                    String hazszam = cim.getElementsByTagName("Hazszam").item(0).getTextContent().trim();
                    System.out.println("Lekérdezés 2:");
                    System.out.println("Cím: " + " " + varos + " " + utca + " " + hazszam);
                    System.out.println();
                    break;
                        }
                    }

            
         // Lekérdezés 3: A "03"-as Megrendelés határideje
            String M_Id = "03";
            NodeList megrendelesList = document.getElementsByTagName("Megrendeles");
            for (int i = 0; i < megrendelesList.getLength(); i++) {
            	
                Element megrendeles = (Element) megrendelesList.item(i);
                if(megrendeles.getAttribute("M_id").equals(M_Id)) {
                	String hatarido = megrendeles.getElementsByTagName("Hatarido").item(0).getTextContent().trim();
                	System.out.println("Lekérdezés 3:");
                	System.out.println("A "+ M_Id + "-as ID Megrendelés határideje : " + hatarido);
                	System.out.println();
                }
      
            }

            
            
         // Lekérdezés 4: Azon vásárlók telefonszámának és nevének kiírása, akiknek több telefonszámuk van
            NodeList vasarloList = document.getElementsByTagName("Vasarlo");
            for (int i = 0; i < vasarloList.getLength(); i++) {
                Element vasarlo = (Element) vasarloList.item(i);
                NodeList telefonszamList = vasarlo.getElementsByTagName("Telefonszam");
                
                if (telefonszamList.getLength() > 1) {
                    String nev = vasarlo.getElementsByTagName("Nev").item(0).getTextContent().trim();
                    
                    System.out.println("Lekérdezés 4:");
                    System.out.println("Vásárló neve: " + nev);
                    System.out.println("Telefonszámai:");
                    
                    for (int j = 0; j < telefonszamList.getLength(); j++) {
                        Element telefonszam = (Element) telefonszamList.item(j);
                        System.out.println(telefonszam.getTextContent().trim());
                    }
                    System.out.println();
                }

            }

         // Lekérdezés 5: Azon futárok nevének kiírása, akiknél az áru értéke 2000 fölött van
            String Vnev = "Nagy Máté";
            NodeList vasarloList2 = document.getElementsByTagName("Vasarlo");
            for (int i = 0; i < vasarloList2.getLength(); i++) {
            	
            	Element vasarlo = (Element) vasarloList2.item(i);
            	String M_Id_Vasarlo = "";
            	if(vasarlo.getElementsByTagName("Nev").item(0).getTextContent().equals(Vnev)) {
            		
            		M_Id_Vasarlo = vasarlo.getAttribute("M_id");
            	}
            	
            	NodeList megrendelesList2 = document.getElementsByTagName("Megrendeles");
            	for (int j = 0; j < megrendelesList2.getLength(); j++) {
            		
					Element megrendeles = (Element) megrendelesList2.item(j);
					if(megrendeles.getAttribute("M_id").equals(M_Id_Vasarlo)) {
						String statusz = megrendeles.getElementsByTagName("Statusz").item(0).getTextContent();
						System.out.println("Lekérdezés 5:");
						System.out.println(Vnev + " megrendelésének a státusza: " + statusz);
					}
				}
            			
			}

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

}
