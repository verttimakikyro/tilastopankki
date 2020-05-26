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
 * Kysytään pelaajan tiedot ja luodaan tätä varten dialogi.
 * 
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
	private Pelaaja vastaus = null;

		
	@FXML private void handleOK() {
	    vastaus = new Pelaaja();
	    
	    if(textNimi.getText().equals("")) {
	        Dialogs.showMessageDialog("Anna pelaajalle nimi!");
	        vastaus = null;
	        return;
	    }
	    
		vastaus.setNimi(textNimi.getText()); 
	   
		try {
		if(Integer.valueOf(textOttelut.getText()) < 0) {
		    Dialogs.showMessageDialog("Ottelut eivät voi olla miinuksella!");
		    vastaus = null;
		    return;
		}
		vastaus.setOttelut(Integer.valueOf(textOttelut.getText()));
		} catch (NumberFormatException e) {
			Dialogs.showMessageDialog("Virheellinen syöte kentässä ottelut!");
			vastaus = null;
			return;
		}
		
		try {
		    if(Integer.valueOf(textMaalit.getText()) < 0) {
		        Dialogs.showMessageDialog("Maalit eivät voi olla miinuksella!");
		        vastaus = null;
		        return;
		    }
		    vastaus.setMaalit(Integer.valueOf(textMaalit.getText()));
		} catch (NumberFormatException e) {
			Dialogs.showMessageDialog("Virheellinen syöte kentässä maalit!");
			vastaus = null;
			return;
		}
		
		try {
		    if(Integer.valueOf(textSyotot.getText()) < 0) {
		        Dialogs.showMessageDialog("Syötöt eivät voi olla miinuksella!");
	            vastaus = null;
	            return;
		    }
		    vastaus.setSyotot(Integer.valueOf(textSyotot.getText()));
		} catch (NumberFormatException e) {
			Dialogs.showMessageDialog("Virheellinen syöte kentässä syötöt!");
			vastaus = null;
			return;
		}
		
		try {
		vastaus.setPlusmiinus(Integer.valueOf(textPlusMiinus.getText()));
		} catch (NumberFormatException e) {
			Dialogs.showMessageDialog("Virheellinen syöte kentässä Plus/Miinus!");
			vastaus = null;
			return;
		}
		
		try {
		    if(Integer.valueOf(textJaahyt.getText()) < 0) {
                Dialogs.showMessageDialog("Jäähyt eivät voi olla miinuksella!");
                vastaus = null;
                return;
            }
		vastaus.setJaahyt(Integer.valueOf(textJaahyt.getText()));
		} catch (NumberFormatException e) {
			Dialogs.showMessageDialog("Virheellinen syöte kentässä jäähyt!");
			vastaus = null;
			return;
		}
		vastaus.setPisteet();
		ModalController.closeStage(textJaahyt);
	}
	
	@FXML private void handleCancel() {
	    vastaus = null;
		ModalController.closeStage(textNimi);
	}
	
	
	/**
	 * Luodaan tietojen kysymysdialogi ja palautetaan pelaaja tai null
	 * @param modalityStage mille ollaan modaalisia, null = sovellukselle
	 * @param vastaus null jos painetaan Cancel, muuten pelaaja
	 * @return Pelaaja tai null
	 */
	public static Pelaaja kysyPelaaja(Stage modalityStage, Pelaaja vastaus) {
		return ModalController.showModal(
				PelaajanTiedotController.class.getResource("UusiPelaaja.fxml"),
				"Pelaaja",
				modalityStage, vastaus);
	}


	@Override
	public void setDefault(Pelaaja oletus) {
		vastaus = null;
		
	}


	@Override
	public Pelaaja getResult() {
		return vastaus;
	}

	
	/**
	 * Mitä tehdään, kun dialogi näytetty
	 */
	@Override
	public void handleShown() {
		textNimi.requestFocus();
		
	}
	
}
