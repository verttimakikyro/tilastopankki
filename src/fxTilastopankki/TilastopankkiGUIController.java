/**
 * 
 */
package fxTilastopankki;

import java.awt.Desktop;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.TextAreaOutputStream;
import javafx.application.Platform;
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
	
	@FXML private void handleMuokkaaJoukkue() {
	    uusiNimi();
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
	
	@FXML private void handleTallenna()  {
		if(joukkueet.tallenna("joukkueet.dat", "pelaajat.dat")) {
		onkoMuokattu = false;
		Dialogs.showMessageDialog("Tallennus onnistui!");
		}
	}
	
	@FXML private void handleLopeta() {
	    tallennetaanko();
	    Platform.exit();
	}
	
	@FXML private void handleApua() {
	    avustus();
	}
	
	
//==================================================//
// Tästä eteenpäin ei suoraan käyttöliitymään liittyvää koodia
	
	private Joukkueet joukkueet;
	private Pelaaja pelaajaKohdalla;
	private Joukkue joukkueKohdalla;
	private TextArea areaPelaaja = new TextArea();
	private String joukkueenNimi;
	private boolean onkoMuokattu = false;
	
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
	public void lueTiedosto()  {
		
		joukkueet.lueTiedosto("joukkueet.dat");
		for(Joukkue joukkue : joukkueet.getJoukkueet()) {
			try {
				joukkue.lueTiedosto("pelaajat.dat");
			} catch (Exception e) {
			  Dialogs.showMessageDialog("Virhe tiedoston lukemisessa " + e.getMessage());
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
	
	
	/**
	 * Pelaajan tietojen muokkaus
	 */
	private void muokkaaPelaajaa() {
	    
	    try {
	        pelaajaKohdalla = chooserPelaajat.getSelectedObject();
	    } catch (NullPointerException e) {
	        Dialogs.showMessageDialog("Pelaajaa ei ole valittu!");
	        return;
	    }
		
		Pelaaja muokattu = new Pelaaja();
		muokattu = MuokkaaPelaajaaController.naytaPelaaja(null, pelaajaKohdalla);
		if(muokattu == null) {
		    return;
		}
		
		muokattu.setId(chooserJoukkueet.getSelectedObject().getId());
				
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
		onkoMuokattu = true;
	}
	
	/**
	 * Lisää uuden pelaajan valittuun joukkueeseen
	 */
	private void uusiPelaaja() {
	      if(chooserJoukkueet.getSelectedObject() == null) {
	            Dialogs.showMessageDialog("Joukkuetta ei ole valittu!");
	            return;
	        }
		
		Pelaaja uusi = new Pelaaja();
		uusi = PelaajanTiedotController.kysyPelaaja(null, uusi);
		
		if(uusi == null) return;
		
		
		int id = chooserJoukkueet.getSelectedObject().getId();
		uusi.setId(id);
		
		for(Joukkue joukkue : joukkueet.getJoukkueet()) {
			if(joukkue.getId() == id) {
				joukkue.lisaaPelaaja(uusi);
				break;
			}
		}
		areaPelaaja.clear();
		haePelaajat();
		onkoMuokattu = true;
	}
	
	/**
	 * Ohjelmaa sulkiessa kysytään käyttäjältä tietojen tallennus
	 */
	public void tallennetaanko() {
	    if(onkoMuokattu) {
	    if(Dialogs.showQuestionDialog("Tallennus", "Haluatko tallentaa?", "Kyllä", "Ei")) joukkueet.tallenna("joukkueet.dat", "pelaajat.dat");
	    }
	}
	
	/**
	 * Poistaa valitun pelaajan
	 */
	private void poistaPelaaja() {
	    if(chooserPelaajat.getSelectedObject() == null) {
	        Dialogs.showMessageDialog("Pelaajaa ei ole valittu!");
	        return;
	    }
		pelaajaKohdalla = chooserPelaajat.getSelectedObject();
		for(Joukkue joukkue : joukkueet.getJoukkueet()) {
		    if(joukkue.getId() == pelaajaKohdalla.getId()) {
		        int lkm = 0;
		        Pelaaja pelaajat[] = new Pelaaja[0];
		        for(int i = 0; i < joukkue.getPelaajat().length; i++) {
		            if(joukkue.getPelaajat()[i].equals(pelaajaKohdalla)) continue;
		            pelaajat = Arrays.copyOf(pelaajat, lkm+1);
		            pelaajat[lkm] = joukkue.getPelaajat()[i];
		            lkm++;
		        }
		        joukkue.setPelaajat(pelaajat);
		    }
		}
		areaPelaaja.clear();
		haePelaajat();
		onkoMuokattu = true;
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
			    String[] nimi = pelaaja.getNimi().split(" ");
			    for(int i = 0; i < nimi.length; i++) {
			        if(nimi[i].toLowerCase().startsWith(hakuehto.toLowerCase())) { 
			            chooserPelaajat.add(pelaaja.getNimi(), pelaaja); 
			            break;
			        }
			    }
			}
		}
	}
	
	/**
	 * Lisätään uusi joukkue
	 * @param nimi Joukkueen nimi 
	 */
	private void uusiJoukkue()  {
		String nimi = JoukkueenNimiController.kysyNimi(null, joukkueenNimi);
		if(nimi == null) return;
		Joukkue uusi = new Joukkue(nimi, joukkueet.seuraavaId());
		joukkueet.lisaaJoukkue(uusi);
		haeJoukkueet();
		areaPelaaja.clear();
		onkoMuokattu = true;
	}
	   
	
	/**
	 * Metodi joukkueen nimen muuttamiselle
	 */
	private void uusiNimi()  {
	    if(chooserJoukkueet.getSelectedObject() == null) {
	        Dialogs.showMessageDialog("Joukkuetta ei ole valittu!");
	        return;
	    }
	    joukkueKohdalla = chooserJoukkueet.getSelectedObject();
	    String nimi = JoukkueenNimiController.kysyNimi(null, joukkueenNimi);
	    if(nimi == null) return;
	    joukkueKohdalla.setNimi(nimi);
	    haeJoukkueet();
	    onkoMuokattu = true;
	}
	
	
	/**
	 * Metodi joukkueen poistamiseksi
	 */
	private void poistaJoukkue() {
	    if(chooserJoukkueet.getSelectedObject() == null) {
            Dialogs.showMessageDialog("Joukkuetta ei ole valittu!");
            return;
        }
		joukkueKohdalla = chooserJoukkueet.getSelectedObject();
		for(int i = joukkueet.getJoukkueet().size() - 1; i >= 0 ; i--) {
			if(joukkueet.getJoukkueet().get(i).equals(joukkueKohdalla)) {
				joukkueet.getJoukkueet().remove(joukkueKohdalla);
			}
		}	
		chooserPelaajat.clear();
		areaPelaaja.clear();
		haeJoukkueet();
		onkoMuokattu = true;
	}

	/**
	 * Näytetään ohjelman suunnitelma selaimessa
	 */
	private void avustus() {
	    Desktop desktop = Desktop.getDesktop();
	    try {
	        URI uri = new URI("https://gitlab.jyu.fi/vesamaki/ohj2");
	        desktop.browse(uri);
	    } catch (URISyntaxException e) {
	        return;
	    } catch (IOException e) {
	        return;
	    }
	}

	/**
	 * @param args ei käytössä
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}
