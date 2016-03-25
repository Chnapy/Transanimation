/*
 * 
 * 
 * 
 */
package Elements.speakable;

import Elements.GraphicElement;
import Elements.actions.Action;
import Elements.actions.SpeakAction;
import Elements.actions.SpeakPauseAction;

/**
 * Speakable.java
 *
 */
public class Speakable extends GraphicElement {

	public final Speaker speaker;

	public Speakable(String srcImg) {
		super(srcImg);
		speaker = new Speaker();
		addChilds(speaker);
	}

	@Override
	public void addAction(Action action) {
		if (action instanceof SpeakAction) {
			((SpeakAction) action).setSpeaker(speaker);
			speaker.addAction(action);
		} else if (action instanceof SpeakPauseAction) {
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
