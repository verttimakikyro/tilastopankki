/**
 * 
 */
package fxTilastopankki;

import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Kysytään joukkueen nimi ja luodaan tätä varten dialogi.
 * 
 * @author Vertti Mäkikyrö
 *
 */
public class JoukkueenNimiController implements ModalControllerInterface<String> {
	
	@FXML private TextField textfieldJoukkue;
	private String vastaus = null;
	
	@FXML private void handleOK() {
		vastaus = textfieldJoukkue.getText();
		ModalController.closeStage(textfieldJoukkue);
	}
	
	@FXML private void handleCancel() {
		ModalController.closeStage(textfieldJoukkue);
	}


	@Override
	public String getResult() {		
		return vastaus;
	}


	/**
	 * Mitä tehdään, kun dialogi on näytetty
	 */
	@Override
	public void handleShown() {
		textfieldJoukkue.requestFocus();
	}


	@Override
	public void setDefault(String oletus) {
		textfieldJoukkue.setText(oletus);
		
	}
	
	
	/**
	 * Luodaan nimen kysymis dialogi ja palautetaan annettu nimi tai null
	 * @param modalityStage mille ollaan modaalisia, null = sovellukselle
	 * @param oletus Mitä nimeä käytetään oletuksena
	 * @return null jos painetaan Cancel, muuten annettu nimi
	 */
	public static String kysyNimi(Stage modalityStage, String oletus) {
		return ModalController.showModal(
				JoukkueenNimiController.class.getResource("LisaaJoukkue.fxml"),
				"Joukkue",
				modalityStage, oletus);
	}

}
