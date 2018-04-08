package com.test.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.test.game.MyTestGame;
import com.test.game.utils.Assets;
import com.test.game.utils.Constants;

/**
 * Created by Егор on 07.04.2018.
 */

public class LoadingScreen implements Screen {

    private MyTestGame game;

    private OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;
    private float progress;

    public LoadingScreen (final MyTestGame game) {
        this.game = game;
        this.shapeRenderer = new ShapeRenderer();
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, MyTestGame.GAME_DESKTOP_WIDTH,
                MyTestGame.GAME_DESKTOP_HEIGHT);

    }

    void queueAssets() {
        game.assetManager.load(Constants.Textures.AtlasNames.TANKS_ATLAS, TextureAtlas.class);
        game.assetManager.load(Constants.Textures.AtlasNames.SKIN_ATLAS, TextureAtlas.class);

        game.assetManager.load("images/intro1.png", Texture.class);
        game.assetManager.load("images/intro2.png", Texture.class);
        game.assetManager.load("images/intro_logo.png", Texture.class);
        game.assetManager.load("images/menu_backgr.png", Texture.class);
        game.assetManager.load("images/menu_pict.png", Texture.class);

        Assets.instance.init(game.assetManager);

        game.assetManager.finishLoading();
    }

    @Override
    public void show() {
        shapeRenderer.setProjectionMatrix(camera.combined);
        progress = 0f;
        queueAssets();
    }

    private void update(float delta) {
        progress = MathUtils.lerp(progress, game.assetManager.getProgress(), .1f);
        if (game.assetManager.update() && progress >= game.assetManager.getProgress() - .001f) {
            game.screensManager.setScreen(ScreensManager.STATE.SPLASH);
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(32, camera.viewportHeight / 2 - 8, camera.viewportWidth - 64, 16 - progress * 16);

        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(32, camera.viewportHeight / 2 - 8, progress * (camera.viewportWidth - 64) / 2, 16);

        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(camera.viewportWidth - 32 - progress * (camera.viewportWidth - 64)/2, camera.viewportHeight / 2 - 8, progress * (camera.viewportWidth - 64) / 2, 16);

        shapeRenderer.end();
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
        shapeRenderer.dispose();
    }
}
