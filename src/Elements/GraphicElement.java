/*
 * 
 * 
 * 
 */
package Elements;

import java.util.ArrayList;
import javafx.animation.SequentialTransition;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.ImageView;

/**
 * GraphicElement.java
 *
 */
public abstract class GraphicElement extends Group implements Element {

	protected final ImageView imView;
	protected final ArrayList<Element> childs;

	protected SequentialTransition sTransition;

	public GraphicElement(String srcImg) {
		getStyleClass().add(this.getClass().getSimpleName().toLowerCase());
		imView = new ImageView("file:" + srcImg);
		imView.setSmooth(true);
		getChildren().add(imView);
		childs = new ArrayList();

		sTransition = new SequentialTransition(this);
	}

	public final void addChilds(Element... elems) {
		for (Element el : elems) {
			childs.add(el);
			if (el instanceof Node) {
				getChildren().add((Node) el);
			} else {
				System.err.println(el + " non Node");
			}
		}
	}

	public void setPosition(double x, double y, boolean fromBottom) {
		setLayoutX(x);
		if (fromBottom) {
			setLayoutY(y - imView.getImage().getHeight());
		} else {
			setLayoutY(y);
		}
	}

	@Override
	public void start() {
		Element.super.start();
		childs.forEach((c) -> {
			c.start();
		});
	}

	@Override
	public void end() {
		Element.super.end();
		childs.forEach((c) -> {
			c.end();
		});
	}

	@Override
	public void flipAction() {
		imView.setScaleX(-imView.getScaleX());
	}

	@Override
	public SequentialTransition getSTransition() {
		return sTransition;
	}

}
