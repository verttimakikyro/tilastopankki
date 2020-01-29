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
 * @author vertt
 *
 */
public class Joukkueet {
	
	private ArrayList<Joukkue> joukkueet = new ArrayList<>();
	private int nextID;
	
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
	

	
	public int seuraavaId() {
		if(joukkueet.isEmpty()) nextID = 1;
		else nextID = joukkueet.get(joukkueet.size() -1).getId() + 1;
		return nextID;
	}
	
	public void uusiJoukkue(String nimi) {
		joukkueet.add(new Joukkue(nimi, seuraavaId()));
	}
	
	public void lisaaJoukkue(Joukkue joukkue) {
		joukkueet.add(joukkue);
	}
	
	public boolean etsiJoukkue(int id) {
		for(Joukkue joukkue : joukkueet) {
			if(joukkue.getId() == id) return true;
		}
		return false;
	}

	/**
	 * @return the joukkueet
	 */
	public ArrayList<Joukkue> getJoukkueet() {
		return joukkueet;
	}
	
	public void tallennaJoukkue() throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter("joukkueet.dat", "UTF-8");
		writer.println(";Joukkueet");
		for(Joukkue joukkue : joukkueet) {
			writer.println(joukkue.getId() + "|" + joukkue.getNimi() + "|");
		}
		writer.close();
	}
	
	public void tallennaPelaajat() throws FileNotFoundException, UnsupportedEncodingException {
		StringBuilder sb = new StringBuilder();
		for(Joukkue joukkue : joukkueet) {
			sb.append(joukkue.tallennaPelaajat());
		}
		PrintWriter writer = new PrintWriter("pelaajat.dat", "UTF-8");
		writer.println(sb.toString());
		writer.close();
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}



}
