package com.test.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.test.game.Level;
import com.test.game.LevelInputManager;
import com.test.game.LevelRenderer;
import com.test.game.utils.Assets;
import com.test.game.utils.Constants;
import com.test.game.utils.LevelLoader;

public class GameplayScreen implements Screen {

    private Level level;
    private LevelRenderer levelRenderer = new LevelRenderer();
    private boolean load = false;

    private SpriteBatch batch;

    public GameplayScreen() {
        AssetManager am = new AssetManager();
        Assets.instance.init(am);
        LevelInputManager.instance.enable();
        Box2D.init();
    }

    @Override
    public void show() {
        if(!load){
            batch = new SpriteBatch();
            setupLevel("123rofl321");
            levelRenderer.presetCameraPosition(level);
            load = true;
        }
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
    public void pause() {

    }

    @Override
    public void resume() {
        Gdx.gl.glClearColor(0,0,0,1);
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        level.dispose();
        levelRenderer.dispose();
        batch.dispose();
        Assets.instance.dispose();
    }

    private void setupLevel(String levelName) {
        if(!load) {
            if(Constants.DEBUG)
                level = Level.debugLevel();
            else
                level = LevelLoader.load(levelName);
        }
    }
}
