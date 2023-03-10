package com.mygdx.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setWindowedMode(GrapeBop.WIDTH, GrapeBop.HEIGHT);  //edited to change size of window
		config.setForegroundFPS(60);
		config.setTitle(GrapeBop.TITLE);
		new Lwjgl3Application(new GrapeBop(), config);
	}
}
