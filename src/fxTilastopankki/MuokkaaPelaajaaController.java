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

/**
 * Muokataan pelaajan tietoja ja luodaan t�t� varten dialogi.
 * @author Vertti M�kikyr�
 *
 */
public class MuokkaaPelaajaaController implements ModalControllerInterface<Pelaaja>{

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


	@Override
	public void setDefault(Pelaaja oletus) {
		vastaus = oletus;
		textNimi.setText(oletus.getNimi());
		textOttelut.setText(String.valueOf(oletus.getOttelut()));
		textMaalit.setText(String.valueOf(oletus.getMaalit()));
		textSyotot.setText(String.valueOf(oletus.getSyotot()));
		textPlusMiinus.setText(String.valueOf(oletus.getPlusmiinus()));
		textJaahyt.setText(String.valueOf(oletus.getJaahyt()));
	}
	
	
	/**
	 * Luodaan tietojen kysymysdialogi ja palautetaan pelaajan uudet tiedot tai null
	 * @param modalityStage Mille ollaan modaalisia, null = sovellukselle
	 * @param vastaus Mit� tietoja k�ytet��n oletuksena
	 * @return null, jos painetaan Cancel, muuten pelaaja uusilla tiedoilla
	 */
	public static Pelaaja naytaPelaaja(Stage modalityStage, Pelaaja vastaus) {
		return ModalController.showModal(
				PelaajanTiedotController.class.getResource("MuokkaaPelaajaa.fxml"),
				"Pelaaja",
				modalityStage, vastaus);
	}

}
