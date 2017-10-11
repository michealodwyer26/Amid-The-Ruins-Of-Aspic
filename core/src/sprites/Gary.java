package sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import amid.the.ruins.of.aspic.Platformer;

public class Gary extends Sprite {
	public World world;
	public Body b2body;
	
	public Gary(World world) {
		this.world = world;
		defineGary();
	}
	
	public void defineGary() {
		BodyDef bdef = new BodyDef();
		bdef.position.set((8 * 16) / Platformer.PPM, (32 * 16) / Platformer.PPM);
		bdef.type = BodyDef.BodyType.DynamicBody;
		
		b2body = world.createBody(bdef);
		
		FixtureDef fdef = new FixtureDef();
		fdef.friction = 0.80f;
		
		CircleShape shape = new CircleShape();
		shape.setRadius(12 / Platformer.PPM);
		
		fdef.shape = shape;
		
		b2body.createFixture(fdef);
	}
}
