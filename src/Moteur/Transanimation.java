/*
 * 
 * 
 * 
 */
package Moteur;

import Elements.Element;
import Elements.background.Background;
import Elements.camera.CameraBox;
import Elements.animate.Animable;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import javafx.animation.AnimationTimer;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * Transanimation.java
 *
 */
public class Transanimation extends Stage {

	private static final String CSS_PATH = "assets/css/main.css";
	public static final boolean DEBUG = true;
	public static Point MAX_WINDOW_SIZE = new Point(1920, 1080);
	public static Point DEFAULT_WINDOW_SIZE = new Point(960, 540);
	
	final double SCALE_DELTA = 1.1;

	private final CameraBox root;
	private final Group group;
	private final Scene scene;
	private final AnimationTimer animeTimer;
	private final ArrayList<Element> elements;

	public Transanimation(String title) {

		root = new CameraBox();
		scene = new Scene(root, DEFAULT_WINDOW_SIZE.x, DEFAULT_WINDOW_SIZE.y);
		scene.getStylesheets().add("file:" + CSS_PATH);
		scene.setFill(Color.RED);

		root.getStyleClass().add("root");

		group = new Group();
		group.getStyleClass().add("group");
		root.getChildren().addAll(group);
		group.setLayoutX(0);
		group.setLayoutY(0);

		animeTimer = new AnimationTimer() {

			@Override
			public void handle(long l) {
				elements.stream().filter((e) -> (e instanceof Animable)).forEach((e) -> {
					animationUpdate((Animable) e);
				});
			}
		};

		elements = new ArrayList();
		elements.add(root);

		setTitle(title);
		setScene(scene);
	}

	private void animationUpdate(Animable anim) {
		anim.update();
	}

	public void start() {
		elements.forEach((el) -> {
			el.start();
		});
		animeTimer.start();
	}

	public void end() {
		animeTimer.stop();
		elements.forEach((el) -> {
			el.end();
		});
	}

	public void addElements(Element... elems) {
		for (Element el : elems) {
			try {
				if (el instanceof Background) {
					addBackgrounds((Background) el);
				} else {
					addElement(el);
				}
			} catch (Exception e) {
				System.err.println("addElements - un élément n'a pas pu être ajouté : " + el);
			}
		}
	}

	public void addElements(Collection<Element> elems) {
		elems.stream().forEach((el) -> {
			try {
				if (el instanceof Background) {
					addBackgrounds((Background) el);
				} else {
					addElement(el);
				}
			} catch (Exception e) {
				System.err.println("addElements - un élément n'a pas pu être ajouté : " + el);
			}
		});
	}

	public void goToDuration(long d) {
		elements.forEach((el) -> {
			el.goToDuration(d);
		});
	}

	private void addElement(Element el) throws Exception {
		group.getChildren().add((Node) el);
		elements.add(el);
	}

	private void addBackgrounds(Background bg) throws Exception {
		if (bg != null) {
			addElement(bg);
			addBackgrounds(bg.top());
			addBackgrounds(bg.right());
			addBackgrounds(bg.bottom());
			addBackgrounds(bg.left());
		}
	}

	public CameraBox getCamera() {
		return root;
	}
	
	public void alertResolution() {
		Rectangle2D primaryScreenBounds = Screen.getPrimary().getBounds();
		if (primaryScreenBounds.getWidth() < 1920 || primaryScreenBounds.getHeight() < 1080) {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Attention");
			alert.setHeaderText(null);
			alert.setContentText("Il semble que vous avez une résolution non-full HD.\n"
					+ "Bon déjà ne pas avoir la HD en 2016 c'est chaud quand même.\n"
					+ "En plus de ça ce programme a été fait pour cette résolution (2016 je rappelle). Alors ça risque de pas super bien marcher.\n\n"
					+ "Après moi j'dis ça...");

			alert.showAndWait();
		}
	}

}
