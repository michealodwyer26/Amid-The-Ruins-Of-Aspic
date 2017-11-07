package tools;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import sprites.Gary;
import sprites.ZombieSoldier;

public class WorldContactListener implements ContactListener {

	@Override
	public void beginContact(Contact contact) {
		Fixture fixA = contact.getFixtureA();
		Fixture fixB = contact.getFixtureB();
		
		// Handling Gary's ground collisions
		if(fixA.getUserData() instanceof Gary || fixB.getUserData() instanceof Gary) {
			if(fixA.getUserData().equals("ground") || fixB.getUserData().equals("ground")){
				Fixture garyFixture = fixA.getUserData() instanceof Gary ? fixA : fixB;
				
				Gary gary = (Gary) garyFixture.getUserData();
				gary.isLanding = true;
				gary.jumpsLeft = 2;
			}
		}
		
		// Handling Gary's attacks on Zombie Soldiers with his whip
		if(fixA.getUserData() instanceof ZombieSoldier || fixB.getUserData() instanceof ZombieSoldier) {
			if(fixA.getUserData().equals("whipLineSegment") || fixB.getUserData().equals("whipLineSegment")) {
				Fixture zombieFix = fixA.getUserData() instanceof ZombieSoldier ? fixA : fixB;
				
				ZombieSoldier zomSold = (ZombieSoldier) zombieFix.getUserData();
				zomSold.isDying = true;
			}
		}
		
		// Handle the Zombie Soldier's attacks on Gary
		if(fixA.getUserData() instanceof ZombieSoldier || fixB.getUserData() instanceof ZombieSoldier) {
			if(fixA.getUserData() instanceof Gary || fixB.getUserData() instanceof Gary) {
				Fixture zombieSoldFix = fixA.getUserData() instanceof ZombieSoldier ? fixA : fixB;

				ZombieSoldier zomSold = (ZombieSoldier) zombieSoldFix.getUserData();
				
				if(zomSold.currentState == ZombieSoldier.State.STABBING) {
					Fixture garyFix = fixA.getUserData() instanceof Gary ? fixA : fixB;
					
					Gary gary = (Gary) garyFix.getUserData();
					gary.health--;
					
					gary.b2body.applyLinearImpulse(new Vector2(-1.3f, 1f), gary.b2body.getWorldCenter(), true);
				}
			}
		}
		
		// Handling when Gary collides with a Zombie Soldier with no one attacking
		if(fixA.getUserData() instanceof ZombieSoldier || fixB.getUserData() instanceof ZombieSoldier) {
			if(fixA.getUserData() instanceof Gary || fixB.getUserData() instanceof Gary) {
				Fixture zombieSoldFix = fixA.getUserData() instanceof ZombieSoldier ? fixA : fixB;
				ZombieSoldier zomSold = (ZombieSoldier) zombieSoldFix.getUserData();
				
				if(!(zomSold.currentState == ZombieSoldier.State.STABBING)) {
					zomSold.isStabbing = true;
				}
			}
		}
	}

	@Override
	public void endContact(Contact contact) {
		
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		
	}

}
