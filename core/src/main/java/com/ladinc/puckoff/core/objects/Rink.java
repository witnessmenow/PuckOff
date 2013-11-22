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

public class Rink 
{

	public Sprite rinkImage;
	
	public Body body;
	
	public Rink(World world)
	{
		BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal("PBE/iceRink2.json"));
		rinkImage = getRinkSprite();
		rinkImage.setPosition(0, 0);
		
		BodyDef bd = new BodyDef();
	    bd.position.set(0, 0);
	    bd.type = BodyType.StaticBody;
	    
	    FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.density = 1.0f;
		
		this.body = world.createBody(bd);
		
		loader.attachFixture(body, "iceRink", fixtureDef, 192);
		
	}
	
	private Sprite getRinkSprite()
    {
    	Texture rinkTexture = new Texture(Gdx.files.internal("Images/Objects/IceRinkOffSet.png"));
    	
    	return new Sprite(rinkTexture);
    }
}
