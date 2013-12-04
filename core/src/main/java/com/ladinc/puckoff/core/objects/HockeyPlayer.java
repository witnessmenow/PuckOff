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
	Vector2 startPos;
	
	private float power;

	private OrthographicCamera camera;
	
	private MouseJoint movementJoint;
	
	public HockeyPlayer(World world, int number, IControls controller, Vector2 startPos, OrthographicCamera camera)
	{
		playerNumber = number;
		
		this.camera = camera;
		
		this.world = world;
		this.controller = controller;
		this.startPos = startPos;
		this.power = 15000f;
		
		createBody();
		
		sprite = getPlayerSprite();
		stickSprite = getStickSprite();
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
	
	private Sprite getStickSprite()
    {
    	Texture stickTexture = new Texture(Gdx.files.internal("Images/Objects/stick.png"));
    	
    	return new Sprite(stickTexture);
    }
	
	private Sprite getPlayerSprite()
    {
    	Texture playerTexture = new Texture(Gdx.files.internal("Images/Objects/player_1_noarm.png"));
    	
    	return new Sprite(playerTexture);
    }
	
	public void setSpritePosition(Sprite spr, int PIXELS_PER_METER, Body forLocation, Body forAngle)
	{
		
		spr.setPosition(PIXELS_PER_METER * forLocation.getPosition().x - spr.getWidth()/2,
				PIXELS_PER_METER * forLocation.getPosition().y  - spr.getHeight()/2);
		spr.setRotation((MathUtils.radiansToDegrees * forAngle.getAngle()));
	}
	
	private Vector3 tempVec = new Vector3(0.0f,0.0f,0.0f);
	
	public void updateStick(float delta, Vector2 rotation, Vector2 playerPosition)
	{
		Gdx.app.debug("HockeyPlayer - updateStick", "rotation: x=" + String.valueOf(rotation.x) + " y=" + String.valueOf(rotation.y));
		
//		if(rotation.x == 0f && rotation.y == 0)
//		{
//			//No movement detected, get rid of mouse joint
//			destroyMovementJoint();
//		}
//		else
//		{
//			//Movement Square size
//			float movementSquareMax = 6f;
//			Vector2 moveForce = new Vector2(rotation.x * movementSquareMax, rotation.y * movementSquareMax);
//			Vector2 moveLocation = new Vector2(playerPosition.x + moveForce.x, playerPosition.y + moveForce.y);
//			
//			Vector2 position= this.stick.getWorldCenter();
//			
//			float xDiff = position.x - moveLocation.x;
//			if (xDiff < 0)
//				xDiff = xDiff * (-1);
//			
//			float yDiff = position.y - moveLocation.y;
//			if (yDiff < 0)
//				yDiff = yDiff * (-1);
//			
//			
//			if(xDiff > movementSquareMax)
//			{
//				if(position.x < moveLocation.x)
//				{
//					moveLocation.x -= movementSquareMax;		
//				}
//				else
//				{
//					moveLocation.x += movementSquareMax;
//				}
//				
//				if(moveLocation.y < 0)
//				{
//					moveLocation.y = playerPosition.y + (-1)*movementSquareMax;
//				}
//				else
//				{
//					moveLocation.y = playerPosition.y + movementSquareMax;
//				}
//			}
//			else if(yDiff > movementSquareMax)
//			{
//				if(position.y < moveLocation.y)
//				{
//					moveLocation.y -= movementSquareMax;		
//				}
//				else
//				{
//					moveLocation.y += movementSquareMax;
//				}
//				
//				if(moveLocation.x < 0)
//				{
//					moveLocation.x = playerPosition.x + (-1)*movementSquareMax;
//				}
//				else
//				{
//					moveLocation.x = playerPosition.x + movementSquareMax;
//				}	
//			}
//			
//			//Movement Detected
//			if(this.movementJoint == null)
//			{
//				createMovementJoint(position);
//			}
//			else
//			{
//				movementJoint.setTarget(moveLocation);
//			}
		
		
		if(rotation.x == 0f && rotation.y == 0)
		{
			//No movement detected
			return;
		}
		
		//Movement Square size
		float movementSquareMax = 6f;
		Vector2 moveForce;
		
		if(controller.isRotationRelative())
		{
			tempVec.set(rotation.x, rotation.y, 0);
			camera.unproject(tempVec);
			
			//Camera unproject gives us cameras co-ordinates of the input, this needs to be converted to Box2d co-ords 
			moveForce =  new Vector2(tempVec.x/PIXELS_PER_METER - playerPosition.x, tempVec.y/PIXELS_PER_METER - playerPosition.y);
			Gdx.app.debug("HockeyPlayer - Rotation Relative", "moveForce: x=" + String.valueOf(moveForce.x) + " y=" + String.valueOf(moveForce.y));	
		}
		else
		{
			moveForce = new Vector2(rotation.x * movementSquareMax, rotation.y * movementSquareMax);
			
		}
		//Vector2 moveLocation = new Vector2(playerPosition.x + moveForce.x, playerPosition.y + moveForce.y);
		Vector2 position= this.stick.getWorldCenter();
		
//		float crossToStick = playerPosition.crs(position);
//		float crossToMovement = playerPosition.crs(moveLocation);
//			
//		Gdx.app.debug("HockeyPlayer - Cross Stuff", "rotation: crossToStick=" + String.valueOf(crossToStick) + " crossToMovement=" + String.valueOf(crossToMovement));	
		
		//stickPositionRelativeToPlayer
		Vector2 stickRelativePlayer = new Vector2(position.x - playerPosition.x, position.y - playerPosition.y);
		float angleStick = stickRelativePlayer.angle();
		float angleMovement = moveForce.angle();
		
		Gdx.app.debug("HockeyPlayer - Angle Stuff", "rotation: angleStick=" + String.valueOf(angleStick) + " angleMovement=" + String.valueOf(angleMovement));	
		
		float direction = 1.0f;
		
		if(angleStick + 6f >= angleMovement &&  angleStick - 6f <= angleMovement)
		{
			//nothing to do
			Vector2 currentForce = this.stick.getLinearVelocity();
			this.stick.applyForce(this.stick.getWorldVector(new Vector2(currentForce.x * (-1), currentForce.y * (-1))), position, true );
			
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
		this.stick.applyForce(this.stick.getWorldVector(new Vector2(forceVector.x, forceVector.y)), position, true );
		

	}
}
