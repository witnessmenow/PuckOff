package com.ladinc.puckoff.core.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.ladinc.puckoff.core.collision.CollisionInfo;
import com.ladinc.puckoff.core.collision.CollisionInfo.CollisionObjectType;

public class Puck 
{
	private static int PIXELS_PER_METER = 10;
	
	public Body body;
	
	public HockeyPlayer lastPlayerToTouch;
	
	public float puckSize = 1f;
	float slowDownMultiplier = 0.2f;
	
	public Sprite sprite;
	
	Vector2 startPos; 
	
	public Puck(World world, Vector2 startPos)
	{
		
		lastPlayerToTouch = null;
		this.startPos = startPos;
		
		BodyDef bodyDef = new BodyDef();  
	    bodyDef.type = BodyType.DynamicBody;  
	    bodyDef.position.set(startPos.x, startPos.y);  
	    this.body = world.createBody(bodyDef);  
	    CircleShape dynamicCircle = new CircleShape();  
	    dynamicCircle.setRadius(puckSize);  
	    FixtureDef fixtureDef = new FixtureDef();  
	    fixtureDef.shape = dynamicCircle;  
	    fixtureDef.density = 0.25f;  
	    fixtureDef.friction = 0f;  
	    fixtureDef.restitution = 0.5f;
	    
	    this.body.createFixture(fixtureDef);
	    this.body.setUserData(new CollisionInfo("", CollisionObjectType.Puck));
	    
	    dynamicCircle.dispose();
	    
	    this.sprite = Puck.getPuckSprite();
	    
	}
	
	public void resetPuck()
	{
		lastPlayerToTouch = null;
		this.body.setTransform(startPos, 0);
	}
	
	public void update()
	{
		Vector2 currentVelocity = this.getLocalVelocity();
		Vector2 position= this.getLocation();
		
		//System.out.println("Ball Position - " + position + "Ball Velocity - " + currentVelocity);
		
		this.body.applyForce(this.body.getWorldVector(new Vector2(-(currentVelocity.x*(slowDownMultiplier)), -(currentVelocity.y*(slowDownMultiplier)))), position, true );
	}
	
	public void updateSprite(SpriteBatch spriteBatch)
	{
		setSpritePosition(sprite, PIXELS_PER_METER, body);
		sprite.draw(spriteBatch);
	}
	
	public void setSpritePosition(Sprite spr, int PIXELS_PER_METER, Body forLocation)
	{		
		spr.setPosition(PIXELS_PER_METER * forLocation.getPosition().x - spr.getWidth()/2,
				PIXELS_PER_METER * forLocation.getPosition().y  - spr.getHeight()/2);
	}
	
	public Vector2 getLocation()
	{
		return this.body.getWorldCenter();
	}
	
	public Vector2 getLocalVelocity() {
	    /*
	    returns balls's velocity vector relative to the car
	    */
		return this.body.getLocalVector(this.body.getLinearVelocityFromLocalPoint(new Vector2(0, 0)));
	}
	
	public static Sprite getPuckSprite()
	{
    	Texture puckTexture = new Texture(Gdx.files.internal("Images/Objects/Puck.png"));
    	
    	return new Sprite(puckTexture);
	}

}
