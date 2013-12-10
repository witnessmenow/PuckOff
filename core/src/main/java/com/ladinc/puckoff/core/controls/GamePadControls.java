package com.ladinc.puckoff.core.controls;

import com.badlogic.gdx.math.Vector2;
import com.ladinc.puckoff.core.controls.listeners.GenericControllerListener;
import com.ladinc.puckoff.core.utilities.GenericEnums.Identifier;

public class GamePadControls implements IControls
{

	private float TRIGGER_DEADZONE = 0.350f;
	
	private Vector2 leftMovement;
	
	private GenericControllerListener listener;
	
	private Vector2 rightMovement;
	
	private boolean divePressed;
	
	public boolean active;
	
	private Identifier ident = null;
	
	public GamePadControls(GenericControllerListener listen)
	{
		this.listener = listen;
		this.leftMovement = new Vector2(0,0);
		this.rightMovement = new Vector2(0,0);
	}
	
	private void activateController()
	{
		active = true;
	}
	
	public static enum AnalogStick{left, right};
	
	public void setAnalogMovementX(AnalogStick stick, float x)
	{
		switch(stick)
		{
			case left:
				leftMovement.x = processAndActivate(x, TRIGGER_DEADZONE);
				break;
			case right:
				rightMovement.x = processAndActivate(x, TRIGGER_DEADZONE);
				break;
		
		}
	}
	
	public void setAnalogMovementY(AnalogStick stick, float y)
	{
		//inverting direction so up is up
		
		switch(stick)
		{
			case left:
				leftMovement.y = (-1)*(processAndActivate(y, TRIGGER_DEADZONE));
				break;
			case right:
				rightMovement.y = (-1)*(processAndActivate(y, TRIGGER_DEADZONE));
				break;
		
		}
	}
	
	public float processAndActivate(float movement, float deadzone)
	{
		float processedMovement = processMovment(movement, deadzone);
		
		if (!active && processedMovement > 0)
		{
			activateController();
		}
		
		return processedMovement;
	}
	
	public float processMovment(float movement, float deadzone)
	{
		//Overall power ignoring direction
		float power = movement;
		
		if (movement < 0)
			power = movement*(-1);
	
		if(power <= deadzone)
		{
			//The direction being moved is not greater than the deadzone, ignoring movement
			return 0f;
		}
		else
		{
//			if(!active)
//				activateController();
			
			//have a meaningful value for X
			float movementAbovePower = power - deadzone;
			float availablePower = 1f - deadzone;
			movementAbovePower = movementAbovePower/availablePower;
			
			if(movement > 0)
			{
				return movementAbovePower;
			}
			else
			{
				//convert result to take into account direction
				return (-1)*movementAbovePower;
			}
		}
		
	}
	
	public void setDiveButton(boolean pressed)
	{
		if (!active && pressed)
		{
			activateController();
		}
		
		this.divePressed = pressed;
	}
	
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
		return divePressed;
	}

	@Override
	public boolean isActive() {
		return this.active;
	}

	@Override
	public boolean isRotationRelative() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Identifier getIdentifier() {
		// TODO Auto-generated method stub
		return this.ident;
	}

	@Override
	public void setIdentifier(Identifier identifier) {
		this.ident = identifier;
		
	}


}
