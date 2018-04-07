package com.test.game.screens;


import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.test.game.MyTestGame;
import com.test.game.utils.Assets;

import java.util.HashMap;

/*public class ScreensManager {

    public static final ScreensManager instance = new ScreensManager();
    private final GameplayScreen gameplayScreen;

    private ScreensManager() {
        AssetManager am = new AssetManager();
        Assets.instance.init(am);
        gameplayScreen = new GameplayScreen();
    }

    public Screen getStartScreen(){
        gameplayScreen.resetScreen(null); //here Player should be. LevelName will be incapsulated into Player.
        return gameplayScreen;
    }

    public void disposeScreens(){
        gameplayScreen.shutdown();
        Assets.instance.dispose();
    }
}*/
public class ScreensManager {

    private final MyTestGame game;
    private HashMap<STATE, Screen> gameScreens;

    public enum STATE {
        LOADING,
        SPLASH,
        MAIN_MENU,
        PLAY
    }

    public ScreensManager(final MyTestGame game) {
        this.game = game;

        createScreens();

        setScreen(STATE.LOADING);
    }

    private void createScreens() {
        this.gameScreens = new HashMap<STATE, Screen>();

        this.gameScreens.put(STATE.LOADING, new LoadingScreen(game));
        this.gameScreens.put(STATE.SPLASH, new SplashScreen(game));
        this.gameScreens.put(STATE.MAIN_MENU, new MainMenuScreen(game));
        this.gameScreens.put(STATE.PLAY, new GameplayScreen());
    }

    public void setScreen(STATE nextScreen) {

        if(nextScreen == STATE.LOADING) {
            game.setScreen(gameScreens.get(nextScreen));
            return;
        }
        if(nextScreen == STATE.SPLASH) {
            game.setScreen(gameScreens.get(nextScreen));
            return;
        }
        if(nextScreen == STATE.MAIN_MENU) {
            game.setScreen(gameScreens.get(nextScreen));
            return;
        }

        if(nextScreen == STATE.PLAY) {
            ((GameplayScreen)gameScreens.get(nextScreen)).resetScreen(null);
            game.setScreen(gameScreens.get(nextScreen));
        }else
            game.setScreen(gameScreens.get(nextScreen));
    }

    public void dispose() {
        for(Screen screen : gameScreens.values()) {
            if(screen != null) {
                screen.dispose();
            }
        }
    }
}