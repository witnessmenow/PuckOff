package com.ladinc.puckoff.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.ladinc.puckoff.core.PuckOff;
import com.ladinc.puckoff.core.objects.Rink;

public class HockeyScreen implements Screen 
{
	private OrthographicCamera camera;
    private SpriteBatch spriteBatch;

    private World world;
    
    private Box2DDebugRenderer debugRenderer;
    
    private Rink rink;
    
    //Used for sprites etc
	private int screenWidth;
    private int screenHeight;
    
    //Used for Box2D
    private float worldWidth;
    private float worldHeight;
    private static int PIXELS_PER_METER = 10;  
    
    private Vector2 center;
    
    private PuckOff game;
    
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
