package com.ladinc.puckoff.core.controls.listeners.desktop;

import com.ladinc.puckoff.core.controls.mapping.Xbox360WindowsMapper;
import com.ladinc.puckoff.core.controls.listeners.GenericControllerListener;

public class XboxListener  extends GenericControllerListener{
	
	public XboxListener()
	{
		this.DiveButton = Xbox360WindowsMapper.A_BUTTON;
		
		this.LeftAxisX = Xbox360WindowsMapper.LEFT_ANALOG_X;
		this.LeftAxisY = Xbox360WindowsMapper.LEFT_ANALOG_Y;
		
		//Fix this!
		this.RightAxisX = Xbox360WindowsMapper.RIGHT_ANALOG_X;
		this.RightAxisY= Xbox360WindowsMapper.RIGHT_ANALOG_Y;
	}

}
