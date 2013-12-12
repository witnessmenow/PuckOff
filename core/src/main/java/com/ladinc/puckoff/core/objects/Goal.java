package com.ladinc.puckoff.core.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.ladinc.puckoff.core.collision.CollisionInfo;
import com.ladinc.puckoff.core.collision.CollisionInfo.CollisionObjectType;
import com.ladinc.puckoff.core.utilities.GenericEnums;
import com.ladinc.puckoff.core.utilities.GenericEnums.Direction;

public class Goal 
{
    BoxProp side1;
    BoxProp side2;
    BoxProp back;
    
    public Body scoringZone;
    
    
    
    //How Deep the goals are
	private static float postLength = 8;
	private static float goalThickness = 1;
	//How Long the goals are
	private static float barLength = 18;
	
	GenericEnums.Direction facing;
	
	float goalLineX;
	float goalY;
	
	public Goal(World world, float x, float y, GenericEnums.Direction facing)
    {
    	
    	int direction = 1;
    	
    	this.facing = facing;
    	
		if(facing == GenericEnums.Direction.right)
			direction = -1;
    		
    	
		this.back = new BoxProp(world, goalThickness, barLength, new Vector2 (x , y));
    	
    	float postsXCoord = x + (direction * (-(goalThickness/2) + (postLength/2))); 
    	
    	this.side1 = new BoxProp(world, postLength, goalThickness, new Vector2 ( postsXCoord  , y -(barLength/2 +  goalThickness/2)));
    	this.side2 = new BoxProp(world, postLength, goalThickness, new Vector2 ( postsXCoord  , y +(barLength/2 +  goalThickness/2)));
    	
    	this.goalLineX = x + (direction *(postLength - (goalThickness/2)));
    	this.goalY = y;
    		
    }
	
	public void createScoringZone(World world, float puckSize)
	{
		Vector2 zoneCenter = new Vector2();
		
		float ySize = this.side2.body.getPosition().y - this.side1.body.getPosition().y;
		zoneCenter.y = this.side2.body.getPosition().y - ySize/2;
		
		float xSize;
		
		if (facing == Direction.left)
		{
			xSize = (this.back.body.getPosition().x - goalThickness/2 + postLength - puckSize) - (this.back.body.getPosition().x + goalThickness/2);
			zoneCenter.x =(this.back.body.getPosition().x + goalThickness/2) + xSize/2;
		}
		else
		{
			xSize = (this.back.body.getPosition().x - goalThickness/2) - (this.back.body.getPosition().x + goalThickness/2 - postLength + puckSize);
			zoneCenter.x =(this.back.body.getPosition().x - goalThickness/2) - xSize/2;
		}
		
		ySize -= goalThickness;
		
		//init body 
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set(zoneCenter);
		this.scoringZone = world.createBody(bodyDef);

		//init shape
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.density = 1.0f;
		fixtureDef.isSensor=true;
		PolygonShape zoneShape = new PolygonShape();
		zoneShape.setAsBox(xSize/2, ySize/2);
		fixtureDef.shape = zoneShape;
		this.scoringZone.createFixture(fixtureDef);
		
		this.scoringZone.setUserData(new CollisionInfo("", CollisionObjectType.ScoreZone));
		
		zoneShape.dispose();
	}
	
	
	
}
