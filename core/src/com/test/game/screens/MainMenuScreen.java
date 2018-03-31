package com.test.game.screens;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.test.game.MyTestGame;
import com.test.game.utils.Constants;
import com.test.game.utils.ScreensManager;

/**
 * Created by Егор on 22.03.2018.
 */

public class MainMenuScreen implements Screen {

    private static MyTestGame game;
    private static MainMenuScreen mainMenuScreenInstance;
    public static synchronized MainMenuScreen getInstance(MyTestGame game) {

        if (mainMenuScreenInstance == null) {
            mainMenuScreenInstance = new MainMenuScreen(game);
        }
        return mainMenuScreenInstance;
    }
    private MainMenuScreen() {
    }
    private MainMenuScreen(MyTestGame game) {
        this.game = game;
    }

    private Stage stage;

    @Override
    public void show () {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Skin mySkin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));

        // Image
        Image image_logo = new Image(new Texture(Gdx.files.internal("intro_pict.png")));
        image_logo.setSize(200,100);
        image_logo.setPosition(Gdx.graphics.getWidth()/2-100,Gdx.graphics.getHeight()-100);
        //stage.addActor(image_logo);

        // StartGame Button
        Button button1 = new TextButton("Start Game",mySkin,"default");
        button1.setSize(400,100);
        button1.setPosition(Gdx.graphics.getWidth()/2-200,Gdx.graphics.getHeight()/2);
        button1.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                ScreensManager.getInstance(game).setScreen(Constants.Screens.GAMEPLAY_SCREEN);
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        //stage.addActor(button1);

        // Nothing Button
        Button button2 = new TextButton("Nothing",mySkin,"default");
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
        });
        //stage.addActor(button2);

        // Nothing Button
        Button button3 = new TextButton("Nothing",mySkin,"default");
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
        });

        Table scrollTable = new Table();
        scrollTable.add(image_logo);
        scrollTable.row();
        scrollTable.add(button1);
        scrollTable.row();
        scrollTable.add(button2);
        scrollTable.row();
        scrollTable.add(button3);

        ScrollPane scroller = new ScrollPane(scrollTable);

        Table table = new Table();
        table.setFillParent(true);
        table.add(scroller).fill().expand();

        this.stage.addActor(table);
    }
    @Override
    public void render (float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.act();
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

    public boolean needsGL20 () {
        return false;
    }
}
