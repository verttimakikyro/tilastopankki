/**
 * 
 */
package fxTilastopankki;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * @author Vertti Mäkikyrö
 *
 */
public class Joukkue {
	
	private String nimi;
	private int id;
	private ArrayList<Pelaaja> pelaajat = new ArrayList<>();
	
	
	public Joukkue(String nimi, int id) {
		this.setNimi(nimi);
		this.id = id;
	}
	
	public Joukkue() {
		
	}
	
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
	 */
	public void parse(String rivi) {
		StringBuffer sb = new StringBuffer(rivi);
		id = Mjonot.erota(sb, '|', id);
		nimi = Mjonot.erota(sb, '|', nimi);
	}
	

	public String tallennaPelaajat()  {
		StringBuilder pelaajatTekstina = new StringBuilder();
		for(Pelaaja pelaaja : pelaajat) {
			pelaajatTekstina.append(pelaaja.toString());
		}
		return pelaajatTekstina.toString();
	}
	
	
	/**
	 * @return Palauttaa listan joukkueen pelaajista
	 */
	public ArrayList<Pelaaja> getPelaajat() {
		return pelaajat;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

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


	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
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
	
	

}
