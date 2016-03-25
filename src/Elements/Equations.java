/*
 * 
 * 
 * 
 */
package Elements;

import static java.lang.Math.exp;
import javafx.animation.Interpolator;

/**
 * Equations.java
 *
 */
public class Equations {

	public static final Interpolator EXP = new Interpolator() {
		@Override
		protected double curve(double d) {
			if (d == 0 || d == 1) {
				return d;
			}
			return (exp(d) - exp(0)) / (exp(1) - exp(0));
		}
	};

}
