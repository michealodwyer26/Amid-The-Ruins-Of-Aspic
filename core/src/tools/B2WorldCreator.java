package tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import amid.the.ruins.of.aspic.Platformer;

public class B2WorldCreator {

	public B2WorldCreator(World world, TiledMap tiledMap) {
		BodyDef bdef = new BodyDef();
		PolygonShape shape = new PolygonShape();
		FixtureDef fdef = new FixtureDef();
		Body body;
		
		// Get the ground Map Objects
		
		for(MapObject object : tiledMap.getLayers().get("Ground").getObjects().getByType(RectangleMapObject.class)) {
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			
			bdef.type = BodyDef.BodyType.StaticBody;
			bdef.position.set((rect.getX() + rect.getWidth()/2) / Platformer.PPM, (rect.getY() + rect.getHeight()/2) / Platformer.PPM);
			
			body = world.createBody(bdef);
			
			shape.setAsBox(rect.getWidth() / 2 / Platformer.PPM, rect.getHeight() / 2 / Platformer.PPM);
			fdef.shape = shape; 
			
			body.createFixture(fdef).setUserData("ground");
		}
		
		// Get the enemy Map Objects
		
		for(MapObject object : tiledMap.getLayers().get("Enemies").getObjects().getByType(RectangleMapObject.class)) {
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			
			bdef.type = BodyDef.BodyType.DynamicBody;
			bdef.position.set((rect.getX() + rect.getWidth()/2) / Platformer.PPM, (rect.getY() + rect.getHeight()/2) / Platformer.PPM);
			
			body = world.createBody(bdef);
			body.setUserData("enemyBody");
						
			shape.setAsBox(rect.getWidth() / 2 / Platformer.PPM, rect.getHeight() / 2 / Platformer.PPM);
			fdef.shape = shape; 
			
			body.createFixture(fdef).setUserData("enemyFixture");
		}
	}
}
