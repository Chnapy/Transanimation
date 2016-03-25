/*
 * 
 * 
 * 
 */
package Elements.background;

import Elements.GraphicElement;
import java.util.Arrays;

/**
 * Background.java
 *
 */
public class Background extends GraphicElement {

	private final static int TOP = 0;
	private final static int RIGHT = 1;
	private final static int BOTTOM = 2;
	private final static int LEFT = 3;

	private final String url;
	private Background[] bords;

	public Background(String srcImg) {
		super(srcImg);
		url = srcImg;
		bords = new Background[4];
		Arrays.fill(bords, null);
//		setCache(true);
	}

	@Override
	public void setPosition(double x, double y, boolean fromBottom) {
		super.setPosition(x, y, fromBottom);
		positionBord(TOP, bords[TOP]);
		positionBord(RIGHT, bords[RIGHT]);
		positionBord(BOTTOM, bords[BOTTOM]);
		positionBord(LEFT, bords[LEFT]);
	}

	public Background top(Background top) {
		bords[TOP] = top;
		return positionBord(TOP, top);
	}

	public Background right(Background right) {
		bords[RIGHT] = right;
		return positionBord(RIGHT, right);
	}

	public Background bottom(Background bottom) {
		bords[BOTTOM] = bottom;
		return positionBord(BOTTOM, bottom);
	}

	public Background left(Background left) {
		bords[LEFT] = left;
		return positionBord(LEFT, left);
	}

	private Background positionBord(int i, Background bord) {
		if (bord == null) {
			return null;
		}
		double x = 0, y = 0;
		switch (i) {
			case TOP:
				y = -imView.getImage().getHeight();
				break;
			case RIGHT:
				x = imView.getImage().getWidth();
				break;
			case BOTTOM:
				y = imView.getImage().getHeight();
				break;
			case LEFT:
				x = -imView.getImage().getWidth();
				break;
		}
		bord.setLayoutX(getLayoutX() + x);
		bord.setLayoutY(getLayoutY() + y);
		return bord;
	}

	public Background top() {
		return bords[TOP];
	}

	public Background right() {
		return bords[RIGHT];
	}

	public Background bottom() {
		return bords[BOTTOM];
	}

	public Background left() {
		return bords[LEFT];
	}

	public Background copy() {
		return new Background(url);
	}

}
