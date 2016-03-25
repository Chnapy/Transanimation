/*
 * 
 * 
 * 
 */
package Elements.actions;

import Elements.Element;
import javafx.animation.Interpolator;
import javafx.animation.Transition;

/**
 * Action.java
 *
 * @param <E>
 * @param <T>
 */
public abstract class Action<E extends Element, T extends Transition> {

	public final T transition;

	public Action(T transition, Interpolator interpolator) {
		this.transition = transition;
		this.transition.setInterpolator(interpolator);
	}

}
