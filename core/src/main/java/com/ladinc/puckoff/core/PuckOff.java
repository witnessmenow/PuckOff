package com.ladinc.puckoff.core;

import com.badlogic.gdx.Game;
import com.ladinc.puckoff.core.controls.MyControllerManager;

public class PuckOff extends Game {

	MyControllerManager controllerManager;
	

	@Override
	public void create () 
	{
		controllerManager = new MyControllerManager();
	}

}
