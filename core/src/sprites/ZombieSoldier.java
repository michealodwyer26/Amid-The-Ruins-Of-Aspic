package sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import amid.the.ruins.of.aspic.Platformer;

public class ZombieSoldier extends Sprite {

	public enum State { TRUDGING, DYING };
	
	private Platformer game;
	private static Texture spriteSheet = new Texture(Gdx.files.internal("data/zombieSoldierFrames.png"));
	
	private Body b2body;
	
	private World world;
	
	public ZombieSoldier(World world, Body body) {
		super(spriteSheet);
		
		this.world = world;
		this.b2body = body;
		
		defineZombieSoldier();
	}
	
	public void update(float dt) {
		if(Gdx.input.isKeyJustPressed(Input.Keys.Z))
			b2body.applyLinearImpulse(new Vector2(0f, 2f), b2body.getWorldCenter(), true);
	}
	
	public void defineZombieSoldier() {
	}
	
	public String printB2BodyFixtures() {
		return b2body.getFixtureList().toString();
	}
	
}
