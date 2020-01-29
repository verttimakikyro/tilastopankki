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
 * @author Vertti M�kikyr�
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


	@Override
	public void handleShown() {
		textfieldJoukkue.requestFocus();
	}


	@Override
	public void setDefault(String oletus) {
		textfieldJoukkue.setText(oletus);
		
	}
	
	public static String kysyNimi(Stage modalityStage, String oletus) {
		return ModalController.showModal(
				JoukkueenNimiController.class.getResource("LisaaJoukkue.fxml"),
				"Joukkue",
				modalityStage, oletus);
	}

}