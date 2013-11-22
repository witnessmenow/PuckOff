package com.ladinc.puckoff.java;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import com.ladinc.puckoff.core.PuckOff;

public class PuckOffDesktop {
	public static void main (String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.useGL20 = true;
		config.width = 1920;
		config.height = 1080;
		new LwjglApplication(new PuckOff(), config);
	}
}
