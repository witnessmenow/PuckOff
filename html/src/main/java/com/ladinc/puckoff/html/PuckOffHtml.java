package com.ladinc.puckoff.html;

import com.ladinc.puckoff.core.PuckOff;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

public class PuckOffHtml extends GwtApplication {
	@Override
	public ApplicationListener getApplicationListener () {
		return new PuckOff();
	}
	
	@Override
	public GwtApplicationConfiguration getConfig () {
		return new GwtApplicationConfiguration(1920, 1080);
	}
}
