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
 * @author Vertti M�kikyr�
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
			
			Joukkueet joukkueet = new Joukkueet();
			tilastopankkiCtrl.setJoukkueet(joukkueet);
			
			primaryStage.show();
			//tilastopankkiCtrl.lueTiedosto();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		launch(args);
	}

}
