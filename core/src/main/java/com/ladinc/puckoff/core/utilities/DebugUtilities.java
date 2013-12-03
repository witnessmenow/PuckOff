package com.ladinc.puckoff.core.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class DebugUtilities 
{
	
	public static void renderFPSCounter(SpriteBatch batch, OrthographicCamera camera)
	{
		float x = camera.viewportWidth - 55;
		float y = camera.viewportHeight - 15;
		
		int fps = Gdx.graphics.getFramesPerSecond();
		
		BitmapFont fpsFont = new BitmapFont();
		
		if(fps >= 45)
		{
			fpsFont.setColor(Color.GREEN);
		}
		else if (fps >= 30)
		{
			fpsFont.setColor(Color.YELLOW);
		}
		else
		{
			fpsFont.setColor(Color.RED);
		}
		
		fpsFont.draw(batch, "FPS: " + fps, x, y);
 		
	}
	
	

}
