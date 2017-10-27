package amid.the.ruins.of.aspic;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import screens.MainMenuScreen;
import sprites.Gary;

public class Platformer extends Game {
	public SpriteBatch batch;
	public int livesRemaining;

	
	public static final int V_WIDTH = 400;
	public static final int V_HEIGHT = 208;
	public static final float PPM = 100;
	public static final int TILE_SIZE = 16;
	
	public static final Vector2 GRAVITY = new Vector2(0, -10);
	
	private static final float startCamX = (25 * Platformer.TILE_SIZE) / Platformer.PPM;
	private static final float startCamY = (34 * Platformer.TILE_SIZE) / Platformer.PPM;
	private static final float startCamZ = 0f;
	
	public static final Vector3 startCamPos = new Vector3(startCamX, startCamY, startCamZ);
	
	@Override
	public void create () {
		this.batch = new SpriteBatch();		
		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
