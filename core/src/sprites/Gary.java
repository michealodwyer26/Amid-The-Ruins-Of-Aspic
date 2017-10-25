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
	public enum State { JUMPING, STANDING, RUNNING, FALLING, STOPPING, STANDING_WHIPPING, LANDING };
		
	public State currentState;
	public State previousState;
	public World world;
	public Body b2body;
	
	public boolean isStandingWhipping = false;
	public boolean isLanding = false;
	public int jumpsLeft = 2;
	
	private TextureRegion standFrame;
	private static Texture spriteSheet = new Texture(Gdx.files.internal("data/betterGaryAnimationFrames.png"));
	
	public final float MAX_SPEED = 1.5f;
	
	private final float FRICTION = 0.1f;
	private final int RADIUS = 18;
	
	private final int STARTING_TILE_X = 8;
	private final int STARTING_TILE_Y = 30;
	
	public final Vector2 jumpImpulse = new Vector2(0, 4f);
	public final Vector2 weakerJumpImpulse = new Vector2(0, 2f);
	public final Vector2 runRightImpulse = new Vector2(0.1f, 0);
	public final Vector2 runLeftImpulse = new Vector2(-0.1f, 0);
	
	private Animation<TextureRegion> jumpAnimation;
	private Animation<TextureRegion> runAnimation;
	private Animation<TextureRegion> fallAnimation;
	private Animation<TextureRegion> stopAnimation;
	private Animation<TextureRegion> standingWhipAnimation;
	private Animation<TextureRegion> idleAnimation;
	private Animation<TextureRegion> landAnimation;

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
		
		// Idle Animation
		for(int i = 13; i <= 67; i += 18) {
			
			if(i == 13) {
				// Adds the first frame fifteen times
				for(int noOfFrames = 0; noOfFrames <= 15; noOfFrames++)
					frames.add(new TextureRegion(getTexture(), i, 4, 16, 35));
			}
			else
				frames.add(new TextureRegion(getTexture(), i, 4, 16, 35));
		}
		
		idleAnimation = new Animation<TextureRegion>(.1f, frames);
		frames.clear();
		
		// Run animation
		for(int i = 13; i <= 36; i += 26)
			frames.add(new TextureRegion(getTexture(), i, 86, 21, 33));
		
		frames.add(new TextureRegion(getTexture(), 59, 86, 21, 33));
		
		for(int i = 83; i <= 110; i += 27)
			frames.add(new TextureRegion(getTexture(), i, 87, 23, 32));
		
		for(int i = 136; i <= 159; i += 23) 
			frames.add(new TextureRegion(getTexture(), i, 86, 21, 33));
		
		frames.add(new TextureRegion(getTexture(), 182, 86, 22, 33));
		frames.add(new TextureRegion(getTexture(), 206, 87, 22, 32));
		frames.add(new TextureRegion(getTexture(), 230, 87, 21, 32));
		
		runAnimation = new Animation<TextureRegion>(.1f, frames);
		frames.clear();
		
		// Jump animation
		for(int i = 13; i <= 38; i += 25)
			frames.add(new TextureRegion(getTexture(), i, 43, 24, 35));

		frames.add(new TextureRegion(getTexture(), 63, 46, 23, 32));
		
		for(int noOfFrames = 0; noOfFrames <= 20; noOfFrames++)
			// Adds this frame 20 times
			frames.add(new TextureRegion(getTexture(), 88, 41, 24, 37));
		
		jumpAnimation = new Animation<TextureRegion>(.05f, frames);
		frames.clear();
		
		// Fall animation			
		frames.add(new TextureRegion(getTexture(), 114, 41, 24, 37));
		
		fallAnimation = new Animation<TextureRegion>(.05f, frames);
		frames.clear();
		
		// Land animation
		frames.add(new TextureRegion(getTexture(), 140, 46, 22, 23));
		frames.add(new TextureRegion(getTexture(), 164, 46, 22, 32));
		frames.add(new TextureRegion(getTexture(), 188, 47, 21, 31));
		frames.add(new TextureRegion(getTexture(), 211, 46, 18, 32));
		
		landAnimation = new Animation<TextureRegion>(0.05f, frames);
		frames.clear();

		// Stop animation
		frames.add(new TextureRegion(getTexture(), 13, 123, 20, 32));
		frames.add(new TextureRegion(getTexture(), 35, 123, 23, 32));
		frames.add(new TextureRegion(getTexture(), 60, 123, 21, 32));
		frames.add(new TextureRegion(getTexture(), 83, 121, 19, 34));
		
		stopAnimation = new Animation<TextureRegion>(.05f, frames);
		frames.clear();
		
		// Standing whip animation
		frames.add(new TextureRegion(getTexture(), 13, 190, 19, 34));
		frames.add(new TextureRegion(getTexture(), 34, 192, 21, 32));
		frames.add(new TextureRegion(getTexture(), 57, 192, 23, 32));
		frames.add(new TextureRegion(getTexture(), 82, 192, 28, 32));
		frames.add(new TextureRegion(getTexture(), 112, 192, 29, 32));
		frames.add(new TextureRegion(getTexture(), 143, 190, 29, 34)); 
		frames.add(new TextureRegion(getTexture(), 174, 189, 34, 35)); 
		frames.add(new TextureRegion(getTexture(), 210, 190, 39, 34)); 
		frames.add(new TextureRegion(getTexture(), 251, 195, 67, 29)); 
		frames.add(new TextureRegion(getTexture(), 320, 195, 68, 29)); 
		frames.add(new TextureRegion(getTexture(), 390, 195, 67, 29)); 
		frames.add(new TextureRegion(getTexture(), 459, 193, 54, 31)); 
		frames.add(new TextureRegion(getTexture(), 515, 193, 45, 31)); 
		frames.add(new TextureRegion(getTexture(), 562, 192, 23, 32)); 
		frames.add(new TextureRegion(getTexture(), 587, 190, 19, 34)); 

		standingWhipAnimation = new Animation<TextureRegion>(.05f, frames);
		frames.clear();
		
		defineGary();
		standFrame = new TextureRegion(getTexture(), 13, 4, 16, 35);
		setBounds(13, 4, 16 / Platformer.PPM, 35 / Platformer.PPM);
		setRegion(standFrame);
	}
	
	public void update(float dt) {
		TextureRegion currentFrame = getFrame(dt);
		
		if(currentFrame.isFlipX()) {
			setPosition(b2body.getPosition().x - (currentFrame.getRegionWidth() / Platformer.PPM) + b2body.getFixtureList().get(0).getShape().getRadius(), b2body.getPosition().y - getHeight() / 2);
		} else {
			setPosition(b2body.getPosition().x, b2body.getPosition().y - b2body.getFixtureList().get(0).getShape().getRadius());
		}
		
		
		setRegion(currentFrame);
		setSize(currentFrame.getRegionWidth() / Platformer.PPM, currentFrame.getRegionHeight() / Platformer.PPM);
		
		
		if(standingWhipAnimation.isAnimationFinished(stateTimer) && isStandingWhipping) {
			isStandingWhipping = false;
		}
		
		if(landAnimation.isAnimationFinished(stateTimer) && isLanding) {
			isLanding = false;
		}
	}
	
	private TextureRegion getFrame(float dt) {
		currentState = getState();
		
		
		TextureRegion region;
		switch(currentState) {
			case RUNNING:
				region = (TextureRegion) runAnimation.getKeyFrame(stateTimer, true);
				break;
				
			case FALLING:
				region = (TextureRegion) fallAnimation.getKeyFrame(stateTimer, true);
				break;
				
			case JUMPING:
				region = (TextureRegion) jumpAnimation.getKeyFrame(stateTimer, true);
				break;
				
			case STOPPING:
				region = (TextureRegion) stopAnimation.getKeyFrame(stateTimer);
				break;
				
			case STANDING_WHIPPING:
				region = (TextureRegion) standingWhipAnimation.getKeyFrame(stateTimer);
				break;
				
			case LANDING:
				region = (TextureRegion) landAnimation.getKeyFrame(stateTimer);
				break;
				
			case STANDING:
			default:
				region = (TextureRegion) idleAnimation.getKeyFrame(stateTimer, true);
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
		
		if(isStandingWhipping)
			return State.STANDING_WHIPPING;
		
		if(isLanding)
			return State.LANDING;
		
		else if(b2body.getLinearVelocity().y > 0)
			return State.JUMPING;
		
		else if(b2body.getLinearVelocity().y < 0)
			return State.FALLING;

		else if(b2body.getLinearVelocity().x != 0 && b2body.getLinearVelocity().y == 0)
			return State.RUNNING;
		
		else if(b2body.getLinearVelocity().x == 0 && b2body.getLinearVelocity().y == 0)
			return State.STANDING;
		
		else if(b2body.getLinearVelocity().x <= .5 || b2body.getLinearVelocity().x >= -.5)
			return State.STOPPING;
		
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
		
		b2body.createFixture(fdef).setUserData(this);
	}
}
