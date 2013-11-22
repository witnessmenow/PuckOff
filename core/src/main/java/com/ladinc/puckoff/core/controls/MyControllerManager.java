package com.ladinc.puckoff.core.controls;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.mappings.Ouya;
import com.ladinc.puckoff.core.controls.listeners.desktop.XboxListener;
import com.ladinc.puckoff.core.controls.listeners.ouya.OuyaListener;

public class MyControllerManager {

	public ArrayList<IControls> inActiveControls;
	public ArrayList<IControls> controls;
	
	public MyControllerManager()
	{
		inActiveControls = new ArrayList<IControls>();
		controls = new ArrayList<IControls>();
		
		setUpControls();
	}
	
	private void setUpControls()
	{
		this.controls.clear();
		this.inActiveControls.clear();
    	
        for (Controller controller : Controllers.getControllers()) {
            Gdx.app.debug("ControllerManager", "setUpControls - " + controller.getName());
            addControllerToList(controller);
            

        }
		
	}
	
	private void addControllerToList(Controller controller)
    {
		switch (Gdx.app.getType())
		{
			case Desktop:
				Gdx.app.debug("ControllerManager", "addControllerToList - Desktop");
				
				Gdx.app.debug("ControllerManager", "Added Listener for Windows Xbox Controller");
	        	
				XboxListener desktopListener = new XboxListener();
	            controller.addListener(desktopListener);
	            inActiveControls.add(desktopListener.controls);
	            
				break;
			case Android:
				Gdx.app.debug("ControllerManager", "addControllerToList - Android");
				if(Ouya.runningOnOuya)
		        {
		        	Gdx.app.debug("ControllerManager", "Added Listener for Ouya Controller");
		        	
		        	OuyaListener ouyaListener = new OuyaListener();
		            controller.addListener(ouyaListener);
		            
		            inActiveControls.add(ouyaListener.controls);
		        }
				break;
			case WebGL:
				Gdx.app.debug("ControllerManager", "addControllerToList - WebGL/HTML5");
				break;
			
		
		}
    }
	
}
