package com.test.game.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.*;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.test.game.MyTestGame;
import com.test.game.utils.Assets;
import com.test.game.utils.Constants;

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
 * Created by Егор on 22.03.2018.
 */

public class MainMenuScreen implements Screen {

    private final MyTestGame game;

    private Stage stage;
    private Skin skin;

    private OrthographicCamera camera;
    private BitmapFont font24;

    private Image backgroundImage;
    private Image image_logo;

    public MainMenuScreen(final MyTestGame game) {

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

        Group background = new Group();
        background.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Group foreground = new Group();
        foreground.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        stage.addActor(background);
        stage.addActor(foreground);

        image_logo = new Image(Assets.instance.assetManager.get("images/menu_pict.png", Texture.class));
        //image_logo.setSize(200,100);
        image_logo.setPosition(stage.getWidth() / 2 - image_logo.getWidth()/2, stage.getHeight() - image_logo.getHeight());

        backgroundImage = new Image(Assets.instance.assetManager.get("images/menu_backgr.png", Texture.class));
        backgroundImage.setSize(stage.getWidth(),stage.getHeight());
        backgroundImage.setPosition(0, -image_logo.getHeight());

        background.addActor(backgroundImage);
        background.addActor(image_logo);

        //skin = new Skin(Assets.instance.assetManager.get(Constants.Textures.AtlasNames.SKIN_ATLAS, TextureAtlas.class));
        skin = new Skin();
        skin.addRegions(Assets.instance.assetManager.get("skins/uiskin.atlas", TextureAtlas.class));
        skin.add("default-font", font24);
        skin.load(Gdx.files.internal("skins/uiskin.json"));

        // StartGame Button
        Button button1 = new TextButton("Start Game",skin,"default");
        button1.setSize(MyTestGame.GAME_DESKTOP_WIDTH/2,75);
        button1.setPosition(stage.getWidth() / 2 - MyTestGame.GAME_DESKTOP_WIDTH/4,
                stage.getHeight()/2);
        button1.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                game.screensManager.setScreen(ScreensManager.STATE.PLAY);
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        //stage.addActor(button1);

        // Nothing Button
        /*Button button2 = new TextButton("Nothing",skin,"default");
        button2.setSize(400,100);
        button2.setPosition(Gdx.graphics.getWidth()/2-200,Gdx.graphics.getHeight()/2 - 100);
        button2.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {

            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }
        });*/
        //stage.addActor(button2);

        // Nothing Button
        /*Button button3 = new TextButton("Nothing",skin,"default");
        button3.setSize(400,100);
        button3.setPosition(Gdx.graphics.getWidth()/2-200,Gdx.graphics.getHeight()/2 - 210);
        button3.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {

            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }
        });*/

        /*Table scrollTable = new Table();
        //scrollTable.add(image_logo);
        //scrollTable.row();
        scrollTable.add(button1);
        scrollTable.row();
        *//*scrollTable.add(button2);
        scrollTable.row();
        scrollTable.add(button3);
*//*
        ScrollPane scroller = new ScrollPane(scrollTable);

        Table table = new Table();
        table.setFillParent(true);
        table.add(scroller).fill().expand();*/

        foreground.addActor(button1);
    }

    @Override
    public void render (float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();
    }
    @Override
    public void resize (int width, int height) {
        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
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
    public void dispose () {
        stage.dispose();
    }

}
