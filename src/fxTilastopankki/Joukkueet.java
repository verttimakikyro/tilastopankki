/**
 * 
 */
package fxTilastopankki;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;

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
	 * @throws FileNotFoundException jos tiedostoa ei löydy
	 */
	public void lueTiedosto() throws FileNotFoundException {
		try (Scanner lukija = new Scanner(new File("joukkueet.dat"))) {
			while (lukija.hasNextLine()) {
				String rivi = lukija.nextLine();
				if (rivi.charAt(0) == ';') continue; 
				Joukkue joukkue = new Joukkue();
				joukkue.parse(rivi);
				joukkueet.add(joukkue);
			}
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
	 * @param joukkue
	 */
	public void lisaaJoukkue(Joukkue joukkue) {
		joukkueet.add(joukkue);
	}
	
	/**
	 * Etsitään joukkue ID-numeron perusteella
	 * @param id Joukkueen ID-numero
	 * @return Etsittävä joukkue
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
	 * Tallennetaan joukkueet tiedostoon
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	public void tallenna() throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writerJoukkue = new PrintWriter("joukkueet.dat", "UTF-8");
		PrintWriter writerPelaajat = new PrintWriter("pelaajat.dat", "UTF-8");
		
		writerJoukkue.println(";Joukkueet");
		for(Joukkue joukkue : joukkueet) {
			writerJoukkue.println(joukkue.getId() + "|" + joukkue.getNimi() + "|");
			for(Pelaaja pelaaja : joukkue.getPelaajat()) {
				writerPelaajat.println(pelaaja.toString());
			}
		}
		writerJoukkue.close();
		writerPelaajat.close();
	}
	

	/**
	 * Testipääohjelma luokalle
	 * @param args
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
