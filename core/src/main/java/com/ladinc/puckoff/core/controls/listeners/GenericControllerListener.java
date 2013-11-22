package com.ladinc.puckoff.core.controls.listeners;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.math.Vector3;
import com.ladinc.puckoff.core.controls.GamePadControls;
import com.ladinc.puckoff.core.controls.GamePadControls.AnalogStick;

public class GenericControllerListener implements ControllerListener
{
	public GamePadControls controls;
	
	public GenericControllerListener()
	{
		this.controls = new GamePadControls(this);
	}
	
	public int LeftAxisX;
	public int LeftAxisY;
	
	public int RightAxisX;
	public int RightAxisY;
	
	public int DiveButton;

	@Override
	public boolean accelerometerMoved(Controller arg0, int arg1, Vector3 arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean axisMoved(Controller arg0, int arg1, float arg2) {
		
		Gdx.app.debug("GenericControllerListener", "Axis moved: arg1=" + String.valueOf(arg1) + " arg2=" + String.valueOf(arg2));

		//Cant Use switch, arguements must be constants
		
		if(arg1 == LeftAxisX)
			controls.setAnalogMovementX(AnalogStick.left, arg2);
		else if (arg1 == LeftAxisY)
			controls.setAnalogMovementY(AnalogStick.left, arg2);
		else if (arg1 == RightAxisX)
			controls.setAnalogMovementY(AnalogStick.right, arg2);
		else if (arg1 == RightAxisY)
			controls.setAnalogMovementY(AnalogStick.right, arg2);
				

		return false;
	}

	@Override
	public boolean buttonDown(Controller arg0, int arg1) {
		
		Gdx.app.debug("GenericControllerListener", "buttonDown: arg1=" + String.valueOf(arg1));
		
		if(arg1 == DiveButton)
			controls.setDiveButton(true);
		
		
		return false;
	}

	@Override
	public boolean buttonUp(Controller arg0, int arg1) {
		if(arg1 == DiveButton)
			controls.setDiveButton(false);
		
		return false;
	}

	@Override
	public void connected(Controller arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disconnected(Controller arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean povMoved(Controller arg0, int arg1, PovDirection arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean xSliderMoved(Controller arg0, int arg1, boolean arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean ySliderMoved(Controller arg0, int arg1, boolean arg2) {
		// TODO Auto-generated method stub
		return false;
	}
}
