package com.ladinc.puckoff.core.screens;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.ladinc.puckoff.core.PuckOff;
import com.ladinc.puckoff.core.controls.IControls;
import com.ladinc.puckoff.core.objects.HockeyPlayer;
import com.ladinc.puckoff.core.objects.Puck;
import com.ladinc.puckoff.core.objects.Rink;

public class HockeyScreen implements Screen 
{
	private OrthographicCamera camera;
    private SpriteBatch spriteBatch;

    private World world;
    
    private Box2DDebugRenderer debugRenderer;
    
    //Used for sprites etc
	private int screenWidth;
    private int screenHeight;
    
    //Used for Box2D
    private float worldWidth;
    private float worldHeight;
    private static int PIXELS_PER_METER = 10;  
    
    private Vector2 center;
    
    private PuckOff game;
    
    //Game Actor Objects
    public List<HockeyPlayer> hockeyPlayerList;
    private Rink rink;
    private Puck puck;
    
    public HockeyScreen(PuckOff game)
    {
    	this.game = game;
    	
    	this.screenWidth = this.game.screenWidth;
    	this.screenHeight = this.game.screenHeight;
    	
    	this.worldHeight = this.screenHeight / PIXELS_PER_METER;
    	this.worldWidth = this.screenWidth / PIXELS_PER_METER;
    	
    	this.center = new Vector2(worldWidth / 2, worldHeight / 2);
    	
    	this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, this.screenWidth, this.screenHeight);
        
        this.debugRenderer = new Box2DDebugRenderer();
    }
    
    
    
    //This is the main game loop, it is repeatedly called
	@Override
	public void render(float delta) 
	{
		camera.update();
		spriteBatch.setProjectionMatrix(camera.combined);
		
		world.step(Gdx.app.getGraphics().getDeltaTime(), 3, 3);
        world.clearForces();
        
        for(HockeyPlayer hp: hockeyPlayerList)
		{
        	hp.updateMovement(delta);
		}
        
        this.puck.update();
        
		this.spriteBatch.begin();
		
		renderSprites(this.spriteBatch);
		
		this.spriteBatch.end();
		
		debugRenderer.render(world, camera.combined.scale(PIXELS_PER_METER,PIXELS_PER_METER,PIXELS_PER_METER));
		
	}
	
	//Sprites are drawn in order, so something drawn first will be bottom of the pile so to speak i.e. draw background first
	private void renderSprites(SpriteBatch spriteBatch)
    {
		//Sets the background colour of the canvas
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        
        this.rink.rinkImage.draw(spriteBatch);
    }

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	//Show gets called when the screen is first navigated to
	@Override
	public void show() {
		world = new World(new Vector2(0.0f, 0.0f), true);
		
		this.rink = new Rink(world);
		
		spriteBatch = new SpriteBatch();
		
		createPlayers();
		this.puck = new Puck(world, this.center);
		
		
	}
	
	private void createPlayers()
	{
		Gdx.app.debug("Hockey Screen", "createPlayers()");
		
		if(hockeyPlayerList == null)
		{
			hockeyPlayerList= new ArrayList<HockeyPlayer>();		
		}
		
		int nextPlayerNumber = this.hockeyPlayerList.size() + 1;
		
		for(IControls ic : this.game.controllerManager.inActiveControls)
		{
			Gdx.app.debug("Hockey Screen", "createPlayers() - looping through controllers");
			
			boolean controllerHasPlayer = false;
			for(HockeyPlayer hp: hockeyPlayerList)
			{
				Gdx.app.debug("Hockey Screen", "createPlayers() - checking to see is controller already assigned");
				 if(hp.controller == ic)
					 controllerHasPlayer = true;
				 
			 }
			
			if(!controllerHasPlayer)
			{
				Gdx.app.debug("Hockey Screen", "createPlayers() - creating new player");
				hockeyPlayerList.add(new HockeyPlayer(world, nextPlayerNumber, ic, this.center));
				nextPlayerNumber++;
			}
		}
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
