package com.ladinc.puckoff.core;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.ladinc.puckoff.core.controls.MyControllerManager;
import com.ladinc.puckoff.core.screens.HockeyScreen;

public class PuckOff extends Game {

	public MyControllerManager controllerManager;
	public int screenWidth = 1920;
    public int screenHeight = 1080;

    public HockeyScreen hockeyScreen;
    
	@Override
	public void create () 
	{
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		
		controllerManager = new MyControllerManager();
		
		createScreens();
		
		setScreen(hockeyScreen);
	}
	
	private void createScreens()
	{
		this.hockeyScreen = new HockeyScreen(this);

	}

}
