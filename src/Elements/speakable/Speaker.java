/*
 * 
 * 
 * 
 */
package Elements.speakable;

import Elements.Element;
import Elements.actions.Action;
import javafx.animation.SequentialTransition;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

/**
 * Speaker.java
 *
 */
public class Speaker extends Label implements Element {
	
	public static double FONT_SIZE = 10;
	public static double ESPACE = 10;

	protected SequentialTransition sTransition;

	public Speaker() {
		sTransition = new SequentialTransition(this);
		
		getStyleClass().add(this.getClass().getSimpleName().toLowerCase());
		setFont(Font.font ("Calibri", 16));
		setWrapText(true);
		setPadding(new Insets(0, 10, 0, 10));
		setVisible(false);
	}
	
	@Override
	public void addAction(Action action) {
		getSTransition().getChildren().add(action.transition);
	}

	@Override
	protected void setHeight(double d) {
		super.setHeight(d);
		reposition();
	}
	
	private void reposition() {
//		setLayoutX();
		setLayoutY(-getHeight() - ESPACE);
	}

	public void show(String text) {
		setText(text);
		setVisible(true);
	}

	public void hide() {
		setVisible(false);
		setText("");
	}

	@Override
	public void flipAction() {
		setScaleX(-getScaleX());
	}

	@Override
	public SequentialTransition getSTransition() {
		return sTransition;
	}

}
