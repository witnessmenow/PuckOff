package com.ladinc.puckoff.core.controls.listeners;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.ladinc.puckoff.core.controls.GamePadControls;
import com.ladinc.puckoff.core.controls.KeyboardAndMouseControls;

public class KeyboardAndMouseListener implements InputProcessor 
{
	public KeyboardAndMouseControls controls;
	
	public KeyboardAndMouseListener()
	{
		this.controls = new KeyboardAndMouseControls(this);
	}
	
	int upKey = Input.Keys.DPAD_UP;
	int downKey = Input.Keys.DPAD_DOWN;
	int leftKey = Input.Keys.DPAD_LEFT;
	int rightKey = Input.Keys.DPAD_RIGHT;
	
	int mouseLeft = Input.Buttons.LEFT;
	
	boolean upKeyPressed = false;
	boolean downKeyPressed = false;
	boolean leftKeyPressed = false;
	boolean rightKeyPressed = false;
	

	@Override
	public boolean keyDown(int keycode) 
	{
		Gdx.app.debug("GenericControllerListener", "keyDown: keycode=" + String.valueOf(keycode));
		
		if(keycode == upKey)
		{
			controls.setMovementY(1.0f);
			upKeyPressed = true;
		}
		else if(keycode == downKey)
		{
			controls.setMovementY(-1.0f);
			downKeyPressed = true;
		}
		else if(keycode == leftKey)
		{
			controls.setMovementX(-1.0f);
			leftKeyPressed = true;
		}
		else if(keycode == rightKey)
		{
			controls.setMovementX(1.0f);
			rightKeyPressed = true;
		}
		
		return false;
	}

	@Override
	public boolean keyUp(int keycode) 
	{
		Gdx.app.debug("GenericControllerListener", "keyUp: keycode=" + String.valueOf(keycode));
		
		if(keycode == upKey)
		{
			upKeyPressed = false;
			if(!downKeyPressed)
				controls.setMovementY(0.0f);
		}
		else if(keycode == downKey)
		{
			downKeyPressed = false;
			if(!upKeyPressed)
				controls.setMovementY(0.0f);
		}
		else if(keycode == leftKey)
		{
			leftKeyPressed = false;
			if(!rightKeyPressed)
				controls.setMovementX(0.0f);
		}
		else if(keycode == rightKey)
		{
			rightKeyPressed = false;
			if(!leftKeyPressed)
				controls.setMovementX(0.0f);
		}
		
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		Gdx.app.debug("GenericControllerListener", "touchDown: screenX=" + String.valueOf(screenX) + "screenY=" + String.valueOf(screenY) + "pointer=" + String.valueOf(pointer) + "button=" + String.valueOf(button));
		
		float x = (float) screenX;
		float y = (float) screenY;
		
		controls.setRotationReferencePoint(x, y);
		
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {

		Gdx.app.debug("GenericControllerListener", "touchUp: screenX=" + String.valueOf(screenX) + "screenY=" + String.valueOf(screenY) + "pointer=" + String.valueOf(pointer) + "button=" + String.valueOf(button));
		
		controls.setRotationReferencePoint(0.0f, 0.0f);
		controls.setRotationMovementPoint(0.0f, 0.0f);
		
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		Gdx.app.debug("GenericControllerListener", "touchDragged: screenX=" + String.valueOf(screenX) + "screenY=" + String.valueOf(screenY) + "pointer=" + String.valueOf(pointer));
		
		float x = (float) screenX;
		float y = (float) screenY;
		
		controls.setRotationMovementPoint(x, y);
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {

		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
