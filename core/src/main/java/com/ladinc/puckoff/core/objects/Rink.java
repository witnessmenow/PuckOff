package com.ladinc.puckoff.core.objects;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.ladinc.puckoff.core.utilities.BodyEditorLoader;
import com.ladinc.puckoff.core.utilities.GenericEnums;
import com.ladinc.puckoff.core.utilities.GenericEnums.Side;

public class Rink 
{
	private static int PIXELS_PER_METER = 10;
	
	//478 y from the top
	//350 x from the left
	private static final float yAxisCenter = 60.2f;
	Vector2 worldCenter;
	
	public Sprite rinkImage;
	
	public Body body;
	
	public Goal leftGoal;
	public Goal rightGoal;
	
	public Rink(World world, float worldHeight, float worldWidth, Vector2 center)
	{
		BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal("PBE/iceRink2.json"));
		rinkImage = Rink.getRinkSprite();
		rinkImage.setPosition(0, 0);
		
		BodyDef bd = new BodyDef();
	    bd.position.set(0, 0);
	    //bd.type = BodyType.DynamicBody;
	    
	    FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.density = 1.0f;
		
		this.worldCenter = center;
		
		initiateStartingPositions();
		resetPositions();
		
		this.body = world.createBody(bd);
		
		loader.attachFixture(body, "iceRink", fixtureDef, 192);
		
		leftGoal = new Goal(world, 30f, yAxisCenter, Side.Home);
		rightGoal = new Goal(world, worldWidth - 30f, yAxisCenter, Side.Away);
		
	}
	
	private static Sprite getRinkSprite()
    {
    	Texture rinkTexture = new Texture(Gdx.files.internal("Images/Objects/IceRinkOffSet1.png"));
    	
    	return new Sprite(rinkTexture);
    }
	
	public Vector2 getPuckStartingPoint()
	{
		return new Vector2(this.worldCenter.x, Rink.yAxisCenter);
	}
	
	int lastUsedHomePosition;
	int lastUsedAwayPosition;
	
	public void resetPositions()
	{
		lastUsedAwayPosition = 0;
		lastUsedHomePosition = 0;
	}
	
	private void initiateStartingPositions()
	{
		homeStartingPositionList = new ArrayList<StartingPosition>();
		
		//Face Off
		homeStartingPositionList.add(new StartingPosition(new Vector2(this.worldCenter.x - 7.6f, Rink.yAxisCenter), (float)Math.toRadians(180 - 15)));
		homeStartingPositionList.add(new StartingPosition(new Vector2(this.worldCenter.x - 16.8f, 36.3f), (float)Math.toRadians(180)));
		homeStartingPositionList.add(new StartingPosition(new Vector2(this.worldCenter.x - 16.8f, 83.2f), (float)Math.toRadians(180)));
		homeStartingPositionList.add(new StartingPosition(new Vector2(this.worldCenter.x - 56f, Rink.yAxisCenter), (float)Math.toRadians(180)));
		
		awayStartingPositionList = new ArrayList<StartingPosition>();
		
		awayStartingPositionList.add(new StartingPosition(new Vector2(this.worldCenter.x + 7.6f, Rink.yAxisCenter), (float)Math.toRadians(15)));
		awayStartingPositionList.add(new StartingPosition(new Vector2(this.worldCenter.x + 16.8f, 36.3f), (float)Math.toRadians(0)));
		awayStartingPositionList.add(new StartingPosition(new Vector2(this.worldCenter.x + 16.8f, 83.2f), (float)Math.toRadians(0)));
		awayStartingPositionList.add(new StartingPosition(new Vector2(this.worldCenter.x + 56f, Rink.yAxisCenter), (float)Math.toRadians(0)));
	}
	
	private List<StartingPosition> homeStartingPositionList;
	private List<StartingPosition> awayStartingPositionList;
	
	public StartingPosition getPlayerStartingPosition(Side side, int playerNumber)
	{
		int index = 0;
		
		if(side == Side.Home)
		{
			index = lastUsedHomePosition;
			
			if((this.lastUsedHomePosition + 1) < this.homeStartingPositionList.size())
			{
				this.lastUsedHomePosition++;
			}
			
			return homeStartingPositionList.get(index);
		}
		else
		{
			index = lastUsedAwayPosition;
			
			if((this.lastUsedAwayPosition + 1) < this.awayStartingPositionList.size())
			{
				this.lastUsedAwayPosition++;
			}
			
			return awayStartingPositionList.get(index);
		}
	}
	
	public void createScoringZones(World world, float puckSize)
	{
		this.leftGoal.createScoringZone(world, puckSize);
		this.rightGoal.createScoringZone(world, puckSize);
	}
}
