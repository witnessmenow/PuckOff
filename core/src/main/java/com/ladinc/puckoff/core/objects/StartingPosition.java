package com.ladinc.puckoff.core.objects;

import com.badlogic.gdx.math.Vector2;

public class StartingPosition 
{
	Vector2 location;
	float angle;
	
	public StartingPosition(Vector2 loc, float ang)
	{
		this.location = loc;
		this.angle = ang;
	}
}
