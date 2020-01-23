/**
 * 
 */
package tilastopankki;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
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
				if (rivi.charAt(0) == ';') continue; 
				Pelaaja pelaaja = new Pelaaja();
				pelaaja.parse(rivi);
				if(pelaaja.getId() == this.id) pelaajat.add(pelaaja);
			}
		}
	
	}
	
	public void parse(String rivi) {
		StringBuffer sb = new StringBuffer(rivi);
		id = Mjonot.erota(sb, '|', id);
		nimi = Mjonot.erota(sb, '|', nimi);
	}
	
	public void tallenna() throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter("pelaajat.dat", "UTF-8");
		writer.println(";Kenttien jÃ¤rjestys tiedostossa on seuraava:");
		writer.println(";sukunimi etunimi		|ottelut	|maalit		|syotot		|plusmiinus		|jaahyminuutit	|");
		for(Pelaaja pelaaja : pelaajat) {
			writer.println(pelaaja.toString());
		}
		writer.close();
	}
	
	

	/**
	 * Metodi pelaajalistan tulostamiseksi
	 */
	public void tulosta() {
		System.out.println(this.nimi);	
		for(Pelaaja pelaaja : pelaajat) {
			pelaaja.tulosta();
		}
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

}
