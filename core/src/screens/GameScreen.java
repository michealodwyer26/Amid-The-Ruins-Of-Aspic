package screens;

import amid.the.ruins.of.aspic.Platformer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameScreen implements Screen {
	
	private Platformer game;
	private TiledMap map;
	private OrthographicCamera gamecam;
	private OrthogonalTiledMapRenderer mapRenderer;
	private Viewport gamePort;
	
	public GameScreen(Platformer game) {		
		this.game = game;
		
		gamecam = new OrthographicCamera();
		gamePort = new FitViewport(Platformer.V_WIDTH / Platformer.PPM, Platformer.V_HEIGHT / Platformer.PPM, gamecam);
		
		map = new TmxMapLoader().load("data/ruinsOfAspic.tmx");
		mapRenderer = new OrthogonalTiledMapRenderer(map, 1 / Platformer.PPM);
		
		gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2f + (384 / Platformer.PPM), 0); // Adds an amount in m to set the camera at the right y value
		
	}

	@Override
	public void show() {
		
	}
	
	private void handleInput() {
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
			gamecam.translate(-0.02f, 0);
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
			gamecam.translate(0.02f, 0);
	}

	@Override
	public void render(float delta) {
		gamecam.update();

		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		handleInput();
		
		mapRenderer.setView(gamecam);
		mapRenderer.render();
		
	}

	@Override
	public void resize(int width, int height) {
		gamePort.update(width, height);
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void dispose() {
		
	}
}
