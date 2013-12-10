package com.ladinc.puckoff.core.controls;

import com.badlogic.gdx.math.Vector2;
import com.ladinc.puckoff.core.utilities.GenericEnums.Identifier;

public interface IControls 
{
	
	public Vector2 getMovementInput();
	
	public Vector2 getRotationInput();
	
	public boolean getDivePressed();
	
	public boolean isActive();
	
	public boolean isRotationRelative();
	
	public void setIdentifier(Identifier identifier);
	
	public Identifier getIdentifier();
	
	//TODO: Add start etc

}
