package screens;

import amid.the.ruins.of.aspic.Platformer;
import sprites.Gary;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
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
	
	private Gary gary;
	
	public GameScreen(Platformer game) {		
		this.game = game;
		
		gamecam = new OrthographicCamera();
		gamePort = new FitViewport(Platformer.V_WIDTH / Platformer.PPM, Platformer.V_HEIGHT / Platformer.PPM, gamecam);
		
		map = new TmxMapLoader().load("data/ruinsOfAspic.tmx");
		mapRenderer = new OrthogonalTiledMapRenderer(map, 1 / Platformer.PPM);
		
		gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2f + (384 / Platformer.PPM), 0); // Adds an amount in m to set the camera at the right y value
		
		world = new World(new Vector2(0, -10), true);
		b2dr = new Box2DDebugRenderer();
		b2dr.SHAPE_STATIC.set(1, 0, 0, 1);
		
		BodyDef bdef = new BodyDef();
		PolygonShape shape = new PolygonShape();
		FixtureDef fdef = new FixtureDef();
		Body body;
		
		// Get the ground Map Objects
		
		for(MapObject object : map.getLayers().get("Ground").getObjects().getByType(RectangleMapObject.class)) {
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			
			bdef.type = BodyDef.BodyType.StaticBody;
			bdef.position.set((rect.getX() + rect.getWidth()/2) / Platformer.PPM, (rect.getY() + rect.getHeight()/2) / Platformer.PPM);
			
			body = world.createBody(bdef);
			
			shape.setAsBox(rect.getWidth() / 2 / Platformer.PPM, rect.getHeight() / 2 / Platformer.PPM);
			fdef.shape = shape; 
			
			body.createFixture(fdef);
		}
		
		gary = new Gary(world);
	}

	@Override
	public void show() {
		
	}
	
	private void handleInput() {
		if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
			gary.b2body.applyLinearImpulse(new Vector2(0, 4f), gary.b2body.getWorldCenter(), true);
		
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && gary.b2body.getLinearVelocity().x <= 2)
			gary.b2body.applyLinearImpulse(new Vector2(0.1f, 0), gary.b2body.getWorldCenter(), true);
		
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && gary.b2body.getLinearVelocity().x >= -2)
			gary.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), gary.b2body.getWorldCenter(), true);
	}

	@Override
	public void render(float delta) {
		gamecam.update();

		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		handleInput();
		
		world.step(1 / 60f, 6, 2);
		
		gamecam.position.x = gary.b2body.getPosition().x;
		
		mapRenderer.setView(gamecam);
		mapRenderer.render();
		
		b2dr.render(world, gamecam.combined);
		
		
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
