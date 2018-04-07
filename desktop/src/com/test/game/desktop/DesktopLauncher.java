package com.test.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.test.game.MyTestGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.width = MyTestGame.GAME_DESKTOP_WIDTH;
		config.height = MyTestGame.GAME_DESKTOP_HEIGHT;

		new LwjglApplication(new MyTestGame(), config);
	}
}
