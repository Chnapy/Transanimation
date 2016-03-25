/*
 * 
 * 
 * 
 */
package Elements.actions;

import Elements.Element;
import Elements.speakable.Speakable;
import Elements.speakable.Speaker;
import javafx.animation.Interpolator;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Transition;
import javafx.util.Duration;

/**
 * SpeakAction.java
 *
 */
public class SpeakAction extends Action<Speakable, SequentialTransition> {

	private static final int OPEN_CLOSE_DURATION = 250;

	public SpeakAction(String text, Interpolator interpolator, long duration) {
		super(new SequentialTransition(), interpolator);
		if (duration < OPEN_CLOSE_DURATION * 2) {
			throw new Error("Duration too low : " + duration + " < " + OPEN_CLOSE_DURATION * 2);
		}

		ScaleTransition sc1 = new ScaleTransition(Duration.millis(OPEN_CLOSE_DURATION));
		sc1.setFromX(0);
		sc1.setToX(1);

		PauseTransition pause = new PauseTransition(Duration.millis(duration - OPEN_CLOSE_DURATION * 2));

		ScaleTransition sc2 = new ScaleTransition(Duration.millis(OPEN_CLOSE_DURATION));
		sc2.setFromX(1);
		sc2.setToX(0);

		transition.getChildren().addAll(new TextTransition(text), sc1, pause, sc2);
	}

	public void setSpeaker(Speaker sp) {
		transition.setNode(sp);
		((TextTransition) transition.getChildren().get(0)).setSpeaker(sp);
	}

	class TextTransition extends Transition {

		private Speaker sp;
		private String text;
		private boolean used;

		public TextTransition(String text) {
			super.setCycleDuration(Duration.ONE);
			used = false;
			this.text = text;
		}

		public void setSpeaker(Speaker sp) {
			this.sp = sp;
		}

		@Override
		protected void interpolate(double d) {
			if (!used && d > 0) {
				sp.show(text);
				transition.setOnFinished((e) -> {
//					sp.hide();	//Pas nécessaire
				});
				used = true;
			}
		}

	}

}
