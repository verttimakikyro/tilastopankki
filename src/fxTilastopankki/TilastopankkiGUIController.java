/**
 * 
 */
package fxTilastopankki;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.TextAreaOutputStream;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;

/**
 * @author Vertti Mäkikyrö
 *
 */
public class TilastopankkiGUIController implements Initializable {
	
	@FXML private ListChooser<Pelaaja> chooserPelaajat;
	@FXML private ListChooser<Joukkue> chooserJoukkueet;
	@FXML private ScrollPane panelPelaajat;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		alusta();	
	}
	
	@FXML private void handleLisaaPelaaja() {
		ModalController.showModal(TilastopankkiGUIController.class.getResource("LisaaPelaaja.fxml"), "Pelaaja", null, "");
	}
	
	
	
	
	
	
	
	
	
	
//==================================================//
	private Joukkueet joukkueet;
	private Pelaaja pelaajaKohdalla;
	private Joukkue joukkueKohdalla;
	private TextArea areaPelaaja = new TextArea();
	
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
	
	protected void haeJoukkueet() {
		chooserJoukkueet.clear();
		for(Joukkue joukkue : joukkueet.getJoukkueet()) {
			chooserJoukkueet.add(joukkue.getNimi(), joukkue);
		}
	}
	
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


	
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */

}
