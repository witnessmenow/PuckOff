package com.ladinc.puckoff.core.controls.listeners.ouya;

import com.badlogic.gdx.controllers.mappings.Ouya;
import com.ladinc.puckoff.core.controls.listeners.GenericControllerListener;

public class OuyaListener extends GenericControllerListener{
	
	public OuyaListener()
	{
		this.DiveButton = Ouya.BUTTON_O;
		
		this.LeftAxisX = Ouya.AXIS_LEFT_X;
		this.LeftAxisY = Ouya.AXIS_LEFT_Y;
		
		this.RightAxisX = Ouya.AXIS_RIGHT_X;
		this.RightAxisY= Ouya.AXIS_RIGHT_Y;
	}

}
