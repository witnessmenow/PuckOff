package com.ladinc.puckoff.core.controls;

import java.util.ArrayList;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.mappings.Ouya;
import com.ladinc.puckoff.core.controls.listeners.KeyboardAndMouseListener;
import com.ladinc.puckoff.core.controls.listeners.ListenerForNewControllers;
import com.ladinc.puckoff.core.controls.listeners.desktop.XboxListener;
import com.ladinc.puckoff.core.controls.listeners.ouya.OuyaListener;
import com.ladinc.puckoff.core.utilities.GenericEnums.Identifier;

public class MyControllerManager {

	public ArrayList<IControls> inActiveControls;
	public ArrayList<IControls> controls;
	
	public ArrayList<Identifier> usedIdentifiers;
	public ArrayList<Identifier> orderedIdentifiers;
	
	public MyControllerManager()
	{
		inActiveControls = new ArrayList<IControls>();
		controls = new ArrayList<IControls>();
		
		setUpControls();
		resetIdentifiers();
	}
	
	public void resetIdentifiers()
	{
		usedIdentifiers = new ArrayList<Identifier>();
		orderedIdentifiers = new ArrayList<Identifier>();
		orderedIdentifiers.add(Identifier.red);
		orderedIdentifiers.add(Identifier.blue);
		orderedIdentifiers.add(Identifier.yellow);
		orderedIdentifiers.add(Identifier.green);
		orderedIdentifiers.add(Identifier.orange);
		orderedIdentifiers.add(Identifier.darkblue);
		orderedIdentifiers.add(Identifier.purple);
		
		
	}
	
	public Identifier getNextAvailableIdentifer()
	{
		
		
		for (Identifier ident : orderedIdentifiers) 
		{
			 if(!usedIdentifiers.contains(ident))
			 {
				 usedIdentifiers.add(ident);
				 return ident;
			 }
		}
		
		//All previous identifiers must be used
		return Identifier.red;
	}
	
	private void setUpControls()
	{
		this.controls.clear();
		this.inActiveControls.clear();
    	
        for (Controller controller : Controllers.getControllers()) {
            Gdx.app.debug("ControllerManager", "setUpControls - " + controller.getName());
            addControllerToList(controller);
            

        }
        if(Gdx.app.getType() == ApplicationType.Desktop)
        {
        	KeyboardAndMouseListener inputProcess = new KeyboardAndMouseListener();
        	inActiveControls.add(inputProcess.controls);
        	Gdx.input.setInputProcessor(inputProcess);
        }
	}
	
	public void addControllerToList(Controller controller)
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
	
	public boolean checkForNewControllers()
	{
		ArrayList<IControls> tempControllers = (ArrayList<IControls>) inActiveControls.clone();
		boolean foundNew = false;
		
		for (IControls cont : tempControllers) 
		{
			if(cont.isActive())
			{
				if(!this.controls.contains(cont))
				{
					cont.setIdentifier(getNextAvailableIdentifer());
					this.controls.add(cont);
					this.inActiveControls.remove(cont);
					foundNew = true;
				}
			}
		}
		
		return foundNew;
	}
	
}
