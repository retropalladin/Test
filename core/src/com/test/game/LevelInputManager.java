package com.test.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.test.game.inputs.LevelInputMobile;
import com.test.game.inputs.LevelInputPc;
import com.test.game.utils.LevelInput;

public class LevelInputManager {

    public static final LevelInputManager instance = new LevelInputManager();
    public final LevelInput levelInput;


    private LevelInputManager(){
        if (Gdx.app.getType() == Application.ApplicationType.Android || Gdx.app.getType() == Application.ApplicationType.iOS){
            levelInput = new LevelInputMobile();
        }else{
            levelInput = new LevelInputPc();
        }
    }

    public void enable(){
        Gdx.input.setInputProcessor(levelInput);
    }
}
