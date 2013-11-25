package com.ladinc.puckoff.core.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.ladinc.puckoff.core.utilities.GenericEnums;

public class Goal 
{
    BoxProp side1;
    BoxProp side2;
    BoxProp back;
    
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
}
