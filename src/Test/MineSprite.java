/*
 * 
 * 
 * 
 */
package Test;

import Elements.animate.SpeakAnimable;
import com.brashmonkey.spriter.Player;

/**
 * MineSprite.java
 * 
 */
public class MineSprite extends SpeakAnimable {
	
	private static final String SCML_PATH = "assets/test/test.scml";
	private static final double WIDTH = 120;
	private static final double HEIGHT = 180;
	private static final float X = 60;
	private static final float Y = 120;

	public MineSprite() {
		super(SCML_PATH, X, Y, WIDTH, HEIGHT);
	}

	@Override
	protected void updateBones(Player player) {
//		player.setBone("a1", player.getBone("a1").angle + 30);
	}

}
