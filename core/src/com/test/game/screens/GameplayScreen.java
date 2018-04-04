package com.test.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.test.game.level.Level;
import com.test.game.inputs.GameplayInputManager;
import com.test.game.level.LevelRenderer;
import com.test.game.player.PlayerManager;
import com.test.game.utils.Constants;
import com.test.game.level.LevelLoader;
import com.test.game.utils.Enums;
import com.test.game.utils.Enums.GameplayScreenState;

public class GameplayScreen implements Screen {

    private GameplayScreenState gameplayScreenState;

    private Level level;
    private LevelRenderer levelRenderer;

    private SpriteBatch batch;

    public GameplayScreen() {
        Box2D.init();
        batch = new SpriteBatch();
        levelRenderer = new LevelRenderer();
    }

    @Override
    public void show() {
        GameplayInputManager.instance.enable();
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
        GameplayInputManager.instance.disable();
    }

    @Override
    public void dispose() {
        ScreensManager.instance.disposeScreens();
    }

    public void resetScreen(PlayerManager playerManager) {
        setupLevel(playerManager);
        levelRenderer.presetCameraPosition(level);
    }

    public void shutdown() {
        if(level != null)
            level.dispose();
        levelRenderer.dispose();
        batch.dispose();
    }

    private void setupLevel(PlayerManager playerManager) {
        if(Constants.Developing.DEBUG)
            level = Level.debugLevel();
        else
            level = LevelLoader.load(null);
    }
}
