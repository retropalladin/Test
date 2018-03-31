package com.test.game.utils;


import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.test.game.screens.GameplayScreen;

public class ScreensManager {

    public static final ScreensManager instance = new ScreensManager();
    private final GameplayScreen gameplayScreen;

    private ScreensManager() {
        AssetManager am = new AssetManager();
        Assets.instance.init(am);
        gameplayScreen = GameplayScreen.instance;
    }

    public Screen getStartScreen(){
        gameplayScreen.resetScreen(null); //here Player should be. LevelName will be incapsulated into Player.
        return gameplayScreen;
    }

    public void disposeScreens(){
        gameplayScreen.shutdown();
        Assets.instance.dispose();
    }
}
