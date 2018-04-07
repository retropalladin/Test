package com.test.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.test.game.MyTestGame;
import com.test.game.utils.Assets;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.scaleTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * Created by Егор on 03.04.2018.
 */

public class SplashScreen implements Screen {

    private final MyTestGame game;

    private Stage stage;
    private OrthographicCamera camera;

    private Image splashImgOne;
    private Image splashImgTwo;
    private Image backgroundImage;

    public SplashScreen(final MyTestGame game) {
        this.game = game;
        this.camera = new OrthographicCamera();
        camera.setToOrtho(false, MyTestGame.GAME_DESKTOP_WIDTH,
                MyTestGame.GAME_DESKTOP_HEIGHT);
        this.stage = new Stage(new FitViewport(MyTestGame.GAME_DESKTOP_WIDTH,
                MyTestGame.GAME_DESKTOP_HEIGHT, camera));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        Runnable transitionRunnable = new Runnable() {
            @Override
            public void run() {
                game.screensManager.setScreen(ScreensManager.STATE.MAIN_MENU);
            }
        };

        splashImgOne = new Image(Assets.instance.assetManager.get("images/intro1.png", Texture.class));
        splashImgOne.setOrigin(splashImgOne.getWidth() / 2, splashImgOne.getHeight() / 2);
        splashImgOne.setPosition(stage.getWidth() / 2 - 50, stage.getHeight() - MyTestGame.GAME_DESKTOP_HEIGHT/2);
        splashImgOne.addAction(
                sequence(
                        alpha(0),
                        fadeIn(8f),
                        delay(1.5f),
                        fadeOut(1.25f)
                )
        );

        splashImgTwo = new Image(Assets.instance.assetManager.get("images/intro2.png", Texture.class));
        splashImgTwo.setOrigin(splashImgTwo.getWidth() / 2, splashImgTwo.getHeight() / 2);
        splashImgTwo.setPosition(stage.getWidth() / 2 - 50, stage.getHeight() - MyTestGame.GAME_DESKTOP_HEIGHT/2);
        splashImgTwo.addAction(
                sequence(
                        alpha(0),
                        delay(11f),
                        fadeIn(2.25f),
                        delay(1.5f),
                        fadeOut(1.25f)
                )
        );

        backgroundImage = new Image(Assets.instance.assetManager.get("images/menu_backgr.png", Texture.class));
        backgroundImage.setOrigin(backgroundImage.getWidth(), backgroundImage.getHeight());
        backgroundImage.setSize(stage.getWidth(),stage.getHeight());
        backgroundImage.setPosition(0, 0);
        backgroundImage.addAction(
                sequence(
                        alpha(0),
                        delay(16f),
                        fadeIn(1f),
                        delay(1.5f),
                        run(transitionRunnable)
                )
        );

        stage.addActor(splashImgOne);
        stage.addActor(splashImgTwo);
        stage.addActor(backgroundImage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        stage.draw();
    }

    public void update(float delta) {
        stage.act(delta);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, false);
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
        stage.dispose();
    }
}
