package com.test.game.utils;


import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.test.game.MyTestGame;
import com.test.game.screens.GameplayScreen;
import com.test.game.screens.MainMenuScreen;

public class ScreensManager {

    ////////////////////////////////////////////
    /// Заберем управление из game,
    /// в game инициализируем инстанс screensManager,
    /// который сам будет вызывать экраны

    private static ScreensManager screensManagerInstance;
    public static synchronized ScreensManager getInstance(MyTestGame game) {

        if (screensManagerInstance == null) {
            screensManagerInstance = new ScreensManager(game);
        }
        return screensManagerInstance;
    }

    private static MyTestGame game;
    private static GameplayScreen gameplayScreen;
    private static MainMenuScreen mainMenuScreen;
    private ScreensManager(MyTestGame game) {
        this.game = game;

        gameplayScreen = GameplayScreen.getInstance(game);
        mainMenuScreen = MainMenuScreen.getInstance(game);
    }

    public void setScreen(int id){
        switch (id){
            case Constants.Screens.MAINMENU_SCREEN:
                game.setScreen(mainMenuScreen);
                break;
            case Constants.Screens.GAMEPLAY_SCREEN:
                game.setScreen(gameplayScreen);
                break;
        }
    }


    ////////////////////////////////////////////

    //public static final ScreensManager instance = new ScreensManager();

    //private final GameplayScreen gameplayScreen;
    //private final MainMenuScreen mainMenuScreen;

    private ScreensManager() {
        AssetManager am = new AssetManager();
        Assets.instance.init(am);

        //gameplayScreen = GameplayScreen.instance;
        //mainMenuScreen = MainMenuScreen.instance;
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
