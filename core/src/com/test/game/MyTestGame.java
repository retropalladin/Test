package com.test.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.test.game.screens.LoadingScreen;
import com.test.game.screens.ScreensManager;
import com.test.game.utils.Assets;

public class MyTestGame extends Game {

	public static  int GAME_DESKTOP_WIDTH = 720;
	public static  int GAME_DESKTOP_HEIGHT = 420;

	public AssetManager assetManager;
	public ScreensManager screensManager;

	@Override
	public void create() {
		assetManager = new AssetManager();
		screensManager = new ScreensManager(this);
	}
	@Override
	public void render() {
		super.render();
	}
	@Override
	public void dispose() {
		super.dispose();

		assetManager.dispose();
		screensManager.dispose();
	}
}