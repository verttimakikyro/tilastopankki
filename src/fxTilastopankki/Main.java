/**
 * 
 */
package fxTilastopankki;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * @author Vertti Mäkikyrö
 *
 */
public class Main extends Application{
	@Override
	public void start(Stage primaryStage) {
		try {
			final FXMLLoader ldr = new FXMLLoader(getClass().getResource("TilastopankkiGUIView.fxml"));
			final Pane root = (Pane)ldr.load();
			final TilastopankkiGUIController tilastopankkiCtrl = (TilastopankkiGUIController)ldr.getController();
			
			final Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Tilastopankki");
			
			primaryStage.setOnCloseRequest((event) -> {
			    tilastopankkiCtrl.tallennetaanko();    
			});
			
			Joukkueet joukkueet = new Joukkueet();
			tilastopankkiCtrl.setJoukkueet(joukkueet);
			
			primaryStage.show();
			tilastopankkiCtrl.lueTiedosto();
			tilastopankkiCtrl.haeJoukkueet();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param args ei käytössä
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
