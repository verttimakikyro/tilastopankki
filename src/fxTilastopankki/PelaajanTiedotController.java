/**
 * 
 */
package fxTilastopankki;

import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import fxTilastopankki.Pelaaja;

/**
 * @author Vertti Mäkikyrö
 *
 */
public class PelaajanTiedotController implements ModalControllerInterface<Pelaaja>{
	
	@FXML private TextField textNimi;
	@FXML private TextField textOttelut;
	@FXML private TextField textMaalit;
	@FXML private TextField textSyotot;
	@FXML private TextField textPlusMiinus;
	@FXML private TextField textJaahyt;
	
	private Pelaaja vastaus = new Pelaaja();

		
	@FXML private void handleOK() {
		vastaus.setNimi(textNimi.getText()); 
		vastaus.setOttelut(Integer.valueOf(textOttelut.getText()));
		vastaus.setMaalit(Integer.valueOf(textMaalit.getText()));
		vastaus.setSyotot(Integer.valueOf(textSyotot.getText()));
		vastaus.setPlusmiinus(Integer.valueOf(textPlusMiinus.getText()));
		vastaus.setJaahyt(Integer.valueOf(textJaahyt.getText()));
		vastaus.setPisteet();
		ModalController.closeStage(textNimi);
	}
	
	@FXML private void handleCancel() {
		ModalController.closeStage(textNimi);
	}
	
	
	public static Pelaaja kysyPelaaja(Stage modalityStage, Pelaaja vastaus) {
		return ModalController.showModal(
				PelaajanTiedotController.class.getResource("UusiPelaaja.fxml"),
				"Pelaaja",
				modalityStage, vastaus);
	}


	@Override
	public void setDefault(Pelaaja oletus) {
		vastaus = oletus;
		
	}


	@Override
	public Pelaaja getResult() {
		
		return vastaus;
	}


	@Override
	public void handleShown() {
		textNimi.requestFocus();
		
	}
	
}
