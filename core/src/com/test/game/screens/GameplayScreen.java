package com.test.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.test.game.Level;
import com.test.game.MyTestGame;
import com.test.game.utils.LevelInputManager;
import com.test.game.LevelRenderer;
import com.test.game.utils.Assets;
import com.test.game.utils.Constants;
import com.test.game.utils.LevelLoader;
import com.test.game.utils.ScreensManager;

public class GameplayScreen implements Screen {

    //public static final GameplayScreen instance = new GameplayScreen();

    private static MyTestGame game;
    private static GameplayScreen gameplayScreenInstance;
    public static synchronized GameplayScreen getInstance(MyTestGame game) {

        if (gameplayScreenInstance == null) {
            gameplayScreenInstance = new GameplayScreen(game);
        }
        return gameplayScreenInstance;
    }
    private GameplayScreen() {
    }
    private GameplayScreen(MyTestGame game) {
        this.game = game;

        Box2D.init();
        batch = new SpriteBatch();
        levelRenderer = new LevelRenderer();
    }

    private Level level;
    private LevelRenderer levelRenderer;

    private SpriteBatch batch;

    /*private GameplayScreen() {
        Box2D.init();
        batch = new SpriteBatch();
        levelRenderer = new LevelRenderer();
    }*/

    @Override
    public void show() {
        LevelInputManager.instance.enable();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        levelRenderer.render(delta, level, batch);
        level.update(delta);
    }

    @Override
    public void resize(int width, int height) {
        levelRenderer.updateCameraResolution(width, height);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() { Gdx.gl.glClearColor(0,0,0,1);}

    @Override
    public void hide() {
        LevelInputManager.instance.disable();
    }

    @Override
    public void dispose() {
        ScreensManager.getInstance(game).disposeScreens();
    }

    public void resetScreen(String levelName) {
        setupLevel(levelName);
        levelRenderer.presetCameraPosition(level);
    }

    public void shutdown() {
        if(level != null)
            level.dispose();
        levelRenderer.dispose();
        batch.dispose();
    }

    private void setupLevel(String levelName) {
        if(Constants.Developing.DEBUG)
            level = Level.debugLevel();
        else
            level = LevelLoader.load(levelName);
    }
}
