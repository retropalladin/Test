package com.test.game.player;

import com.test.game.utils.Enums.AmmoType;
import com.test.game.utils.Enums.TankType;

public class PlayerManager {
    private String levelName;

    public static final byte NORMAL_BULLETS_ID = 0;
    public static final byte PLASMA_BULLETS_ID = 1;
    public static final byte DOUBLE_NORMAL_ID = 2;
    public static final byte DOUBLE_PLASMA_ID = 3;
    public static final byte AP_BULLETS_ID = 4;
    public static final byte RAP_BULLETS_ID = 5;
    public static final byte ENERGY_DRINKS_ID = 6;
    public static final byte REPAIR_TOOLKIT_ID = 7;
    public static final byte TIME_STOP_ID = 8;
    public static final byte ITEMS_SIZE = 9;

    public static final byte HEAVY_ID = 0;
    public static final byte LIVES_ID = 1;
    public static final byte CONST_HP_ID = 2;
    public static final byte REAL_HP_ID = 3;
    public static final byte STATS_SIZE = 4;

    public short[] items;
    public byte[] stats;

    private byte currentPlayerAmmo;
    private byte prevAmmo;
    private byte currentAllyAmmo;

    private PlayerManager(String levelName){
        if(levelName == null)
            throw new NullPointerException("Null levelName in PlayerManager ctor");
        this.levelName = levelName;
        items = new short[ITEMS_SIZE];
        stats = new byte[STATS_SIZE];
        prevAmmo = RAP_BULLETS_ID;
    }

    public static PlayerManager debugPlayerManager(){
        PlayerManager playerManager = new PlayerManager("Debug Level");
        playerManager.items[NORMAL_BULLETS_ID] = 99;
        playerManager.items[PLASMA_BULLETS_ID] = 0;
        playerManager.items[DOUBLE_NORMAL_ID] = 0;
        playerManager.items[DOUBLE_PLASMA_ID] = 0;
        playerManager.items[AP_BULLETS_ID] = 0;
        playerManager.items[RAP_BULLETS_ID] = 0;
        playerManager.items[ENERGY_DRINKS_ID] = 2;
        playerManager.items[REPAIR_TOOLKIT_ID] = 2; //is not ready yet
        playerManager.items[TIME_STOP_ID] = 2;

        playerManager.stats[HEAVY_ID] = 0;
        playerManager.stats[LIVES_ID] = 1;
        playerManager.stats[CONST_HP_ID] = 5;
        playerManager.stats[REAL_HP_ID] = 5;

        playerManager.updateMarkers();
        return playerManager;
    }

    public void updateMarkers(){
        currentAllyAmmo = RAP_BULLETS_ID;
        setCurrentPlayerAmmo(prevAmmo);
    }


    public byte getConstHp(){
        return stats[CONST_HP_ID];
    }

    public byte getLives(){
        return stats[LIVES_ID];
    }

    public String getLevelName(){
        return levelName;
    }

    public TankType getTankType(){
        if(stats[HEAVY_ID] == 0)
            return TankType.LIGHT_TANK;
        else
            return TankType.HEAVY_TANK;
    }

    public AmmoType getPrevLevelAmmo() {
        switch (prevAmmo) {
            case RAP_BULLETS_ID:
                if(items[RAP_BULLETS_ID] > 0) {
                    prevAmmo = RAP_BULLETS_ID;
                    return AmmoType.RAP_BULLET;
                }
            case AP_BULLETS_ID:
                if(items[AP_BULLETS_ID] > 0) {
                    prevAmmo = AP_BULLETS_ID;
                    return AmmoType.AP_BULLET;
                }
            case DOUBLE_PLASMA_ID:
                if(items[DOUBLE_PLASMA_ID] > 0){
                    prevAmmo = DOUBLE_NORMAL_ID;
                    return AmmoType.DOUBLE_PLASMA_BULLET;
                }
            case DOUBLE_NORMAL_ID:
                if(items[DOUBLE_NORMAL_ID] > 0) {
                    prevAmmo = DOUBLE_NORMAL_ID;
                    return AmmoType.DOUBLE_NORMAL_BULLET;
                }
            case PLASMA_BULLETS_ID:
                if(items[PLASMA_BULLETS_ID]> 0) {
                    prevAmmo =PLASMA_BULLETS_ID;
                    return AmmoType.PLASMA_BULLET;
                }
            case NORMAL_BULLETS_ID:
                prevAmmo = NORMAL_BULLETS_ID;
                return AmmoType.NORMAL_BULLET;
            default:
                prevAmmo = RAP_BULLETS_ID;
                return getPrevLevelAmmo();
        }
    }


    public void setCurrentPlayerAmmo(byte currentPlayerAmmo){
        switch (currentPlayerAmmo) {
            case ENERGY_DRINKS_ID:
                if(items[ENERGY_DRINKS_ID] > 0){
                    this.prevAmmo = this.currentPlayerAmmo;
                    this.currentPlayerAmmo = ENERGY_DRINKS_ID;
                }
                break;
            case TIME_STOP_ID:
                if(items[TIME_STOP_ID] > 0){
                    this.prevAmmo = this.currentPlayerAmmo;
                    this.currentPlayerAmmo = TIME_STOP_ID;
                }
                break;
            case RAP_BULLETS_ID:
                if(items[RAP_BULLETS_ID] > 0){
                    this.currentPlayerAmmo = RAP_BULLETS_ID;
                    this.prevAmmo = RAP_BULLETS_ID;
                    break;
                }
            case AP_BULLETS_ID:
                if(items[AP_BULLETS_ID] > 0){
                    this.currentPlayerAmmo = AP_BULLETS_ID;
                    this.prevAmmo = AP_BULLETS_ID;
                    break;
                }
            case DOUBLE_PLASMA_ID:
                if(items[DOUBLE_PLASMA_ID] > 0) {
                    this.currentPlayerAmmo = DOUBLE_PLASMA_ID;
                    this.prevAmmo = DOUBLE_PLASMA_ID;
                    break;
                }
            case DOUBLE_NORMAL_ID:
                if(items[DOUBLE_NORMAL_ID] > 0){
                    this.currentPlayerAmmo = DOUBLE_NORMAL_ID;
                    this.prevAmmo = DOUBLE_NORMAL_ID;
                    break;
                }
            case PLASMA_BULLETS_ID:
                if(items[PLASMA_BULLETS_ID]> 0){
                    this.currentPlayerAmmo = PLASMA_BULLETS_ID;
                    this.prevAmmo = PLASMA_BULLETS_ID;
                    break;
                }
            case NORMAL_BULLETS_ID:
                this.currentPlayerAmmo = NORMAL_BULLETS_ID;
                this.prevAmmo = NORMAL_BULLETS_ID;
                break;
            default:
                currentPlayerAmmo = RAP_BULLETS_ID;
                setCurrentPlayerAmmo(currentPlayerAmmo);
        }
    }

    public void setRealHp(byte realHp){
        stats[REAL_HP_ID] = realHp;
    }


    public AmmoType shootCurrentAllyAmmo(){
        switch(currentAllyAmmo){
            case RAP_BULLETS_ID:
                if(items[RAP_BULLETS_ID] > 0){
                    items[RAP_BULLETS_ID]--;
                    currentAllyAmmo = RAP_BULLETS_ID;
                    return AmmoType.RAP_BULLET;
                }
            case AP_BULLETS_ID:
                if(items[AP_BULLETS_ID] > 0){
                    items[AP_BULLETS_ID]--;
                    currentAllyAmmo = AP_BULLETS_ID;
                    return AmmoType.AP_BULLET;
                }
            case DOUBLE_PLASMA_ID:
                if(items[DOUBLE_PLASMA_ID] > 0) {
                    items[DOUBLE_PLASMA_ID]--;
                    currentAllyAmmo = DOUBLE_PLASMA_ID;
                    return AmmoType.DOUBLE_PLASMA_BULLET;
                }
            case DOUBLE_NORMAL_ID:
                if(items[DOUBLE_NORMAL_ID] > 0){
                    items[DOUBLE_NORMAL_ID]--;
                    currentAllyAmmo = DOUBLE_NORMAL_ID;
                    return AmmoType.DOUBLE_NORMAL_BULLET;
                }
            case PLASMA_BULLETS_ID:
                if(items[PLASMA_BULLETS_ID]> 0){
                    items[PLASMA_BULLETS_ID]--;
                    currentAllyAmmo = PLASMA_BULLETS_ID;
                    return AmmoType.PLASMA_BULLET;
                }
            default:
                currentAllyAmmo = NORMAL_BULLETS_ID;
                return AmmoType.NORMAL_BULLET;
        }
    }

    public AmmoType shootCurrentPlayerAmmo(){
        setCurrentPlayerAmmo(currentPlayerAmmo);
        switch(currentPlayerAmmo){
            case ENERGY_DRINKS_ID:
                items[ENERGY_DRINKS_ID]--;
                currentPlayerAmmo = prevAmmo;
                return AmmoType.ENERGY_DRINK;
            case TIME_STOP_ID:
                items[TIME_STOP_ID]--;
                currentPlayerAmmo = prevAmmo;
                return AmmoType.TIME_STOP;
            case RAP_BULLETS_ID:
                items[RAP_BULLETS_ID]--;
                return AmmoType.RAP_BULLET;
            case AP_BULLETS_ID:
                items[AP_BULLETS_ID]--;
                return AmmoType.AP_BULLET;
            case DOUBLE_PLASMA_ID:
                items[DOUBLE_PLASMA_ID]--;
                return AmmoType.DOUBLE_PLASMA_BULLET;
            case DOUBLE_NORMAL_ID:
                items[DOUBLE_NORMAL_ID]--;
                return AmmoType.DOUBLE_NORMAL_BULLET;
            case PLASMA_BULLETS_ID:
                items[PLASMA_BULLETS_ID]--;
                return AmmoType.PLASMA_BULLET;
            case NORMAL_BULLETS_ID:
                if(items[NORMAL_BULLETS_ID] > 0) {
                    items[NORMAL_BULLETS_ID]--;
                    return AmmoType.NORMAL_BULLET;
                }else{
                    return null;
                }
            default:
                return null;
        }
    }
}
