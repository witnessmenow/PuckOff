package com.ladinc.puckoff.core.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.ladinc.puckoff.core.controls.IControls;

public class HockeyPlayer {
	
	float playerSize = 4f;
	
	private static int PIXELS_PER_METER = 10;

	public Sprite sprite;
	public Body body;
	private World world;
	
	public int playerNumber;
	
	public IControls controller;
	Vector2 startPos;
	
	private float power;
	
	public HockeyPlayer(World world, int number, IControls controller, Vector2 startPos)
	{
		playerNumber = number;
		
		this.world = world;
		this.controller = controller;
		this.startPos = startPos;
		this.power = 1500f;
		
		createBody();
	}
	
	private void createBody()
	{
		//Dynamic Body  
	    BodyDef bodyDef = new BodyDef();  
	    bodyDef.type = BodyType.DynamicBody;  
	    bodyDef.position.set(this.startPos.x, this.startPos.y);
	    //bodyDef.position.set(0, 0); 
	    bodyDef.fixedRotation = true;
	    this.body = world.createBody(bodyDef); 

	    CircleShape dynamicCircle = new CircleShape();  
	    dynamicCircle.setRadius(playerSize);  
	    
	    FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.density = 1.0f;
	    fixtureDef.friction = 0.3f;  
	    fixtureDef.restitution = 0.5f;
	    fixtureDef.shape = dynamicCircle;
	     
	    this.body.createFixture(fixtureDef);
		
	}
	
	public void updateMovement(float delta)
	{
		Vector2 movement = this.controller.getMovementInput();
		Vector2 rotation = this.controller.getRotationInput();
		
		Gdx.app.debug("HockeyPlayer - updateMovement", "Movement: x=" + String.valueOf(movement.x) + " y=" + String.valueOf(movement.y));
		
		Vector2 forceVector= new Vector2(this.power*movement.x, this.power*movement.y);
		Vector2 position= this.body.getWorldCenter();
		this.body.applyForce(this.body.getWorldVector(new Vector2(forceVector.x, forceVector.y)), position, true );
	}
}
