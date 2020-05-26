/**
 * 
 */
package fxTilastopankki;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import fi.jyu.mit.fxgui.Dialogs;
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
	private Pelaaja pelaajat[] = new Pelaaja[0];
	private static final int MAX_PELAAJIA = 20;
	private int lkm = 0;
	
	
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
	    if(pelaajat.length < MAX_PELAAJIA) {
	        pelaajat = Arrays.copyOf(pelaajat, lkm+1);
	        pelaajat[lkm] = pelaaja;
	        lkm++;
	    }
	    else Dialogs.showMessageDialog("Joukkueen pelaajien määrä on täynnä");
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
	 * @param tiedostonNimi mikä tiedosto luetaan
	 * 
	 * @example
	 * <pre name="test">
	 * #import java.io.File;
	 * Joukkueet joukkueet = new Joukkueet();
	 * Joukkue joukkue = new Joukkue();
	 * joukkue.setId(1);
	 * joukkueet.lisaaJoukkue(joukkue);
	 * Pelaaja pelaaja1 = new Pelaaja();
	 * pelaaja1.testiTiedot();
	 * Pelaaja pelaaja2 = new Pelaaja();
	 * pelaaja2.parse("2|test|1|1|1|1|1|1|");
	 * Pelaaja pelaaja3 = new Pelaaja();
	 * pelaaja3.parse("1|test|1|1|1|1|1|1|");
	 * Pelaaja pelaaja4 = new Pelaaja();
	 * pelaaja4.parse("3|test|1|1|1|1|1|1|");
	 * joukkue.lisaaPelaaja(pelaaja1);
	 * joukkue.lisaaPelaaja(pelaaja2);
	 * joukkue.lisaaPelaaja(pelaaja3);
	 * joukkue.lisaaPelaaja(pelaaja4);
	 * joukkueet.tallenna("joukkuetesti.dat", "pelaajatesti.dat");
	 * joukkue.tyhjenna();
	 * joukkue.lueTiedosto("pelaajatesti.dat");
	 * joukkue.getPelaajat().length === 2;
	 * joukkue.getPelaajat()[0].getId() === 1;
	 * joukkue.getPelaajat()[1].getId() === 1;
	 * </pre>
	 */
	public void lueTiedosto(String tiedostonNimi) {
	    
		try (Scanner lukija = new Scanner(new File(tiedostonNimi))) {
			while (lukija.hasNextLine()) {
				String rivi = lukija.nextLine(); 
				Pelaaja pelaaja = new Pelaaja();
				pelaaja.parse(rivi);
				if(pelaaja.getId() == this.id) {
				    lisaaPelaaja(pelaaja);
				}
			}
		} catch (FileNotFoundException e) {
		    Dialogs.showMessageDialog("Virhe tiedoston lukemisessa! " + e.getMessage());
        }
	
	}
	
	/**
	 * Erottaa merkkirivistä joukkueen id-numeron ja nimen
	 * @param rivi merkkirivi, josta id-numero ja nimi erotellaan
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
	 * Metodi pelaajalistan tyhjentämiseksi.
	 */
	public void tyhjenna() {
	    this.pelaajat = new Pelaaja[0];
	    this.lkm = 0;
	}

	
	/**
	 * @return Palauttaa listan joukkueen pelaajista
	 */
	public Pelaaja[] getPelaajat() {
		return pelaajat;
	}
	
    /**
     * @param pelaajat the pelaajat to set
     */
    public void setPelaajat(Pelaaja[] pelaajat) {
        this.pelaajat = pelaajat;
        
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
	
	/* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }


    /**
     * Testipääohjelma luokalle
	 * @param args ei käytössä
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
