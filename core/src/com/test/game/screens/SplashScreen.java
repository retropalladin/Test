package com.test.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.test.game.MyTestGame;
import com.test.game.utils.Assets;

import java.io.Console;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.scaleTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

/**
 * Created by Егор on 03.04.2018.
 */

public class SplashScreen implements Screen {

    private final MyTestGame game;

    private Stage stage;
    private Skin skin;
    private BitmapFont font24;
    private OrthographicCamera camera;

    private Image splashImgOne;
    private Image splashImgTwo;
    private Image backgroundImage;
    private Image image_logo;

    private float progress;
    private boolean delayFirst;
    private boolean delaySecond;
    private boolean delayEnd;

    private Button buttonStart;

    public SplashScreen(final MyTestGame game) {
        this.game = game;
        this.camera = new OrthographicCamera();
        camera.setToOrtho(false, MyTestGame.GAME_DESKTOP_WIDTH,
                MyTestGame.GAME_DESKTOP_HEIGHT);
        this.stage = new Stage(new FitViewport(MyTestGame.GAME_DESKTOP_WIDTH,
                MyTestGame.GAME_DESKTOP_HEIGHT, camera));
        initFonts();
    }

    public void initFonts() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Arcon.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 24;
        parameter.color = Color.RED;
        font24 = generator.generateFont(parameter);
        //generator.dispose();
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

        Group background = new Group();
        background.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Group foreground = new Group();
        foreground.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        stage.addActor(background);
        stage.addActor(foreground);

        splashImgOne = new Image(Assets.instance.assetManager.get("images/intro1.png", Texture.class));
        splashImgOne.setOrigin(splashImgOne.getWidth() / 2, splashImgOne.getHeight() / 2);
        splashImgOne.setPosition(stage.getWidth() / 2 - 50, stage.getHeight() - MyTestGame.GAME_DESKTOP_HEIGHT/2);
        splashImgOne.addAction(
                sequence(
                        alpha(0),
                        fadeIn(5.5f),
                        delay(1.25f),
                        fadeOut(1.25f)
                )
        );

        splashImgTwo = new Image(Assets.instance.assetManager.get("images/intro2.png", Texture.class));
        splashImgTwo.setOrigin(splashImgTwo.getWidth() / 2, splashImgTwo.getHeight() / 2);
        splashImgTwo.setPosition(stage.getWidth() / 2 - 50, stage.getHeight() - MyTestGame.GAME_DESKTOP_HEIGHT/2);
        splashImgTwo.addAction(
                sequence(
                        alpha(0),
                        delay(8f),
                        fadeIn(2.25f),
                        delay(1.5f),
                        fadeOut(1.25f)
                )
        );

        foreground.addActor(splashImgOne);
        foreground.addActor(splashImgTwo);

        skin = new Skin();
        skin.addRegions(Assets.instance.assetManager.get("skins/uiskin.atlas", TextureAtlas.class));
        skin.add("default-font", font24);
        skin.load(Gdx.files.internal("skins/uiskin.json"));

        image_logo = new Image(Assets.instance.assetManager.get("images/menu_pict.png", Texture.class));
        image_logo.setPosition(stage.getWidth() / 2 - image_logo.getWidth()/2, stage.getHeight() - image_logo.getHeight());
        image_logo.addAction(
                sequence(
                        alpha(0),
                        delay(13f),
                        fadeIn(1f)
                        /*moveTo(
                                stage.getWidth() / 2 - image_logo.getWidth()/2,
                                stage.getHeight() - image_logo.getHeight(),
                                1f,
                                Interpolation.linear
                        )*/
                )
        );

        backgroundImage = new Image(Assets.instance.assetManager.get("images/menu_backgr.png", Texture.class));
        backgroundImage.setOrigin(backgroundImage.getWidth(), backgroundImage.getHeight());
        backgroundImage.setSize(stage.getWidth(),stage.getHeight());
        backgroundImage.setPosition(0, -image_logo.getHeight());
        backgroundImage.addAction(
                sequence(
                        alpha(0),
                        delay(13f),
                        fadeIn(1f),
                        delay(1.5f),
                        run(transitionRunnable)
                )
        );

        buttonStart = new TextButton("Start Game",skin,"default");
        buttonStart.setSize(MyTestGame.GAME_DESKTOP_WIDTH/2,75);
        buttonStart.setPosition(stage.getWidth(), stage.getHeight()/2);
        buttonStart.addAction(
                sequence(
                        alpha(0),
                        delay(12f),
                        fadeIn(1f),
                        moveTo(
                                stage.getWidth() / 2 - MyTestGame.GAME_DESKTOP_WIDTH/4,
                                stage.getHeight()/2,
                                .5f,
                                Interpolation.linear
                        )
                )
        );

        background.addActor(backgroundImage);
        background.addActor(image_logo);
        background.addActor(buttonStart);

        progress = 0f;
        delayFirst = false;
        delaySecond = false;
        delayEnd = false;

        stage.addListener(new ClickListener() {
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                if(!delayEnd) {
                    delayEnd = true;
                }
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        stage.draw();
    }

    public void update(float delta) {
        progress += delta;

        /*if(!delayFirst && !delaySecond) {
            splashImgOne.addAction(
                    sequence(
                            alpha(0),
                            fadeIn(5.5f),
                            delay(1.25f),
                            fadeOut(1.25f)
                    )
            );
            splashImgOne.act(delta);
        }

        if((delayFirst && !delaySecond) || progress > 8f ) {
            splashImgOne.remove();
            splashImgTwo.addAction(
                    sequence(
                            alpha(0),
                            fadeIn(2.25f),
                            delay(1.5f),
                            fadeOut(1.25f)
                    )
            );
            splashImgTwo.act(delta);
        }
        if((delayFirst && delaySecond && delayEnd) || progress> 13f) {
            splashImgTwo.remove();
            image_logo.addAction(
                    sequence(
                            alpha(0),
                            fadeIn(1f)
                    )
            );

            Runnable transitionRunnable = new Runnable() {
                @Override
                public void run() {
                    game.screensManager.setScreen(ScreensManager.STATE.MAIN_MENU);
                }
            };

            backgroundImage.addAction(
                    sequence(
                            alpha(0),
                            fadeIn(1f),
                            delay(1.5f),
                            run(transitionRunnable)
                    )
            );

            buttonStart.addAction(
                    sequence(
                            alpha(0),
                            fadeIn(1f),
                            moveTo(
                                    stage.getWidth() / 2 - MyTestGame.GAME_DESKTOP_WIDTH/4,
                                    stage.getHeight() - MyTestGame.GAME_DESKTOP_WIDTH/2,
                                    1.4f,
                                    Interpolation.linear
                            )
                    )
            );

            delayEnd = false;
        }
*/
        /*if(delayEnd) {
            splashImgOne.clear();
            splashImgTwo.clear();

            image_logo.clearActions();
            backgroundImage.clearActions();
            buttonStart.clearActions();

            image_logo.addAction(
                    sequence(
                            alpha(0),
                            fadeIn(1f)
                    )
            );

            Runnable transitionRunnable = new Runnable() {
                @Override
                public void run() {
                    game.screensManager.setScreen(ScreensManager.STATE.MAIN_MENU);
                }
            };

            backgroundImage.addAction(
                    sequence(
                            alpha(0),
                            fadeIn(1f),
                            delay(1.5f),
                            run(transitionRunnable)
                    )
            );

            buttonStart.addAction(
                    sequence(
                            alpha(0),
                            fadeIn(1f),
                            moveTo(
                                    stage.getWidth() / 2 - MyTestGame.GAME_DESKTOP_WIDTH/4,
                                    stage.getHeight()/2,
                                    .5f,
                                    Interpolation.linear
                            )
                    )
            );

            image_logo.act(delta);
            backgroundImage.act(delta);
            buttonStart.act(delta);
        }*/
        stage.act(delta);
        //System.out.println("fir=" + delayFirst+";sec="+delaySecond);
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
