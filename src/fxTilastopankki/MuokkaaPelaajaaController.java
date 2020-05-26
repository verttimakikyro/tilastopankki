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
 * Muokataan pelaajan tietoja ja luodaan tätä varten dialogi.
 * @author Vertti Mäkikyrö
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
	    
	    if(textNimi.getText().equals("")) {
	        Dialogs.showMessageDialog("Anna pelaajalle nimi!");
	        return;
	    }
		vastaus.setNimi(textNimi.getText()); 
		
		try {
		    if(Integer.valueOf(textOttelut.getText()) < 0) {
		        Dialogs.showMessageDialog("Ottelut eivät voi olla miinuksella!");
		         return;
		     }
		vastaus.setOttelut(Integer.valueOf(textOttelut.getText()));
		} catch (NumberFormatException e) {
			Dialogs.showMessageDialog("Virheellinen syöte kentässä ottelut!");
			return;
		}
		
		try {
	        if(Integer.valueOf(textMaalit.getText()) < 0) {
	            Dialogs.showMessageDialog("Maalit eivät voi olla miinuksella!");
	            return;
	         }
		vastaus.setMaalit(Integer.valueOf(textMaalit.getText()));
		} catch (NumberFormatException e) {
			Dialogs.showMessageDialog("Virheellinen syöte kentässä maalit!");
			return;
		}
		
		try {
	          if(Integer.valueOf(textSyotot.getText()) < 0) {
	                Dialogs.showMessageDialog("Syötöt eivät voi olla miinuksella!");
	                 return;
	             }
		vastaus.setSyotot(Integer.valueOf(textSyotot.getText()));
		} catch (NumberFormatException e) {
			Dialogs.showMessageDialog("Virheellinen syöte kentässä syötöt!");
			return;
		}
		
		try {
		vastaus.setPlusmiinus(Integer.valueOf(textPlusMiinus.getText()));
		} catch (NumberFormatException e) {
			Dialogs.showMessageDialog("Virheellinen syöte kentässä Plus/Miinus!");
			return;
		}
		
		try {
	          if(Integer.valueOf(textJaahyt.getText()) < 0) {
	                Dialogs.showMessageDialog("Jäähyt eivät voi olla miinuksella!");
	                 return;
	             }
		vastaus.setJaahyt(Integer.valueOf(textJaahyt.getText()));
		} catch (NumberFormatException e) {
			Dialogs.showMessageDialog("Virheellinen syöte kentässä jäähyt!");
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
	 * Mitä tehdään, kun dialogi näytetty
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
	 * @param vastaus Mitä tietoja käytetään oletuksena
	 * @return null, jos painetaan Cancel, muuten pelaaja uusilla tiedoilla
	 */
	public static Pelaaja naytaPelaaja(Stage modalityStage, Pelaaja vastaus) {
		return ModalController.showModal(
				PelaajanTiedotController.class.getResource("MuokkaaPelaajaa.fxml"),
				"Pelaaja",
				modalityStage, vastaus);
	}

}
