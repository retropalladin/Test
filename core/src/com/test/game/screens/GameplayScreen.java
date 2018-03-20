package com.test.game.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.test.game.Level;
import com.test.game.utils.Constants;
import com.test.game.utils.LevelLoader;

public class GameplayScreen implements Screen {

    private Level level;
    private boolean load = false;

    private SpriteBatch batch;

    @Override
    public void show() {
        batch = new SpriteBatch();
        setupLevel("123rofl321");
    }

    @Override
    public void render(float delta) {
        Level.update(delta);
        Level.render();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

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
