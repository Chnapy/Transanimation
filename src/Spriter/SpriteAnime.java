/*
 * 
 * 
 * 
 */
package Spriter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Logger;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;

import com.brashmonkey.spriter.Data;
import com.brashmonkey.spriter.Drawer;
import com.brashmonkey.spriter.FileReference;
import com.brashmonkey.spriter.Loader;
import com.brashmonkey.spriter.Player;
import com.brashmonkey.spriter.SCMLReader;
import com.brashmonkey.spriter.Timeline.Key.Object;
import java.util.logging.Level;
import javafx.scene.effect.Effect;

public class SpriteAnime {

	// spriter objects
	private final Canvas gameCanvas;
	public Player player;
	private ImageDrawer drawer;
	private Loader<Image> loader;
	private final Effect effect;

	public SpriteAnime(String SCMLpath, Canvas gameCanvas, float x, float y, Effect effect) {
		this.gameCanvas = gameCanvas;
		this.effect = effect;
		int index = SCMLpath.lastIndexOf("/") + 1;
		String fileName = SCMLpath.substring(index);
		SCMLpath = SCMLpath.substring(0, index);

		initialize(SCMLpath, fileName);

		player.setPosition(x, y);
	}

	public void update() {
		player.update();
	}

	public void draw(boolean debug) {
		drawer.draw(player);
		if (debug) {
			drawer.drawBones(player);
			drawer.drawBoxes(player);
		}
	}

	private void initialize(String folderPath, String fileName) {
		String xmlSCML = null;
		try {
			xmlSCML = new String(Files.readAllBytes(Paths.get(folderPath + fileName)));
		} catch (IOException ex) {
			Logger.getLogger(SpriteAnime.class.getName()).log(Level.SEVERE, null, ex);
		}
		Data data = new SCMLReader(xmlSCML).getData();
		player = new Player(data.getEntity(0));
		this.loader = new Loader<Image>(data) {

			@Override
			protected Image loadResource(FileReference ref) {
				return new Image("file:" + super.root + "/" + data.getFile(ref).name);
			}
		};
		this.loader.load(folderPath);

		drawer = new ImageDrawer(loader, gameCanvas, effect);
	}

	private class ImageDrawer extends Drawer<Image> {

		Canvas can;
		GraphicsContext gc;

		public ImageDrawer(Loader<Image> loader, Canvas can, Effect effect) {
			super(loader);
			this.can = can;
			gc = can.getGraphicsContext2D();
			gc.setEffect(effect);
		}

		@Override
		public void setColor(float r, float g, float b, float a) {
			gc.setStroke(new Color(r, g, b, a));
		}

		@Override
		public void line(float x1, float y1, float x2, float y2) {
			gc.strokeLine((int) x1, (int) can.getHeight() - y1, (int) x2, (int) can.getHeight() - y2);
		}

		@Override
		public void rectangle(float x, float y, float width, float height) {
			gc.strokeRect((int) x, (int) can.getHeight() - y, (int) width, (int) height);
		}

		@Override
		public void circle(float x, float y, float radius) {
			gc.strokeOval((int) (x - radius), (int) can.getHeight() - (y - radius), (int) radius, (int) radius);
		}

		@Override
		public void draw(Object object) {
			Image sprite = loader.get(object.ref);
			draw(sprite, object.position.x, object.position.y, object.pivot.x, object.pivot.y, object.scale.x, object.scale.y, object.angle, object.alpha);
		}

		public void draw(Image image, float x, float y, float pivot_x, float pivot_y, float scale_x, float scale_y,
				float angle, float alpha) {
			drawRotatedImage(gc, image, -angle, x, y, pivot_x, pivot_y, scale_x, scale_y);
		}

		private void drawRotatedImage(GraphicsContext gc, Image image, double angle, double x, double y, double pivot_x, double pivot_y, double scale_x, double scale_y) {
			gc.save(); // saves the current state on stack, including the current transform

			double newPivotX = (image.getWidth() * pivot_x);
			double newX = x - newPivotX;

			double newPivotY = (image.getHeight() * pivot_y);
			double newY = (gc.getCanvas().getHeight() - y) - (image.getHeight() - newPivotY);

			rotate(gc, angle, x, (gc.getCanvas().getHeight() - y));

			gc.drawImage(image, newX, newY, image.getWidth() * scale_x, image.getHeight() * scale_y);
			gc.restore(); // back to original state (before rotation)
		}

		private void rotate(GraphicsContext gc, double angle, double px, double py) {
			Rotate r = new Rotate(angle, px, py);
			gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
		}
	}
}
