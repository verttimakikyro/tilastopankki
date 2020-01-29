/**
 * 
 */
package fxTilastopankki;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.TextAreaOutputStream;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


/**
 * @author Vertti Mäkikyrö
 * Luokka käyttöliittymän tapahtumien hoitamiseksi
 */
public class TilastopankkiGUIController implements Initializable {
	
	@FXML private ListChooser<Pelaaja> chooserPelaajat;
	@FXML private ListChooser<Joukkue> chooserJoukkueet;
	@FXML private ScrollPane panelPelaajat;
	@FXML private TextField textfieldJoukkue;
	@FXML private TextField textHakuehto;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		alusta();	
	}
	
	@FXML private void handleLisaaPelaaja() {
		uusiPelaaja();
	}
	
	@FXML private void handleMuokkaaPelaajaa() {
		muokkaaPelaajaa();
	}
	
	@FXML private void handlePoistaPelaaja() {
		poistaPelaaja();
	}
	
	@FXML private void handleLisaaJoukkue() {
		uusiJoukkue();
	}
	
	@FXML private void handlePoistaJoukkue() {
		poistaJoukkue();
	}
	
	@FXML private void handleHae() {
		etsiPelaaja(textHakuehto.getText());
	}
	
	
//==================================================//
	private Joukkueet joukkueet;
	private Pelaaja pelaajaKohdalla;
	private Joukkue joukkueKohdalla;
	private TextArea areaPelaaja = new TextArea();
	private String joukkueenNimi;
	
	
	public void alusta() {
		chooserJoukkueet.addSelectionListener(e -> haePelaajat());
		chooserPelaajat.addSelectionListener(e -> naytaPelaaja());
		panelPelaajat.setContent(areaPelaaja);
	}
	
	public void setJoukkueet(Joukkueet joukkueet) {
		this.joukkueet = joukkueet;
	}
	
	public void lueTiedosto() {
		
		try {
			joukkueet.lueTiedosto();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(Joukkue joukkue : joukkueet.getJoukkueet()) {
			try {
				joukkue.lueTiedosto();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	protected void naytaPelaaja() {
		pelaajaKohdalla = chooserPelaajat.getSelectedObject();
		if(pelaajaKohdalla == null) return;
		
		areaPelaaja.setText("");
		try(PrintStream os = TextAreaOutputStream.getTextPrintStream(areaPelaaja)) {
			pelaajaKohdalla.tulosta(os);
		}
	}
	
	
	/**
	 * Haetaan joukkueet listalle
	 */
	protected void haeJoukkueet() {
		chooserJoukkueet.clear();
		for(Joukkue joukkue : joukkueet.getJoukkueet()) {
			chooserJoukkueet.add(joukkue.getNimi(), joukkue);
		}
	}
	
	/**
	 * Haetaan pelaajat listalle
	 */
	protected void haePelaajat() {
		chooserPelaajat.clear();
		for(Pelaaja pelaaja : chooserJoukkueet.getSelectedObject().getPelaajat()) {
			chooserPelaajat.add(pelaaja.getNimi(), pelaaja);
		}
	}
	
	public void tulostaValitut(TextArea text) {
		try (PrintStream os = TextAreaOutputStream.getTextPrintStream(text)) {
			
		}
	}
	
	private void muokkaaPelaajaa() {
		pelaajaKohdalla = chooserPelaajat.getSelectedObject();
		if(pelaajaKohdalla == null) return;
		
		Pelaaja muokattu = new Pelaaja();
		muokattu = MuokkaaPelaajaaController.naytaPelaaja(null, pelaajaKohdalla);
		
		for(Joukkue joukkue : joukkueet.getJoukkueet()) {
			for(Pelaaja pelaaja : joukkue.getPelaajat()) {
				if(pelaaja.equals(pelaajaKohdalla)) {
					pelaaja = muokattu;
					break;
				}
			}
		}
		haePelaajat();
	}
	
	private void uusiPelaaja() {
		joukkueKohdalla = chooserJoukkueet.getSelectedObject();
		if (joukkueKohdalla == null) return;
		
		Pelaaja uusi = new Pelaaja();
		uusi = PelaajanTiedotController.kysyPelaaja(null, uusi);
		
		int id = joukkueKohdalla.getId();
		uusi.setId(id);
		
		for(Joukkue joukkue : joukkueet.getJoukkueet()) {
			if(joukkue.getId() == id) {
				joukkue.lisaaPelaaja(uusi);
				break;
			}
		}
		haePelaajat();
		try {
			joukkueet.tallennaPelaajat();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void poistaPelaaja() {
		pelaajaKohdalla = chooserPelaajat.getSelectedObject();
		for(Joukkue joukkue : joukkueet.getJoukkueet()) {
			for(int i = joukkue.getPelaajat().size() - 1; i >= 0; i--) {
				if(joukkue.getPelaajat().get(i).equals(pelaajaKohdalla)) {
					joukkue.getPelaajat().remove(pelaajaKohdalla);
				}
			}
		}
		haePelaajat();
		try {
			joukkueet.tallennaPelaajat();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void etsiPelaaja(String hakuehto) {
		ArrayList<Pelaaja> pelaajat = new ArrayList<>();
		for(Joukkue joukkue : joukkueet.getJoukkueet()) {
			for(Pelaaja pelaaja : joukkue.getPelaajat()) {
				if(pelaaja.getNimi().contains(hakuehto)) pelaajat.add(pelaaja);
			}
		}
		chooserPelaajat.clear();
		for(Pelaaja pelaaja : pelaajat) {
			chooserPelaajat.add(pelaaja.getNimi(), pelaaja);
		}
	}
	
	/**
	 * Lisätään uusi joukkue
	 * @param nimi, Joukkueen nimi 
	 */
	private void uusiJoukkue()  {
		String nimi = JoukkueenNimiController.kysyNimi(null, joukkueenNimi);
		if(nimi == null) return;
		Joukkue uusi = new Joukkue(nimi, joukkueet.seuraavaId());
		joukkueet.lisaaJoukkue(uusi);
		haeJoukkueet();
		try {
			joukkueet.tallennaJoukkue();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Metodi joukkueen poistamiseksi
	 */
	private void poistaJoukkue() {
		joukkueKohdalla = chooserJoukkueet.getSelectedObject();
		for(int i = joukkueet.getJoukkueet().size() - 1; i >= 0 ; i--) {
			if(joukkueet.getJoukkueet().get(i).equals(joukkueKohdalla)) {
				joukkueet.getJoukkueet().remove(joukkueKohdalla);
			}
		}
		
		chooserPelaajat.clear();
		areaPelaaja.clear();
		haeJoukkueet();
		try {
			joukkueet.tallennaJoukkue();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}
