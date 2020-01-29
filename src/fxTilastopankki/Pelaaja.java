/**
 * 
 */
package fxTilastopankki;



import java.io.PrintStream;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * @author Vertti Mäkikyrö
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
	
	/**
	 * @param nimi the nimi to set
	 */
	public void setNimi(String nimi) {
		this.nimi = nimi;
	}

	/**
	 * @param maalit the maalit to set
	 */
	public void setMaalit(int maalit) {
		this.maalit = maalit;
	}

	/**
	 * @param syotot the syotot to set
	 */
	public void setSyotot(int syotot) {
		this.syotot = syotot;
	}

	/**
	 * @param pisteet the pisteet to set
	 */
	public void setPisteet() {
		this.pisteet = maalit + syotot;
	}

	/**
	 * @param jaahyt the jaahyt to set
	 */
	public void setJaahyt(int jaahyt) {
		this.jaahyt = jaahyt;
	}

	/**
	 * @param plusmiinus the plusmiinus to set
	 */
	public void setPlusmiinus(int plusmiinus) {
		this.plusmiinus = plusmiinus;
	}

	/**
	 * @param ottelut the ottelut to set
	 */
	public void setOttelut(int ottelut) {
		this.ottelut = ottelut;
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
		return id + "|" + nimi + "|" + ottelut + "|" + maalit + "|" + syotot + "|" + plusmiinus + "|" + jaahyt + "|" + "\n";
	}
	
	public void tulosta(PrintStream out) {
		out.println("Nimi : " + nimi);
		out.println("Maalit : " + maalit);
		out.println("Syötöt : " + syotot);
		out.println("Pisteet : " + pisteet);
		out.println("Plus/Miinus : " + plusmiinus);
		out.println("Jäähyminuutit : " + jaahyt);	
	}
	
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pelaaja other = (Pelaaja) obj;
		if (id != other.id)
			return false;
		if (jaahyt != other.jaahyt)
			return false;
		if (maalit != other.maalit)
			return false;
		if (nimi == null) {
			if (other.nimi != null)
				return false;
		} else if (!nimi.equals(other.nimi))
			return false;
		if (ottelut != other.ottelut)
			return false;
		if (pisteet != other.pisteet)
			return false;
		if (plusmiinus != other.plusmiinus)
			return false;
		if (syotot != other.syotot)
			return false;
		return true;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
