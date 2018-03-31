package com.test.game;

import com.badlogic.gdx.Game;
import com.test.game.utils.ScreensManager;

public class MyTestGame extends Game {

	@Override
	public void create() {
		setScreen(ScreensManager.instance.getStartScreen());
	}

}