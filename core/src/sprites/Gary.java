package sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import amid.the.ruins.of.aspic.Platformer;

public class Gary extends Sprite {
	public enum State { JUMPING, STANDING, RUNNING, FALLING };
	public State currentState;
	public State previousState;
	public World world;
	public Body b2body;
	private TextureRegion stand;
	private static Texture spriteSheet = new Texture(Gdx.files.internal("data/betterGaryAnimationFrames.png"));
	
	public final int MAX_SPEED = 2;
	
	private final float FRICTION = 0.65f;
	private final int RADIUS = 18;
	
	private final int STARTING_TILE_X = 8;
	private final int STARTING_TILE_Y = 30;
	
	public final Vector2 jumpImpulse = new Vector2(0, 4f);
	public final Vector2 runRightImpulse = new Vector2(0.1f, 0);
	public final Vector2 runLeftImpulse = new Vector2(-0.1f, 0);
	
	private Animation idleAnimation;
	private Animation jumpAnimation;
	private Animation runAnimation;

	private float stateTimer;
	private boolean runningRight;
	
	
	public Gary(World world) {
		super(spriteSheet);
		this.world = world;
		
		currentState = State.STANDING;
		previousState = State.STANDING;
		stateTimer = 0;
		runningRight = true;
		
		Array<TextureRegion> frames = new Array<TextureRegion>();
		for(int i = 13; i <= 36; i += 26) {
			frames.add(new TextureRegion(getTexture(), i, 86, 21, 33));
		}
		frames.add(new TextureRegion(getTexture(), 59, 86, 21, 33));
		
		for(int i = 81; i <= 109; i += 28) {
			frames.add(new TextureRegion(getTexture(), i, 87, 25, 32));
		}
		for(int i = 136; i <= 159; i += 23) {
			frames.add(new TextureRegion(getTexture(), i, 86, 21, 33));
		}
		frames.add(new TextureRegion(getTexture(), 182, 86, 22, 33));
		frames.add(new TextureRegion(getTexture(), 206, 87, 22, 32));
		frames.add(new TextureRegion(getTexture(), 230, 87, 21, 32));
		
		runAnimation = new Animation(.7f, frames);
		frames.clear();
		
		defineGary();
		stand = new TextureRegion(getTexture(), 13, 4, 16, 35);
		setBounds(13, 4, 16 / Platformer.PPM, 35 / Platformer.PPM);
		setRegion(stand);
	}
	
	public void update(float dt) {
		setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
		setRegion(getFrame(dt));
	}
	
	private TextureRegion getFrame(float dt) {
		currentState = getState();
		
		TextureRegion region;
		switch(currentState) {
			case RUNNING:
				region = (TextureRegion) runAnimation.getKeyFrame(stateTimer, true);
				break;
			case FALLING:
			case STANDING:
			default:
				region = stand;
				break;
		}
		
		if((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
			region.flip(true, false);
			runningRight = false;
		}
		else if((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
			region.flip(true, false);
			runningRight = true;
		}
		
		stateTimer = currentState == previousState ? stateTimer + dt : 0;
		previousState = currentState;
		return region;
	}
	
	public State getState() {
		if(b2body.getLinearVelocity().y > 0 || (b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING))
			return State.JUMPING;
		if(b2body.getLinearVelocity().y < 0)
			return State.FALLING;
		else if(b2body.getLinearVelocity().x != 0)
			return State.RUNNING;
		else
			return State.STANDING;
	}
	public void defineGary() {
		float startX = (STARTING_TILE_X * Platformer.TILE_SIZE) / Platformer.PPM;
		float startY = (STARTING_TILE_Y * Platformer.TILE_SIZE) / Platformer.PPM;
		
		BodyDef bdef = new BodyDef();
		bdef.position.set(startX, startY);
		bdef.type = BodyDef.BodyType.DynamicBody;
		
		b2body = world.createBody(bdef);
		
		FixtureDef fdef = new FixtureDef();
		fdef.friction = FRICTION;
		
		CircleShape shape = new CircleShape();
		shape.setRadius(RADIUS / Platformer.PPM);
		
		fdef.shape = shape;
		
		b2body.createFixture(fdef);
	}
}
