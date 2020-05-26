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
	 * Muodostaja tyhjälle pelaajalle, jonka tiedot täytetään käyttöliittymässä
	 */
	public Pelaaja() {
		
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
	 * Asetetaan pelaajalle pisteet maalien ja syöttöjen perusteella
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

	/**
	 * @return nimi
	 */
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
	 * @return the maalit
	 */
	public int getMaalit() {
		return maalit;
	}

	/**
	 * @return the syotot
	 */
	public int getSyotot() {
		return syotot;
	}

	/**
	 * @return the jaahyt
	 */
	public int getJaahyt() {
		return jaahyt;
	}

	/**
	 * @return the plusmiinus
	 */
	public int getPlusmiinus() {
		return plusmiinus;
	}

	/**
	 * @return the ottelut
	 */
	public int getOttelut() {
		return ottelut;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	
	/**
	 * Luetaan tiedostosta pelaajan tiedot rivi kerrallaan
	 * @param rivi Pelaajan tiedot
	 * 
	 * @example
	 * <pre name="test">
	 * Pelaaja pelaaja = new Pelaaja();
	 * pelaaja.parse("1|test|1|1|1|1|1|1|");
	 * pelaaja.toString().equals("1|test|1|1|1|1|1|1|");
	 * 
	 * </pre>
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
	
	/**
	 * Asetetaan pelaajalle tiedot testaamista varten
	 */
	public void testiTiedot() {
		this.nimi = "testi";
		this.maalit = 1;
		this.syotot = 1;
		this.pisteet = this.maalit + this.syotot;
		this.jaahyt = 1;
		this.plusmiinus = 1;
		this.ottelut = 1;
		this.id = 1;
	}
	

	/**
	 * Tulostus pelaajan tietojen tallennusta varten
	 * 
	 * @example
	 * <pre name="test">
	 * Pelaaja pelaaja = new Pelaaja();
	 * pelaaja.parse("1|test|1|1|1|1|1|1|");
	 * pelaaja.toString().startsWith("1|test|1") === true;
	 * 
	 * </pre>
	 */
	@Override
	public String toString() {
		return id + "|" + nimi + "|" + ottelut + "|" + maalit + "|" + syotot + "|" + plusmiinus + "|" + jaahyt + "|";
	}
	
	
	/**
	 * Tulostus pelaajan tietojen näyttämiseksi käyttöliittymässä
	 * @param out minne tulostetaan
	 */
	public void tulosta(PrintStream out) {
		out.println("Nimi : " + nimi);
		out.println("Ottelut : " + ottelut);
		out.println("Maalit : " + maalit);
		out.println("Syötöt : " + syotot);
		out.println("Pisteet : " + pisteet);
		out.println("Plus/Miinus : " + plusmiinus);
		out.println("Jäähyminuutit : " + jaahyt);	
	}
	
	
	/**
	 * Equals metodi, jolla tarkastetaan viitataanko samaan pelaajaan
	 */
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

	/* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        // TODO Auto-generated method stub
        return super.hashCode();
    }

    /**
	 * Testiohjelma pelaajalle
	 * @param args ei käytössä
	 */
	public static void main(String[] args) {
		Pelaaja pelaaja = new Pelaaja();
		pelaaja.parse("0|testi|1|2|3|4|5|6");
		pelaaja.tulosta(System.out);

	}

}
