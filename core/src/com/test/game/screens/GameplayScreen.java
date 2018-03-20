package com.test.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.test.game.Level;
import com.test.game.utils.Constants;
import com.test.game.utils.LevelLoader;

public class GameplayScreen implements Screen {

    private Level level;
    private boolean load = false;

    private SpriteBatch batch;

    @Override
    public void show() {
        Box2D.init();
        batch = new SpriteBatch();
        setupLevel("123rofl321");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        level.update(delta);
        level.render();
    }

    @Override
    public void resize(int width, int height) {
        level.viewport.update(width, height, false);
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
        batch.dispose();
    }

    private void setupLevel(String levelName) {
        if(!load) {
            if(Constants.DEBUG)
                level = Level.debugLevel(batch);
            else
                level = LevelLoader.load(levelName, batch);
        }
    }
}
