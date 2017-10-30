package sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import amid.the.ruins.of.aspic.Platformer;

public class ZombieSoldier extends Sprite {

	public enum State { WALKING, DYING };
	
	public State currentState;
	public State previousState;
	private TextureRegion currentFrame;
	public boolean isDying;
	public boolean isDead;
	private final float MAX_SPEED = 0.3f;
	private final Vector2 walkImpulse = new Vector2(-0.1f, 0);
	private float stateTimer;
	
	private final float rectWidth;
	private final float rectHeight;
	
	private Platformer game;
	private static Texture spriteSheet = new Texture(Gdx.files.internal("data/zombieSoldierFrames.png"));
	
	private Animation<TextureRegion> walkAnimation;
	private Animation<TextureRegion> dyingAnimation;
	
	private Body b2body;
	
	private World world;
	
	
	public ZombieSoldier(World world, Body body) {
		super(spriteSheet);
		
		this.world = world;
		this.b2body = body;
		
		currentState = State.WALKING;
		isDying = false;
		isDead = false;
		stateTimer = 0f;
		
		rectWidth = 27;
		rectHeight = 33;
		
		loadAnimations();
		
		defineZombieSoldier();
	}
	
	public void update(float dt) {
		currentFrame = getFrame(dt);
		
		setRegion(currentFrame);
		setSize(currentFrame.getRegionWidth() / Platformer.PPM, currentFrame.getRegionHeight() / Platformer.PPM);
		setPosition(b2body.getPosition().x - (rectWidth / 2 / Platformer.PPM),
					b2body.getPosition().y - (rectHeight / 2 / Platformer.PPM));
		
		if(dyingAnimation.isAnimationFinished(stateTimer) && isDying) {
			isDead = true;
		}
		
		if(b2body.getLinearVelocity().x >= -MAX_SPEED) {
			b2body.applyLinearImpulse(walkImpulse, b2body.getWorldCenter(), true);
		}
	}
	
	private TextureRegion getFrame(float dt) {
		currentState = getState();
		
		TextureRegion region;
		
		switch(currentState) {
		
			default:
			case WALKING:
				region = (TextureRegion) walkAnimation.getKeyFrame(stateTimer, true);
				break;
				
			case DYING:
				region = (TextureRegion) dyingAnimation.getKeyFrame(stateTimer, false);
				break;
		}
		
		stateTimer = currentState == previousState ? stateTimer += dt : 0f;
		previousState = currentState;
		return region;
	}
	
	private State getState() {
		if(b2body.getLinearVelocity().x > 0)
			return State.WALKING;
		if(isDying)
			return State.DYING;
		
		return State.WALKING;
	}
	
	public void defineZombieSoldier() {
		
	}
	
	public void loadAnimations() {
		Array<TextureRegion> frames = new Array<TextureRegion>();
		frames.add(new TextureRegion(getTexture(), 74, 11, 27, 33));
		frames.add(new TextureRegion(getTexture(), 102, 11, 25, 33));
		frames.add(new TextureRegion(getTexture(), 127, 9, 23, 34));
		frames.add(new TextureRegion(getTexture(), 151, 10, 24, 33));
		frames.add(new TextureRegion(getTexture(), 176, 10, 27, 33));
		
		walkAnimation = new Animation<TextureRegion>(0.3f, frames);
		frames.clear();
		
		frames.add(new TextureRegion(getTexture(), 110, 199, 49, 38));
		frames.add(new TextureRegion(getTexture(), 68, 269, 32, 30));
		frames.add(new TextureRegion(getTexture(), 102, 268, 30, 31));
		frames.add(new TextureRegion(getTexture(), 134, 267, 30, 32));
		frames.add(new TextureRegion(getTexture(), 164, 267, 32, 32));
		
		dyingAnimation = new Animation<TextureRegion>(0.1f, frames);
		frames.clear();
		

	}
}
