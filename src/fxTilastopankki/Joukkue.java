/**
 * 
 */
package fxTilastopankki;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * Luokka joukkueen luomiseksi
 * 
 * @author Vertti Mäkikyrö
 *
 */
public class Joukkue {
	
	private String nimi;
	private int id;
	private ArrayList<Pelaaja> pelaajat = new ArrayList<>();
	
	
	/**
	 * Muodostaja
	 * @param nimi Joukkueen nimi
	 * @param id Joukkueen ID-numero
	 */
	public Joukkue(String nimi, int id) {
		this.setNimi(nimi);
		this.id = id;
	}
	
	
	/**
	 * Muodostaja
	 */
	public Joukkue() {
		
	}
	
	
	/**
	 * Lisätään pelaaja joukkueeseen
	 * @param pelaaja Lisättävä pelaaja
	 */
	public void lisaaPelaaja(Pelaaja pelaaja) {
		pelaajat.add(pelaaja);
	}
	
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}


	/**
	 * Luetaan tiedostosta rivi kerrallaan pelaajan tiedot ja luodaan uusi pelaaja
	 * @throws Exception
	 */
	public void lueTiedosto() throws Exception {
		
		try (Scanner lukija = new Scanner(new File("pelaajat.dat"))) {
			while (lukija.hasNextLine()) {
				String rivi = lukija.nextLine();
				//if (rivi.charAt(0) == ';') continue; 
				Pelaaja pelaaja = new Pelaaja();
				pelaaja.parse(rivi);
				if(pelaaja.getId() == this.id) pelaajat.add(pelaaja);
			}
		}
	
	}
	
	/**
	 * Erottaa merkkirivistä joukkueen id-numeron ja nimen
	 * @param rivi, merkkirivi, josta id-numero ja nimi erotellaan
	 * 
	 * @example
	 * <pre name="test">
	 * Joukkue joukkue = new Joukkue();
	 * joukkue.parse("1|test|");
	 * joukkue.toString().equals("1|test|");
	 * </pre>
	 */
	public void parse(String rivi) {
		StringBuffer sb = new StringBuffer(rivi);
		id = Mjonot.erota(sb, '|', id);
		nimi = Mjonot.erota(sb, '|', nimi);
	}
	


	
	
	/**
	 * @return Palauttaa listan joukkueen pelaajista
	 */
	public ArrayList<Pelaaja> getPelaajat() {
		return pelaajat;
	}


	/**
	 * @return the nimi
	 */
	public String getNimi() {
		return nimi;
	}

	/**
	 * @param nimi the nimi to set
	 */
	public void setNimi(String nimi) {
		this.nimi = nimi;
	}


	/* 
	 * Equals metodi, jolla tarkistetaan viitataanko samaan joukkueeseen
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Joukkue other = (Joukkue) obj;
		if (id != other.id)
			return false;
		if (nimi == null) {
			if (other.nimi != null)
				return false;
		} else if (!nimi.equals(other.nimi))
			return false;
		return true;
	}
	
	
	/**
	 * Palauttaa joukkueen tiedot merkkijonona
	 */
	@Override
	public String toString() {
		return id + "|" + nimi + "|";
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Joukkue joukkue = new Joukkue();
		joukkue.parse("1|testi");
		System.out.println(joukkue.getId());
		System.out.println(joukkue.getNimi());
		Pelaaja pelaaja = new Pelaaja();
		joukkue.lisaaPelaaja(pelaaja);
		for(Pelaaja testi : joukkue.pelaajat) {
			testi.tulosta(System.out);
		}

	}

}
