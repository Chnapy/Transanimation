/*
 * 
 * 
 * 
 */
package Test;

import Elements.Element;
import Elements.actions.CustomAction;
import Elements.actions.CustomAction.CustomTransition;
import Elements.background.Background;
import Elements.camera.CameraBox;
import Moteur.Transanimation;
import Elements.animate.SpeakAnimable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Interpolator;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;
import org.xml.sax.SAXException;
import taml.TAML;

/**
 *
 * @author Richard
 */
public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		primaryStage.close();

		//Création du moteur
		Transanimation moteur = new Transanimation("Prototype");

		//Vérifie la résolution
		moteur.alertResolution();

		//Récupération de la caméra
		CameraBox camera = moteur.getCamera();

		//Création, puis ajout des éléments au moteur
//		Background bg0 = new Background("assets/graphics/bg/bg0.jpg");
//		moteur.addElements(bg0);

//		SpeakAnimable animeSprite = new SpeakAnimable("assets/test/test.scml", 60, 120, 120, 180);
//		moteur.addElements(animeSprite);

		//Positionnement initial
//		animeSprite.setPosition(50, 910, true);
		camera.setPosition(0, 0);

		//Récupération TAML
		try {
			//Initialisation du module TAML
			TAML.init();

			//Récupération des éléments
			HashMap<String, Pair<Elements.Element, ArrayList<Integer>>> TAMLret = TAML.getElementsFromTAML("assets/taml/test.taml");
			
			//Ajout des actions custom
			TAMLret.get("mcman").getValue().forEach((i) -> {
				switch (i) {
					case 0:
						TAMLret.get("mcman").getKey().addAction(new CustomAction(new CustomTransition(Duration.millis(3000)), Interpolator.LINEAR), i);
						break;
				}
			});
			
			//Ajouts des éléments au moteur
			TAMLret.values().forEach((p) -> {
				moteur.addElements(p.getKey());
			});
		} catch (IOException | SAXException ex) {
			throw new Error("Fichier .taml introuvable");
		}

		//Ajout des actions
		/*animeSprite
		 .speakPause(3000)
		 .pause(1000)
		 .move(1320, 0, Interpolator.EASE_BOTH, 2000)
		 .flip()
		 .speak("Moonwalk", Interpolator.LINEAR, 3000)
		 .speak("B)", Interpolator.LINEAR, 2000)
		 .move(960, 0, Interpolator.LINEAR, 5000)
		 .flip()
		 .speakPause(2300)
		 .move(400, 0, Interpolator.EASE_OUT, 2500)
		 .speak("Des petits sauts...", Interpolator.LINEAR, 1500)
		 .flip()
		 .move(-50, -40, Interpolator.LINEAR, 250)
		 .move(-50, 40, Interpolator.LINEAR, 200)
		 .move(-50, -40, Interpolator.LINEAR, 250)
		 .move(-50, 40, Interpolator.LINEAR, 200)
		 .pause(2000)
		 .speakPause(200)
		 .speak("Téléportation dans...", Interpolator.LINEAR, 1400)
		 .speak("3", Interpolator.LINEAR, 1000)
		 .speak("2", Interpolator.LINEAR, 1000)
		 .speak("1", Interpolator.LINEAR, 1000)
		 .move(800, 0, Interpolator.DISCRETE, 3000)
		 .speakPause(500)
		 .speak(":3", Interpolator.LINEAR, 1000)
		 .speakPause(1000)
		 .speak("Attention, ca va foncer", Interpolator.LINEAR, 2000)
		 .pause(4500)
		 .move(-3200, 0, Interpolator.EASE_BOTH, 3000)
		 .pause(1000)
		 .flip()
		 .speakPause(6000)
		 .custom(new CustomTransition(Duration.millis(3000)), Interpolator.LINEAR)
		 .speak("Fin", Interpolator.LINEAR, 20000);*/
		camera
				.pause(1200)
				.move(840, 0, Interpolator.EASE_BOTH, 2000)
				.pause(4800)
				.move(840, 0, Interpolator.EASE_OUT, 2500)
				.pause(10400)
				.move(-1680, 0, Interpolator.EASE_BOTH, 3000);

		//Debug
//		moteur.goToDuration(9000);
//		ScenicView.show(moteur.getScene());
		//Affichage et lancement
		moteur.setFullScreen(true);
		moteur.show();
		moteur.start();
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
