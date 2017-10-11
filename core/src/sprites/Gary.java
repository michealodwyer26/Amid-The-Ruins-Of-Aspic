package sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import amid.the.ruins.of.aspic.Platformer;

public class Gary extends Sprite {
	public World world;
	public Body b2body;
	
	public final int MAX_SPEED = 2;
	
	private final float FRICTION = 0.65f;
	private final int RADIUS = 12;
	
	private final int STARTING_TILE_X = 8;
	private final int STARTING_TILE_Y = 30;
	
	public final Vector2 jumpImpulse = new Vector2(0, 4f);
	public final Vector2 runRightImpulse = new Vector2(0.1f, 0);
	public final Vector2 runLeftImpulse = new Vector2(-0.1f, 0);

	
	public Gary(World world) {
		this.world = world;
		defineGary();
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
