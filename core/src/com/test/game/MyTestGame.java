package com.test.game;

import com.badlogic.gdx.Game;
import com.test.game.utils.Constants;
import com.test.game.utils.ScreensManager;

public class MyTestGame extends Game {

	ScreensManager screensManager = ScreensManager.getInstance(gameInstance);

	private static MyTestGame gameInstance;
	public static synchronized MyTestGame getInstance() {
		return gameInstance;
	}
	@Override
	public void create() {
		screensManager.setScreen(Constants.Screens.MAINMENU_SCREEN);
	}
}