/**
 * 
 */
package fxTilastopankki;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
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
	
	@FXML private void handleTallenna() throws FileNotFoundException, UnsupportedEncodingException {
		joukkueet.tallenna();
		Dialogs.showMessageDialog("Tallennus onnistui");
	}
	
	
//==================================================//
// Tästä eteenpäin ei suoraan käyttöliitymään liittyvää koodia
	
	private Joukkueet joukkueet;
	private Pelaaja pelaajaKohdalla;
	private Joukkue joukkueKohdalla;
	private TextArea areaPelaaja = new TextArea();
	private String joukkueenNimi;
	
	/**
	 * Alustetaan joukkue- ja pelaajalistan kuuntelijat
	 */
	public void alusta() {
		chooserJoukkueet.addSelectionListener(e -> haePelaajat());
		chooserPelaajat.addSelectionListener(e -> naytaPelaaja());
		panelPelaajat.setContent(areaPelaaja);
	}
	
	/**
	 * Asetetaan joukkueet
	 * @param joukkueet Olio joukkueista
	 */
	public void setJoukkueet(Joukkueet joukkueet) {
		this.joukkueet = joukkueet;
	}
	
	/**
	 * Luetaan joukkueet ja pelaajat tiedostoista
	 * Pelaajat asetetaan oikeaan joukkueeseen
	 */
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
	
	/**
	 * Näytetään valitun pelaajan tiedot
	 */
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
	 * Haetaan tietyn joukkueen pelaajat listalle
	 */
	protected void haePelaajat() {
		areaPelaaja.clear();
		chooserPelaajat.clear();
		for(Pelaaja pelaaja : chooserJoukkueet.getSelectedObject().getPelaajat()) {
			chooserPelaajat.add(pelaaja.getNimi(), pelaaja);
		}
	}
	
	public void tulostaValitut(TextArea text) {
		try (PrintStream os = TextAreaOutputStream.getTextPrintStream(text)) {
			
		}
	}
	
	
	/**
	 * Pelaajan tietojen muokkaus
	 */
	private void muokkaaPelaajaa() {
		pelaajaKohdalla = chooserPelaajat.getSelectedObject();
		if(pelaajaKohdalla == null) return;
		
		
		Pelaaja muokattu = new Pelaaja();
		muokattu = MuokkaaPelaajaaController.naytaPelaaja(null, pelaajaKohdalla);
		if(muokattu == null) return;
		
		for(Joukkue joukkue : joukkueet.getJoukkueet()) {
			for(Pelaaja pelaaja : joukkue.getPelaajat()) {
				if(pelaaja.equals(pelaajaKohdalla)) {
					pelaaja = muokattu;
					break;
				}
			}
		}
		areaPelaaja.clear();
		haePelaajat();
	}
	
	/**
	 * Lisää uuden pelaajan valittuun joukkueeseen
	 */
	private void uusiPelaaja() {
		joukkueKohdalla = chooserJoukkueet.getSelectedObject();
		if (joukkueKohdalla == null) return;
		
		Pelaaja uusi = new Pelaaja();
		uusi = PelaajanTiedotController.kysyPelaaja(null, uusi);
		
		if(uusi.getNimi() == null) return;
		
		int id = joukkueKohdalla.getId();
		uusi.setId(id);
		
		for(Joukkue joukkue : joukkueet.getJoukkueet()) {
			if(joukkue.getId() == id) {
				joukkue.lisaaPelaaja(uusi);
				break;
			}
		}
		areaPelaaja.clear();
		haePelaajat();
	}
	
	
	/**
	 * Poistaa valitun pelaajan
	 */
	private void poistaPelaaja() {
		pelaajaKohdalla = chooserPelaajat.getSelectedObject();
		for(Joukkue joukkue : joukkueet.getJoukkueet()) {
			for(int i = joukkue.getPelaajat().size() - 1; i >= 0; i--) {
				if(joukkue.getPelaajat().get(i).equals(pelaajaKohdalla)) {
					joukkue.getPelaajat().remove(pelaajaKohdalla);
				}
			}
		}
		areaPelaaja.clear();
		haePelaajat();
	}
	
	
	/**
	 * Pelaajan etsintä nimen perusteella
	 * @param hakuehto Pelaajan nimi tai nimen osa
	 */
	public void etsiPelaaja(String hakuehto) {
		areaPelaaja.clear();
		chooserPelaajat.clear();
		for(Joukkue joukkue : joukkueet.getJoukkueet()) {
			for(Pelaaja pelaaja : joukkue.getPelaajat()) {
				if(pelaaja.getNimi().contains(hakuehto)) chooserPelaajat.add(pelaaja.getNimi(), pelaaja);
			}
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
		areaPelaaja.clear();
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

	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}
