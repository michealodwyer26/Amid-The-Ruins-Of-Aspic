package amid.the.ruins.of.aspic;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import screens.MainMenuScreen;

public class Platformer extends Game {
	public SpriteBatch batch;
	
	public static final int V_WIDTH = 800;
	public static final int V_HEIGHT = 416;
	public static final float PPM = 100;
	
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

	}
}
