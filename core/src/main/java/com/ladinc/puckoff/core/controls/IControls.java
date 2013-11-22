package com.ladinc.puckoff.core.controls;

import com.badlogic.gdx.math.Vector2;

public interface IControls 
{
	
	public Vector2 getMovementInput();
	
	public Vector2 getRotationInput();
	
	public boolean getDivePressed();
	
	
	
	//TODO: Add start etc

}
