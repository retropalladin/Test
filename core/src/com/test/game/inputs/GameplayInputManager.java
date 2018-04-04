package com.test.game.inputs;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;

public class GameplayInputManager {

    public static final GameplayInputManager instance = new GameplayInputManager();
    public final GameplayInput gameplayInput;


    private GameplayInputManager(){
        if (Gdx.app.getType() == Application.ApplicationType.Android || Gdx.app.getType() == Application.ApplicationType.iOS){
            gameplayInput = new GameplayInputMobile();
        }else{
            gameplayInput = new GameplayInputPc();
        }
    }

    public void enable(){
        Gdx.input.setInputProcessor(gameplayInput);
    }
    public void disable() { Gdx.input.setInputProcessor(null);}
}
