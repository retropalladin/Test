package com.test.game.level;

import com.test.game.level.Level;

public class LevelLoader {
    public static Level load(String levelName) {
        if(levelName == null)
            return Level.debugLevel();
        return null;
    }
}
