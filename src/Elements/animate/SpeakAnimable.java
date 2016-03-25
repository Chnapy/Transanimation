/*
 * 
 * 
 * 
 */
package Elements.animate;

import Elements.actions.Action;
import Elements.actions.SpeakAction;
import Elements.actions.SpeakPauseAction;
import Elements.speakable.Speaker;
import javafx.scene.effect.Effect;

/**
 * SpeakAnimable.java
 * 
 */
public class SpeakAnimable extends Animable {

	public final Speaker speaker;

	public SpeakAnimable(String SCMLpath, float x, float y, double width, double height) {
		this(SCMLpath, x, y, width, height, null);
	}

	public SpeakAnimable(String SCMLpath, float x, float y, double width, double height, Effect effect) {
		super(SCMLpath, x, y, width, height, effect);
		speaker = new Speaker();
		getChildren().add(speaker);
	}

	@Override
	public void start() {
		super.start();
		speaker.start();
	}

	@Override
	public void end() {
		speaker.end();
		super.end();
	}

	@Override
	public void addAction(Action action) {
		if (action instanceof SpeakAction) {
			((SpeakAction) action).setSpeaker(speaker);
			speaker.addAction(action);
		} else if(action instanceof SpeakPauseAction) {
			speaker.addAction(action);
		} else {
			super.addAction(action);
		}
	}

	@Override
	public void goToDuration(long duration) {
		super.goToDuration(duration);
		speaker.goToDuration(duration);
	}

}
