package com.ladinc.puckoff.core.controls;

import com.badlogic.gdx.math.Vector2;
import com.ladinc.puckoff.core.controls.listeners.GenericControllerListener;
import com.ladinc.puckoff.core.controls.listeners.KeyboardAndMouseListener;
import com.ladinc.puckoff.core.utilities.GenericEnums.Identifier;

public class KeyboardAndMouseControls implements IControls
{
	private Vector2 rotationReferencePoint;
	private Vector2 rotationMovementPoint;
	
	private Vector2 actualRotationPoint;
	
	private Vector2 movement;
	
	public KeyboardAndMouseListener listener;
	
	private Identifier ident = null;
	
	public KeyboardAndMouseControls(KeyboardAndMouseListener listen)
	{
		this.listener = listen;
		this.rotationReferencePoint = new Vector2(0,0);
		this.rotationMovementPoint = new Vector2(0,0);
		this.actualRotationPoint = new Vector2(0,0);
		this.movement = new Vector2(0,0);
	}
	
	public boolean active;

	private void checkForActive()
	{
		if(!active)
			active = true;
	}
	
	public void setRotationReferencePoint(float x, float y)
	{
		checkForActive();
		
		rotationReferencePoint.x = x;
		rotationReferencePoint.y = y;
	}
	
	public void setRotationMovementPoint(float x, float y)
	{
		checkForActive();
		
		rotationMovementPoint.x = x;
		rotationMovementPoint.y = y;
	}
	
	public void setMovementX(float x)
	{
		checkForActive();
		
		movement.x = x;
	}
	
	public void setMovementY(float y)
	{
		checkForActive();
		
		movement.y = y;
	}
	
	@Override
	public Vector2 getMovementInput() {
		// TODO Auto-generated method stub
		return movement;
	}

	@Override
	public Vector2 getRotationInput() 
	{
		actualRotationPoint.x = rotationMovementPoint.x;
		//actualRotationPoint.y = (rotationMovementPoint.y)*(-1);
		actualRotationPoint.y = (rotationMovementPoint.y);

		return actualRotationPoint;
	}

	@Override
	public boolean getDivePressed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isActive() {
		// TODO Auto-generated method stub
		return this.active;
	}

	@Override
	public boolean isRotationRelative() {
		// TODO Auto-generated method stub
		return true;
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
