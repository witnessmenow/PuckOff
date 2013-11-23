package com.ladinc.puckoff.core.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.ladinc.puckoff.core.controls.IControls;

public class HockeyPlayer {
	
	float playerSize = 3f;
	
	private static int PIXELS_PER_METER = 10;
	
	private float stickPower = 5000f;

	public Sprite sprite;
	public Body body;
	private World world;
	public Body stick;
	
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
		this.power = 15000f;
		
		createBody();
	}
	
	private void createBody()
	{
		//Dynamic Body  
	    BodyDef bodyDef = new BodyDef();  
	    bodyDef.type = BodyType.DynamicBody;  
	    bodyDef.position.set(this.startPos.x, this.startPos.y);
 
	    //This keeps it that the force up is applied relative to the screen, rather than the direction that the player is facing
	    bodyDef.fixedRotation = true;
	    this.body = world.createBody(bodyDef); 

	    CircleShape dynamicCircle = new CircleShape();  
	    dynamicCircle.setRadius(playerSize);  
	    
	    FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.density = 10.0f;
	    fixtureDef.friction = 0.3f;  
	    fixtureDef.restitution = 0.5f;
	    fixtureDef.shape = dynamicCircle;
	     
	    this.body.createFixture(fixtureDef);
	    
	    createHockeyStick();
		
	}
	
	private void createHockeyStick()
	{
		Vector2 position= this.body.getWorldCenter();
		
		BodyDef bodyDef = new BodyDef();  
	    bodyDef.type = BodyType.DynamicBody;  
	    bodyDef.position.set(position.x - 6f, position.y);
	    //bodyDef.fixedRotation = true;
	    
	    this.stick = world.createBody(bodyDef); 
	    
	    FixtureDef fixtureDef = new FixtureDef();
		PolygonShape boxShape = new PolygonShape();
		boxShape.setAsBox(4f, 0.5f);
		fixtureDef.shape=boxShape;
		fixtureDef.restitution=1f;
		fixtureDef.density = 1f;
	    fixtureDef.friction = 0.3f;  
	    
		stick.createFixture(fixtureDef);
		
		boxShape.dispose();
		
		RevoluteJointDef revoJoint = new RevoluteJointDef();
		revoJoint.initialize(this.body, this.stick, position);
//		revoJoint.bodyA = this.stick;
//		revoJoint.bodyB = this.body;
		revoJoint.collideConnected = false;
//		revoJoint.localAnchorB.set(position);
//		revoJoint.localAnchorA.set(stick.getWorldCenter());
		
		revoJoint.motorSpeed = 0;
		revoJoint.maxMotorTorque = 1000f;
		revoJoint.enableLimit = false;
		revoJoint.enableMotor = true;
		
		world.createJoint(revoJoint);
	}
	
	public void updateMovement(float delta)
	{
		Vector2 movement = this.controller.getMovementInput();
		Vector2 rotation = this.controller.getRotationInput();
		
		Gdx.app.debug("HockeyPlayer - updateMovement", "Movement: x=" + String.valueOf(movement.x) + " y=" + String.valueOf(movement.y));
		
		Vector2 forceVector= new Vector2(this.power*movement.x, this.power*movement.y);
		Vector2 position= this.body.getWorldCenter();
		this.body.applyForce(this.body.getWorldVector(new Vector2(forceVector.x, forceVector.y)), position, true );
		
		
		updateStick(delta, rotation);
	}
	
	public void updateStick(float delta, Vector2 rotation)
	{
		Gdx.app.debug("HockeyPlayer - updateStick", "rotation: x=" + String.valueOf(rotation.x) + " y=" + String.valueOf(rotation.y));
		
		Vector2 forceVector= new Vector2(this.stickPower*rotation.x, this.stickPower*rotation.y);
		Vector2 position= this.stick.getWorldCenter();
		
		//position.x = position.x + 2f;
		this.stick.applyForce(this.stick.getWorldVector(new Vector2(forceVector.x, forceVector.y)), position, true );
	}
}
