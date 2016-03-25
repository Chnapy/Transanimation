/*
 * 
 * 
 * 
 */
package Elements;

import Elements.actions.Action;
import Elements.actions.CustomAction;
import Elements.actions.CustomAction.CustomTransition;
import Elements.actions.FlipAction;
import Elements.actions.MoveAction;
import Elements.actions.PauseAction;
import Elements.actions.SpeakAction;
import Elements.actions.SpeakPauseAction;
import javafx.animation.Interpolator;
import javafx.animation.SequentialTransition;
import javafx.util.Duration;

/**
 * Element.java
 *
 */
public interface Element {

	public default void addAction(Action action) {
		if (action instanceof SpeakAction || action instanceof SpeakPauseAction) {
			return;
		}
		getSTransition().getChildren().add(action.transition);
	}

	public default void addAction(Action action, int index) {
		if (action instanceof SpeakAction || action instanceof SpeakPauseAction) {
			return;
		}
		getSTransition().getChildren().add(index, action.transition);
	}

	public default void start() {
		getSTransition().play();
	}

	public default void end() {
		getSTransition().stop();
	}

	public default Element move(double x, double y, Interpolator interpolator, long duration) {
		addAction(new MoveAction(x, y, interpolator, duration));
		return this;
	}

	public default Element pause(long duration) {
		addAction(new PauseAction(duration));
		return this;
	}

	public default Element flip() {
		addAction(new FlipAction());
		return this;
	}
	
	public default Element custom(CustomTransition ct, Interpolator it) {
		addAction(new CustomAction(ct, it));
		return this;
	}

	public default Element speak(String text, Interpolator interpolator, long duration) {
		addAction(new SpeakAction(text, interpolator, duration));
		return this;
	}

	public default Element speakPause(long duration) {
		addAction(new SpeakPauseAction(duration));
		return this;
	}
	
	public default void goToDuration(long duration) {
		getSTransition().jumpTo(Duration.millis(duration));
	}

	public void flipAction();

	public SequentialTransition getSTransition();

}
