package com.ladinc.puckoff.core.collision;

import com.ladinc.puckoff.core.objects.HockeyPlayer;

public class CollisionInfo {
	
	public String text;
	public CollisionObjectType type;
	
	public HockeyPlayer player;
	
	
	public CollisionInfo(String text, CollisionObjectType type)
	{
		this.text = text;		
		this.type = type;
	}
	
	public CollisionInfo(String text, CollisionObjectType type, HockeyPlayer player)
	{
		this.text = text;		
		this.type = type;
		this.player = player;
	}
	
	public static enum CollisionObjectType{Rink, Goal, Player, ScoreZone, Puck};

}

