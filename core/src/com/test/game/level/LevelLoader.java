package com.test.game.level;

import com.test.game.player.PlayerManager;

public class LevelLoader {
    public static Level load(PlayerManager playerManager) {
        if(playerManager == null)
            return Level.debugLevel();
        return null;
    }
}
