package com.ladinc.puckoff.core.objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class Puck 
{
	
	public Body body;
	
	float puckSize = 1f;
	float slowDownMultiplier = 0.2f;
	
	public Sprite sprite;
	
	public Puck(World world, Vector2 startPos)
	{
		
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
	    fixtureDef.restitution = 1f;
	    
	    this.body.createFixture(fixtureDef);
	    
	}
	
	public void update()
	{
		Vector2 currentVelocity = this.getLocalVelocity();
		Vector2 position= this.getLocation();
		
		//System.out.println("Ball Position - " + position + "Ball Velocity - " + currentVelocity);
		
		this.body.applyForce(this.body.getWorldVector(new Vector2(-(currentVelocity.x*(slowDownMultiplier)), -(currentVelocity.y*(slowDownMultiplier)))), position, true );
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

}
