package screens;

import amid.the.ruins.of.aspic.Platformer;
import sprites.Gary;
import tools.B2WorldCreator;
import tools.WorldContactListener;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameScreen implements Screen {
	
	private Platformer game;
	private TiledMap map;
	private OrthographicCamera gamecam;
	private OrthogonalTiledMapRenderer mapRenderer;
	private Viewport gamePort;
	
	private World world;
	private Box2DDebugRenderer b2dr;
	
	private final float timeStep = 1 / 60f;
	private final int velocityIterations = 6;
	private final int positionsInterations = 2;
	
	private Gary gary;
	
	public GameScreen(Platformer game) {		
		this.game = game;
		
		gamecam = new OrthographicCamera();
		gamePort = new FitViewport(Platformer.V_WIDTH / Platformer.PPM, Platformer.V_HEIGHT / Platformer.PPM, gamecam);
		
		map = new TmxMapLoader().load("data/ruinsOfAspic.tmx");
		mapRenderer = new OrthogonalTiledMapRenderer(map, 1 / Platformer.PPM);

		gamecam.position.set(Platformer.startCamPos);

		world = new World(Platformer.GRAVITY, true);
		
		b2dr = new Box2DDebugRenderer();
		b2dr.SHAPE_STATIC.set(1, 0, 0, 1);
		
		new B2WorldCreator(world, map);
		
		world.setContactListener(new WorldContactListener());
		
		gary = new Gary(world);
	}

	@Override
	public void show() {
		
	}
	
	private void handleInput() {
		if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && gary.jumpsLeft > 0) {
			gary.jumpsLeft--;
			if(gary.jumpsLeft == 1)
				gary.b2body.applyLinearImpulse(gary.jumpImpulse, gary.b2body.getWorldCenter(), true);
			else
				gary.b2body.applyLinearImpulse(gary.weakerJumpImpulse, gary.b2body.getWorldCenter(), true);

		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && gary.b2body.getLinearVelocity().x <= gary.MAX_SPEED) {
			gary.b2body.applyLinearImpulse(gary.runRightImpulse, gary.b2body.getWorldCenter(), true);
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && gary.b2body.getLinearVelocity().x >= -gary.MAX_SPEED) {
			gary.b2body.applyLinearImpulse(gary.runLeftImpulse, gary.b2body.getWorldCenter(), true);

		}
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.W) && !gary.isStandingWhipping) {
			gary.isStandingWhipping = true;
		}
	}
	

	@Override
	public void render(float delta) {
		gamecam.update();

		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		handleInput();
		
		world.step(timeStep, velocityIterations, positionsInterations);
		
		gary.update(delta);
				
		if(gary.b2body.getPosition().y < (-100 / Platformer.PPM)) {
			game.livesRemaining--;
			
			if(game.livesRemaining >= 0)
				game.setScreen(new LivesRemainingScreen(game));
			else 
				game.setScreen(new GameOverScreen(game));
		}
		
		gamecam.position.x = gary.b2body.getPosition().x;
		
		mapRenderer.setView(gamecam);
		mapRenderer.render();
		
		b2dr.render(world, gamecam.combined);
		
		game.batch.setProjectionMatrix(gamecam.combined);
		game.batch.begin();
		gary.draw(game.batch);
		game.batch.end();
		
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
		map.dispose();
		mapRenderer.dispose();
		world.dispose();
		b2dr.dispose();
	}
}
