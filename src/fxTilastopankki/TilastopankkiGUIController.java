/**
 * 
 */
package fxTilastopankki;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.ListChooser;
import fxTiimipankki.Pelaaja;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * @author Vertti Mäkikyrö
 *
 */
public class TilastopankkiGUIController implements Initializable {
	
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	
	
	
	
	
	
//==================================================//
	private Joukkueet joukkueet;
	
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
