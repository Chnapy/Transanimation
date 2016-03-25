/*
 * 
 * 
 * 
 */
package Elements.animate;

import Elements.Element;
import Spriter.SpriteAnime;
import javafx.animation.SequentialTransition;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.effect.Effect;

/**
 * Animable.java
 *
 */
public class Animable extends Group implements Element {

	public static boolean DEBUG = false;

	public final Canvas canvas;
	public final SpriteAnime sprite;
	private final SequentialTransition sTransition;
	
	public Animable(String SCMLpath, float x, float y, double width, double height) {
		this(SCMLpath, x, y, width, height, null);
	}

	public Animable(String SCMLpath, float x, float y, double width, double height, Effect effect) {
		canvas = new Canvas(width, height);
		getChildren().add(canvas);
		getStyleClass().add(this.getClass().getSimpleName().toLowerCase());
		this.sprite = new SpriteAnime(SCMLpath, canvas, x, y, effect);

		sTransition = new SequentialTransition(this);
	}

	public void setPosition(double x, double y, boolean fromBottom) {
		setLayoutX(x);
		if (fromBottom) {
			setLayoutY(y - canvas.getHeight());
		} else {
			setLayoutY(y);
		}
	}

	public void update() {
		sprite.update();

		canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

		sprite.draw(DEBUG);
		
		if(DEBUG) {
			canvas.getGraphicsContext2D().strokeRect(0, 0, canvas.getWidth(), canvas.getHeight());
		}
	}

	@Override
	public void flipAction() {
		canvas.setScaleX(-canvas.getScaleX());
	}

	@Override
	public SequentialTransition getSTransition() {
		return sTransition;
	}

}
