/**
 * 
 */
package fxTilastopankki;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import fxTilastopankki.Pelaaja;

/**
 * Kysyt��n pelaajan tiedot ja luodaan t�t� varten dialogi.
 * 
 * @author Vertti M�kikyr�
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
		
		try {
		vastaus.setOttelut(Integer.valueOf(textOttelut.getText()));
		} catch (NumberFormatException e) {
			Dialogs.showMessageDialog("Virheellinen sy�te kent�ss� ottelut!");
			vastaus = null;
			return;
		}
		
		try {
		vastaus.setMaalit(Integer.valueOf(textMaalit.getText()));
		} catch (NumberFormatException e) {
			Dialogs.showMessageDialog("Virheellinen sy�te kent�ss� maalit!");
			vastaus = null;
			return;
		}
		
		try {
		vastaus.setSyotot(Integer.valueOf(textSyotot.getText()));
		} catch (NumberFormatException e) {
			Dialogs.showMessageDialog("Virheellinen sy�te kent�ss� sy�t�t!");
			vastaus = null;
			return;
		}
		
		try {
		vastaus.setPlusmiinus(Integer.valueOf(textPlusMiinus.getText()));
		} catch (NumberFormatException e) {
			Dialogs.showMessageDialog("Virheellinen sy�te kent�ss� Plus/Miinus!");
			vastaus = null;
			return;
		}
		
		try {
		vastaus.setJaahyt(Integer.valueOf(textJaahyt.getText()));
		} catch (NumberFormatException e) {
			Dialogs.showMessageDialog("Virheellinen sy�te kent�ss� j��hyt!");
			vastaus = null;
			return;
		}
		vastaus.setPisteet();
		ModalController.closeStage(textNimi);
	}
	
	@FXML private void handleCancel() {
		ModalController.closeStage(textNimi);
	}
	
	
	/**
	 * Luodaan tietojen kysymysdialogi ja palautetaan pelaaja tai null
	 * @param modalityStage mille ollaan modaalisia, null = sovellukselle
	 * @param vastaus null jos painetaan Cancel, muuten pelaaja
	 * @return
	 */
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

	
	/**
	 * Mit� tehd��n, kun dialogi n�ytetty
	 */
	@Override
	public void handleShown() {
		textNimi.requestFocus();
		
	}
	
}
