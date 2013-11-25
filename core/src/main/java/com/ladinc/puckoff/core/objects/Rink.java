package com.ladinc.puckoff.core.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.ladinc.puckoff.core.utilities.BodyEditorLoader;
import com.ladinc.puckoff.core.utilities.GenericEnums;

public class Rink 
{
	private static int PIXELS_PER_METER = 10;
	
	//478 y from the top
	//350 x from the left

	public Sprite rinkImage;
	
	public Body body;
	
	public Goal leftGoal;
	public Goal rightGoal;
	
	public Rink(World world, float worldHeight, float worldWidth)
	{
		BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal("PBE/iceRink2.json"));
		rinkImage = getRinkSprite();
		rinkImage.setPosition(0, 0);
		
		BodyDef bd = new BodyDef();
	    bd.position.set(0, 0);
	    //bd.type = BodyType.DynamicBody;
	    
	    FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.density = 1.0f;
		
		this.body = world.createBody(bd);
		
		loader.attachFixture(body, "iceRink", fixtureDef, 192);
		
		leftGoal = new Goal(world, 30f, worldHeight - 47.8f, GenericEnums.Direction.left);
		rightGoal = new Goal(world, worldWidth - 30f, worldHeight - 47.8f, GenericEnums.Direction.right);
		
	}
	
	private Sprite getRinkSprite()
    {
    	Texture rinkTexture = new Texture(Gdx.files.internal("Images/Objects/IceRinkOffSet.png"));
    	
    	return new Sprite(rinkTexture);
    }
}
