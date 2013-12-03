package com.ladinc.puckoff.core.ai;

import com.badlogic.gdx.math.Vector2;
import com.ladinc.puckoff.core.controls.IControls;
import com.ladinc.puckoff.core.objects.HockeyPlayer;

public class SimpleAi implements IControls{
	
	private Vector2 leftMovement =  new Vector2(0,0);
	
	private Vector2 rightMovement =  new Vector2(0,0);
	
	private boolean divePressed;
	
	public HockeyPlayer player;

	@Override
	public Vector2 getMovementInput() {
		return leftMovement;
	}

	@Override
	public Vector2 getRotationInput() {
		return rightMovement;
	}

	@Override
	public boolean getDivePressed() {
		// TODO Auto-generated method stub
		return this.divePressed;
	}

	@Override
	public boolean isActive() {
		//If AI exists its active
		return true;
	}

	@Override
	public boolean isRotationRelative() {
		// TODO Auto-generated method stub
		return false;
	}
	
	private Vector2 tempMovement = new Vector2();
	
	public void movementFollowPuck(Vector2 playerLocation, Vector2 puckLocation)
	{
		tempMovement.x = (-1) * getMovementupToOne(playerLocation.x, puckLocation.x);
		
		tempMovement.y = (-1) * getMovementupToOne(playerLocation.y, puckLocation.y);
		
		leftMovement = tempMovement;
	}
	
	private float getMovementupToOne(float ref, float target)
	{
		float temp = (ref - target)/10f;
		
		if(temp > 0.0f)
		{
			if(temp > 1.0f)
				temp = 1.0f;
		}
		else
		{
			if(temp < -1.0f)
				temp = -1.0f;
		}
		
		return temp;
		
	}

}
