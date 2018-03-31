package com.test.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.test.game.screens.GameplayScreen;
import com.test.game.screens.MainMenuScreen;
import com.test.game.utils.Assets;
import com.test.game.utils.Constants;
import com.test.game.utils.ScreensManager;

public class MyTestGame extends Game {

	MainMenuScreen mainMenuScreen = new MainMenuScreen(this);
	GameplayScreen gameplayScreen = new GameplayScreen(this);

	public MyTestGame() {
		Assets.instance.init(new AssetManager());
	}

	public void create() {
		changeScreen(Constants.Screens.GAMEPLAY_SCREEN);
	}

	public void changeScreen(int screen) {
		switch (screen) {
			case Constants.Screens.MAINMENU_SCREEN:
				setScreen(mainMenuScreen);
				break;
			case Constants.Screens.GAMEPLAY_SCREEN:
				setScreen(gameplayScreen);
				break;
		}
	}
}
