import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Formatter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;
/* @autor Lars Weder,Martin Heinzle,Roman Vorburger, Marvin K�ndig
 * @version 0.3
 * Datum:24.02.2018
 */
@XmlRootElement(name = "Kartei")
@XmlAccessorType(XmlAccessType.FIELD)
public class Kartei2 {
	
	@XmlElement(name = "Karte")
	private ArrayList<Karte> kartei;
	
	@XmlElement(name = "Sprache")
	private ArrayList<Sprache> sprachen;
	
	@XmlElement(name = "Benutzer")
	private ArrayList<Benutzer> benutzerListe;
	
	@XmlTransient
	private Fach[] fach;
	
	@XmlTransient
	private Benutzer aktuellerBenutzer;
	
	@XmlTransient
	private Sprache aktuelleSprache;

	public Kartei2() {
		
	}
	
	public Kartei2(String pfad) throws Exception {
		this.kartei = new ArrayList<Karte>();
		this.sprachen = new ArrayList<Sprache>();
		this.benutzerListe = new ArrayList<Benutzer>();
		this.fach = new Fach[5];
		karteiEinlesen(pfad);
	}


	public void karteHinzufuegen(Karte k1) {		
		for (Karte k2 : kartei) {
			if (k1.getWortA().equalsIgnoreCase(k2.getWortA()) && k2.getWortB().equalsIgnoreCase(k1.getWortB())){
				System.out.println("Wortkombination bereits vorhanden");
				return;
			}
		}
		
		kartei.add(k1);
		
	}
	
	public void karteLoeschen(Karte k) {
		kartei.remove(k);
	}
			
	public ArrayList<Karte> getLernkartei() {
		return kartei;
	}
	
	public ArrayList<Benutzer> getBenutzeriste() {
		return benutzerListe;
	}

	public void setLernkartei(ArrayList<Karte> lernkartei) {
		this.kartei = lernkartei;
	}

	public void lernkarteiSpeichern(String pfad) {
	    
		try {
    		
		JAXBContext jaxbContext = JAXBContext.newInstance(Kartei2.class);
	    Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
	 
	    jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	     
	    //Marshal the card list in console
	    //jaxbMarshaller.marshal(this, System.out);
	     
	    //Marshal the card list in file
	    jaxbMarshaller.marshal(this, new File(pfad));
		 } catch (JAXBException e) {
				e.printStackTrace();
			      }
    }
	
	
	public void karteiEinlesen(String pfad) throws Exception {
		/*
		 * XML File einlesen und die enthaltenen Elemente zu Objekten (Karte) umwandeln
		 */

		
	try {
    		
			JAXBContext context = JAXBContext.newInstance(Kartei2.class);
			
			Unmarshaller unmarshaller = context.createUnmarshaller();
			
			Kartei2 imp =  (Kartei2) unmarshaller.unmarshal(new File(pfad));
			
				for (Karte k : imp.getLernkartei()) {
					//System.out.println("Importiere Karte:" + k.toString());
					kartei.add(k);
				}
				
				for (Benutzer b : imp.getBenutzerListe()) {
					benutzerListe.add(b);
					
				}
			
		      } catch (JAXBException e) {
			e.printStackTrace();
		      }
		
	}

	public void kartenAnzeigen() {
		for (Karte k : kartei) {
			System.out.println(k.toString());
	}
	
			
	}
	
	public boolean benutzerExistiert(String benutzername) {
		for (Benutzer b : benutzerListe) {
			if (b.getBenutzername().equals(benutzername)) {
				return true;
			}
		}
		return false;
	}
	
	public void benutzerLaden(String benutzername, String passwort) {
		for (Benutzer b : benutzerListe) {
			System.out.println(b);
			if (b.getBenutzername().equals(benutzername)) {
				if (b.getPasswort().equals(getMD5Hash(passwort))) {
					//passwort korrekt
					this.aktuellerBenutzer = b;
				}
				else {
					//falsche passwort
					System.out.println("Passwort falsch");
				}
				
			}
		}
	}
	
	public void benutzerHinzufuegen(String benutzername, String passwort) {
		System.out.println(benutzerListe.size());
		for (Benutzer b : benutzerListe) {
			if (b.getBenutzername().equals(benutzername)) {
				System.out.println("Benutzer existiert bereits");
				return;				
			}
		}
		benutzerListe.add(new Benutzer(benutzername, getMD5Hash(passwort)));
	}
	
	public void benutzerLoeschen(Benutzer b) {
		benutzerListe.remove(b);
	}
		
	public ArrayList<Benutzer> getBenutzerListe() {
		return benutzerListe;
	}
	
	public void faecherBefuellen() {
		
		
		if (aktuellerBenutzer.getLernfortschritte() == (null)){
			aktuellerBenutzer.setLernfortschritte(new ArrayList<KartenStatus>());			
		}
		
		ArrayList<KartenStatus> alleStatus = aktuellerBenutzer.getLernfortschritte();
		
		
		// Faecher 1-5 erstellen und gegebenenfalls bereits vorhandene Faecher l�schen
		for(int i=1; i<6; i++) {
			fach[i-1] = new Fach(i-1);
			
		}
						
			// Karten aus der Kartei in Fach laden, dazugeh�rigen Benutzerstatus pr�fen und ins jeweilige Fach ablegen.
				
			for (Karte k : kartei) {
				
				boolean found = false;

				for (KartenStatus st : alleStatus){
					if (k.getId().equals(st.getUid())){
						fach[st.getFach()-1].karteHinzufuegen(k);
						found = true;
						break;
					}
				}
				
				if (found == false) {
					alleStatus.add(new KartenStatus(k.getId(), 1));
					fach[0].karteHinzufuegen(k);
				}
				
			}
			
								
				}			
	
public Karte gibNaechsteKarte(int fachnr, String sprache) {
	
	//N�chste Karte aus diesem Fach in der entsprechenden Sprache ausgeben
	for (Karte k : fach[fachnr-1].gibKarten()) {
		if (k.getSprache().equals(sprache)) {
			return k;
		}
	}
	
	return null;
}

public void karteVerschieben(Karte k, int altesFach, int neuesFach) {
	
	if (k != null) {
	
	//Zuerst Benutzerstatus anpassen

	boolean found = false;
	
	for (KartenStatus ks : aktuellerBenutzer.getLernfortschritte()) {
		if (k.getId().equals(ks.getUid())) {
			ks.setFach(neuesFach);
			found = true;		
			break;
		}
	}

	if (found == false) {
		aktuellerBenutzer.getLernfortschritte().add(new KartenStatus(k.getId(), neuesFach));
	}
	
	//.. dann Karte in Fach verschieben
	
	//System.out.println("entfernen");
	fach[altesFach-1].karteEnfernen(k);
	
	//System.out.println("hinzufuegen");
	fach[neuesFach-1].karteHinzufuegen(k);
	
	
	}
	
	else {
		System.out.println("Verschieben nicht m�glich. Keine Karte.");
	}
		
}

public Benutzer getBenutzer() {
	return aktuellerBenutzer;
}

public void setBenutzer(Benutzer benutzer) {
	this.aktuellerBenutzer = benutzer;
}

// Methode zur verschl�sselung des Passworts. 
public static String getMD5Hash(String str) {
    StringBuilder sb = new StringBuilder(32);
    try {
       MessageDigest md5 = MessageDigest.getInstance("MD5");
       md5.update(str.getBytes());
       Formatter f = new Formatter(sb);
       for (byte b : md5.digest()) {
          f.format("%02x", b);
       }
    }
    catch (NoSuchAlgorithmException ex) {
       ex.printStackTrace();
    }
    return sb.toString();
 }

public boolean spracheHinzugfuegen(String ab, String a, String b) {
	for (Sprache s : sprachen) {
		if (s.getSpracheA().equalsIgnoreCase(a) && s.getSpracheB().equalsIgnoreCase(b)) {
			System.out.println("Sprachpaar existiert bereits");
			return false;
		}
				if (s.getSpracheA().equalsIgnoreCase(b) && s.getSpracheB().equalsIgnoreCase(a)) {
			System.out.println("Sprachpaar existiert bereits");
			return false;
		}
	}
	
	sprachen.add(new Sprache(ab, a, b));
	
	return true;
}



} 
	
	
	



	
	

