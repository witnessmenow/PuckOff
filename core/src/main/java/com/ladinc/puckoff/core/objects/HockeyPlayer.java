package com.ladinc.puckoff.core.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.ladinc.puckoff.core.controls.IControls;
import com.ladinc.puckoff.core.utilities.GenericEnums.Side;

public class HockeyPlayer {
	
	float playerSize = 3f;
	
	private static int PIXELS_PER_METER = 10;
	
	private float stickPower = 5000f;

	public Sprite sprite;
	public Sprite stickSprite;
	
	public Body body;
	private World world;
	public Body stick;
	
	public int playerNumber;
	
	public IControls controller;
	StartingPosition startPos;
	
	public Side side;
	
	private float power;

	private OrthographicCamera camera;
	
	private MouseJoint movementJoint;
	
	public HockeyPlayer(World world, int number, Side side, IControls controller, StartingPosition startPos, OrthographicCamera camera)
	{
		playerNumber = number;
		
		this.camera = camera;
		
		this.world = world;
		this.controller = controller;
		this.startPos = startPos;
		//this.power = 15000f;
		this.power = 10000f;
		
		createBody();
		
		this.stick.setTransform(startPos.location, startPos.angle);
		
		this.side = side;
		
		this.sprite = HockeyPlayer.getPlayerSprite(side);
		this.stickSprite = HockeyPlayer.getStickSprite();
	}
	
	private void createBody()
	{
		//Dynamic Body  
	    BodyDef bodyDef = new BodyDef();  
	    bodyDef.type = BodyType.DynamicBody;  
	    bodyDef.position.set(this.startPos.location.x, this.startPos.location.y);
 
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
		fixtureDef.restitution=0.3f;
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
		revoJoint.maxMotorTorque = 10000f;
		revoJoint.enableLimit = false;
		revoJoint.enableMotor = true;
		
		world.createJoint(revoJoint);
		
		
	}
	
	public void updateSprite(SpriteBatch spriteBatch)
	{
		setSpritePosition(stickSprite, PIXELS_PER_METER, stick, stick);
		stickSprite.draw(spriteBatch);
		setSpritePosition(sprite, PIXELS_PER_METER, body, stick);
		sprite.draw(spriteBatch);
	}
	
	private void createMovementJoint(Vector2 jointStartPoint)
	{
		MouseJointDef def = new MouseJointDef();
		
		//Body A is not used at all, it just needs a body (any body).
		def.bodyA = this.body;
		def.bodyB = this.stick;
		//def.collideConnected = true;
		def.maxForce = 1000.0f * stick.getMass();
		
		def.target.set(jointStartPoint.x, jointStartPoint.y);
		
		this.movementJoint = (MouseJoint)world.createJoint(def);
	}
	
	private void destroyMovementJoint()
	{
		if(this.movementJoint != null)
		{
			world.destroyJoint(movementJoint);
			this.movementJoint = null;
		}
		
	}
	
	public void updateMovement(float delta)
	{
		Vector2 movement = this.controller.getMovementInput();
		Vector2 rotation = this.controller.getRotationInput();
		
		Gdx.app.debug("HockeyPlayer - updateMovement", "Movement: x=" + String.valueOf(movement.x) + " y=" + String.valueOf(movement.y));
		
		Vector2 forceVector= new Vector2(this.power*movement.x, this.power*movement.y);
		Vector2 position= this.body.getWorldCenter();
		this.body.applyForce(this.body.getWorldVector(new Vector2(forceVector.x, forceVector.y)), position, true );
		
		
		updateStick(delta, rotation, position);
	}
	
	public void setSpritePosition(Sprite spr, int PIXELS_PER_METER, Body forLocation, Body forAngle)
	{
		
		spr.setPosition(PIXELS_PER_METER * forLocation.getPosition().x - spr.getWidth()/2,
				PIXELS_PER_METER * forLocation.getPosition().y  - spr.getHeight()/2);
		spr.setRotation((MathUtils.radiansToDegrees * forAngle.getAngle()));
	}
	
	private Vector3 tempVec = new Vector3(0.0f,0.0f,0.0f);
	private float movementSquareMax = 6f;
	private Vector2 moveForce = new Vector2();
	private Vector2 stickCenter = new Vector2();
	
	public void updateStick(float delta, Vector2 rotation, Vector2 playerPosition)
	{
		Gdx.app.debug("HockeyPlayer - updateStick", "rotation: x=" + String.valueOf(rotation.x) + " y=" + String.valueOf(rotation.y));		
		
		if(rotation.x == 0f && rotation.y == 0)
		{
			//No movement detected
			return;
		}
		
		if(controller.isRotationRelative())
		{
			// We are being given a co-ordinate from the screen, we need to convert this to a movement action
			// relative to the players position
			
			tempVec.set(rotation.x, rotation.y, 0);
			camera.unproject(tempVec);
			//temp vec is now the value of the input, but adjusted to match box2ds world
			
			
			//Camera unproject gives us cameras co-ordinates of the input, this needs to be converted to Box2d co-ords 
			moveForce.x = tempVec.x/PIXELS_PER_METER - playerPosition.x; 
			moveForce.y = tempVec.y/PIXELS_PER_METER - playerPosition.y;
			Gdx.app.debug("HockeyPlayer - Rotation Relative", "moveForce: x=" + String.valueOf(moveForce.x) + " y=" + String.valueOf(moveForce.y));	
		}
		else
		{
			moveForce.x = rotation.x * movementSquareMax; 
			moveForce.y = rotation.y * movementSquareMax;
			
		}
		stickCenter = this.stick.getWorldCenter();
			
		//stickPositionRelativeToPlayer
		Vector2 stickRelativePlayer = new Vector2(stickCenter.x - playerPosition.x, stickCenter.y - playerPosition.y);
		float angleStick = stickRelativePlayer.angle();
		float angleMovement = moveForce.angle();
		
		Gdx.app.debug("HockeyPlayer - Angle Stuff", "rotation: angleStick=" + String.valueOf(angleStick) + " angleMovement=" + String.valueOf(angleMovement));	
		
		float direction = 1.0f;
		
		if(angleStick + 6f >= angleMovement &&  angleStick - 6f <= angleMovement)
		{
			//nothing to do
			Vector2 currentForce = this.stick.getLinearVelocity();
			this.stick.applyForce(this.stick.getWorldVector(new Vector2(currentForce.x * (-1), currentForce.y * (-1))), stickCenter, true );
			
			return;
		}
		else if(angleStick > angleMovement)
		{
			if(angleMovement + 180f < angleStick)
				direction = -1.0f;
		}
		else
		{
			if(angleMovement - 180f < angleStick)
				direction = -1.0f;
		}
		
		Vector2 forceVector= new Vector2(0.0f, this.stickPower*direction);
		this.stick.applyForce(this.stick.getWorldVector(new Vector2(forceVector.x, forceVector.y)), stickCenter, true );
		

	}
	
	public static Sprite getStickSprite()
    {
    	Texture stickTexture = new Texture(Gdx.files.internal("Images/Objects/stick.png"));
    	
    	return new Sprite(stickTexture);
    }
	
	public static Sprite getPlayerSprite(Side side)
	{
		Texture playerTexture;
		
		if(side == Side.Home)
			playerTexture = new Texture(Gdx.files.internal("Images/Objects/player_1_noarm.png"));
		else
			playerTexture = new Texture(Gdx.files.internal("Images/Objects/player_2_noarm.png"));
    	
    	return new Sprite(playerTexture);
	}
	
}


