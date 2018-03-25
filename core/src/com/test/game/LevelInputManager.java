package com.test.game;

import com.badlogic.gdx.Gdx;
import com.test.game.utils.LevelInput;

public class LevelInputManager {
    public static LevelInput input = null;
    public static void setInput(LevelInput levelInput){
        input = levelInput;
        Gdx.input.setInputProcessor(input);
    }
}
