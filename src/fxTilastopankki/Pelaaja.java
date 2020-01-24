/**
 * 
 */
package fxTilastopankki;



import java.io.PrintStream;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * @author Vertti M�kikyr�
 * Luokka pelaajan luomiseksi
 */
public class Pelaaja {
	
	private String nimi;
	private int maalit;
	private int syotot;
	private int pisteet;
	private int jaahyt;
	private int plusmiinus;
	private int ottelut;
	private int id;

	
	
	/**
	 * @param nimi
	 * @param maalit
	 * @param syotot
	 * @param jaahyt
	 * @param plusmiinus
	 * @param ottelut
	 */
	public Pelaaja(String nimi, int maalit, int syotot, int jaahyt, int plusmiinus, int ottelut) {

		this.nimi = nimi;
		this.maalit = maalit;
		this.syotot = syotot;
		this.jaahyt = jaahyt;
		this.plusmiinus = plusmiinus;
		this.ottelut = ottelut;
		this.pisteet = maalit + syotot;
	}
	
	public String getNimi() {
		return nimi;
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

	public Pelaaja() {
		
	}
	
	/**
	 * Luetaan tiedostosta pelaajat rivi kerrallaan
	 * @param rivi Pelaajan tiedot
	 */
	public void parse(String rivi) {
		StringBuffer sb = new StringBuffer(rivi);
		id = Mjonot.erota(sb, '|', id);
		nimi = Mjonot.erota(sb, '|', nimi);
		maalit = Mjonot.erota(sb, '|', maalit);
		syotot = Mjonot.erota(sb, '|', syotot);
		pisteet = maalit + syotot;
		jaahyt = Mjonot.erota(sb, '|', jaahyt);
		plusmiinus = Mjonot.erota(sb, '|', plusmiinus);
		ottelut = Mjonot.erota(sb, '|', ottelut);
	}
	

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return id + "|" + nimi + "|" + ottelut + "|" + maalit + "|" + syotot + "|" + plusmiinus + "|" + jaahyt + "|";
	}
	
	public void tulosta(PrintStream out) {
		out.println("Nimi : " + nimi);
		out.println("Maalit : " + maalit);
		out.println("Sy�t�t : " + syotot);
		out.println("Pisteet : " + pisteet);
		out.println("Plus/Miinus : " + plusmiinus);
		out.println("J��hyminuutit : " + jaahyt);	
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
