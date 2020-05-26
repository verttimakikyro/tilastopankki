/**
 * 
 */
package fxTilastopankki;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import fi.jyu.mit.fxgui.Dialogs;

/**
 * Luokkka joukkueiden käsittelemiseksi
 * 
 * @author Vertti Mäkikyrö
 *
 */
public class Joukkueet {
	
	private ArrayList<Joukkue> joukkueet = new ArrayList<>();
	private int nextID;
	
	
	/**
	 * Luetaan joukkueet tiedostosta
	 * @param tiedosto Tiedosto joka luetaan
	 * 
	 * @example
	 * <pre name="test">
     * #import java.io.File;
     * Joukkueet joukkueet = new Joukkueet();
     * Joukkue joukkue1 = new Joukkue("test", 1);
     * Joukkue joukkue2 = new Joukkue("test", 2);
     * Joukkue joukkue3 = new Joukkue("test", 3);
     * joukkueet.lisaaJoukkue(joukkue1);
     * joukkueet.lisaaJoukkue(joukkue2);
     * joukkueet.lisaaJoukkue(joukkue3);
     * joukkueet.tallenna("joukkueettesti.dat", "pelaajattesti.dat");
     * joukkueet.tyhjenna();    
     * joukkueet.lueTiedosto("joukkuetesti.dat");
     * joukkueet.getJoukkueet().size() === 3;
	 */
	public void lueTiedosto(String tiedosto) {
		try (Scanner lukija = new Scanner(new File(tiedosto))) {
			while (lukija.hasNextLine()) {
				String rivi = lukija.nextLine();
				if (rivi.charAt(0) == ';') continue; 
				Joukkue joukkue = new Joukkue();
				joukkue.parse(rivi);
				joukkueet.add(joukkue);
			}
		} catch (FileNotFoundException e) {
            Dialogs.showMessageDialog("Virhe tiedoston lukemisessa! " + e.getMessage());
        }
	}
	

	/**
	 * Antaa seuraavan ID-numeron
	 * @return ID-numero
	 * 
	 * @example
	 * <pre name="test">
	 * Joukkueet test = new Joukkueet();
	 * test.seuraavaId() === 1;
	 * Joukkue joukkue1 = new Joukkue();
	 * joukkue1.setId(1);
	 * test.lisaaJoukkue(joukkue1);
	 * test.seuraavaId() === 2;
	 * Joukkue joukkue2 = new Joukkue();
	 * test.lisaaJoukkue(joukkue2);
	 * joukkue2.setId(4);
	 * test.seuraavaId() === 5;
	 * </pre>
	 */
	public int seuraavaId() {
		if(joukkueet.isEmpty()) nextID = 1;
		else nextID = joukkueet.get(joukkueet.size() -1).getId() + 1;
		return nextID;
	}
	
	
	/**
	 * Lisätään uusi joukkue
	 * @param nimi Joukkueen nimi
	 */
	public void uusiJoukkue(String nimi) {
		joukkueet.add(new Joukkue(nimi, seuraavaId()));
	}
	
	/**
	 * Lisätään joukkue
	 * @param joukkue lisättävä joukkue
	 */
	public void lisaaJoukkue(Joukkue joukkue) {
		joukkueet.add(joukkue);
	}
	
	/**
	 * Etsitään joukkue ID-numeron perusteella
	 * @param id Joukkueen ID-numero
	 * @return Etsittävä joukkue
	 * 
	 * @example
	 * <pre name="test">
	 * Joukkueet joukkueet = new Joukkueet();
	 * Joukkue joukkue1 = new Joukkue("test1", 1);
	 * Joukkue joukkue2 = new Joukkue("test2", 2);
	 * Joukkue joukkue3 = new Joukkue("test3", 3);
	 * Joukkue joukkue4 = new Joukkue("test4", 4);
	 * joukkueet.lisaaJoukkue(joukkue1);
	 * joukkueet.lisaaJoukkue(joukkue2);
	 * joukkueet.lisaaJoukkue(joukkue3);
	 * joukkueet.lisaaJoukkue(joukkue4);
	 * joukkueet.etsiJoukkue(1) === true;
	 * joukkueet.etsiJoukkue(5) === false;
	 * joukkueet.etsiJoukkue(2) === true;
	 * joukkueet.etsiJoukkue(6) === false;
	 * joukkueet.etsiJoukkue(4) === true;
	 * </pre>
	 * 
	 */
	public boolean etsiJoukkue(int id) {
		for(Joukkue joukkue : joukkueet) {
			if(joukkue.getId() == id) return true;
		}
		return false;
	}

	/**
	 * 
	 * @return Lista joukkueista
	 */
	public ArrayList<Joukkue> getJoukkueet() {
		return joukkueet;
	}
	
	
	/**
	 * Metodi joukkueiden tyhjentämiseksi.
	 */
	public void tyhjenna() {
	    this.joukkueet = new ArrayList<>();
	}
	
	
	/**
	 * Tallennetaan joukkueet tiedostoon
	 * @param joukkueetTiedosto Mihin tiedostoon tallennetaan joukkueen tiedot
	 * @param pelaajatTiedosto Mihin tiedostoon tallennetaan pelaajien tiedot
	 * @return True jos tallennus onnistui, muuten false
	 * @example
     * <pre name="test">
     * #import java.io.File;
     * Joukkueet joukkueet = new Joukkueet();
     * Joukkue joukkue1 = new Joukkue("test", 1);
     * Joukkue joukkue2 = new Joukkue("test", 2);
     * Joukkue joukkue3 = new Joukkue("test", 3);
     * joukkueet.lisaaJoukkue(joukkue1);
     * joukkueet.lisaaJoukkue(joukkue2);
     * joukkueet.lisaaJoukkue(joukkue3);
     * joukkueet.tallenna("joukkuetesti.dat", "pelaajattesti.dat");
     * joukkueet.tyhjenna();
     * joukkueet.lueTiedosto("joukkueet.dat");
     * joukkueet.getJoukkueet().size() === 3;
     * joukkueet.getJoukkueet().get(1).getId() === 2;
     * </pre>
	 */
	@SuppressWarnings("resource")
    public boolean tallenna(String joukkueetTiedosto, String pelaajatTiedosto)  {
        
	    try {
	            PrintWriter writerJoukkue = new PrintWriter(joukkueetTiedosto, "UTF-8");
	            PrintWriter writerPelaajat = new PrintWriter(pelaajatTiedosto, "UTF-8");
		
	            writerJoukkue.println(";Joukkueet");
	            for(Joukkue joukkue : joukkueet) {
	                writerJoukkue.println(joukkue.getId() + "|" + joukkue.getNimi() + "|");
	                for(Pelaaja pelaaja : joukkue.getPelaajat()) {
	                    writerPelaajat.println(pelaaja.toString());
	                }
	            }
	            writerJoukkue.close();
	            writerPelaajat.close();
	            return true;
	    } catch (Exception e){
	        Dialogs.showMessageDialog("Tallennustiedostoa ei löytynyt " + e.getMessage());
	        return false;
	    }
	}
	

	/**
	 * Testipääohjelma luokalle
	 * @param args ei käytössä
	 */
	public static void main(String[] args) {
		Joukkueet testi = new Joukkueet();
		
		Joukkue JKV = new Joukkue();
		Joukkue OLO = new Joukkue();
		
		JKV.parse(testi.seuraavaId() + "|" +  "JKV");
		OLO.parse(testi.seuraavaId() + "|" +  "OLO");
		
		testi.lisaaJoukkue(JKV);
		testi.lisaaJoukkue(OLO);
		
		Pelaaja aku = new Pelaaja();
		Pelaaja ankka = new Pelaaja();
		
		aku.testiTiedot();
		ankka.testiTiedot();
		
		JKV.lisaaPelaaja(aku);
		OLO.lisaaPelaaja(ankka);
		
		for(Joukkue joukkue : testi.joukkueet) {
			System.out.println(joukkue.toString());
			for(Pelaaja pelaaja : joukkue.getPelaajat()) {
				System.out.println(pelaaja.toString());
			}
		}
	}
}
