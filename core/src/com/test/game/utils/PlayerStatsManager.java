package com.test.game.utils;


import com.badlogic.gdx.Gdx;
import com.sun.org.apache.xpath.internal.operations.String;
import com.test.game.utils.Enums.AmmoType;
import com.test.game.utils.Enums.TankType;

public class PlayerStatsManager {
    private final byte NORMAL_BULLETS_ID = 0;
    private final byte PLASMA_BULLETS_ID = 1;
    private final byte DOUBLE_NORMAL_ID = 2;
    private final byte DOUBLE_PLASMA_ID = 3;
    private final byte AP_BULLETS_ID = 4;
    private final byte RAP_BULLETS_ID = 5;
    private final byte ENERGY_DRINKS_ID = 6;
    private final byte REPAIR_TOOLKIT_ID = 7;
    private final byte ITEMS_SIZE = 8;

    private final byte HEAVY_ID = 0;
    private final byte CONST_HP_ID = 1;
    private final byte REAL_HP_ID = 2;
    private final short STATS_SIZE = 3;

    public short[] items;
    public byte[] stats;

    private byte currentPlayerAmmo;
    private byte prevAmmo;
    private byte currentAllyAmmo;

    public PlayerStatsManager(){
        items = new short[ITEMS_SIZE];
        stats = new byte[STATS_SIZE];
        prevAmmo = RAP_BULLETS_ID;

        items[NORMAL_BULLETS_ID] = 3;
        items[PLASMA_BULLETS_ID] = 0;
        items[DOUBLE_NORMAL_ID] = 0;
        items[DOUBLE_PLASMA_ID] = 10;
        items[AP_BULLETS_ID] = 0;
        items[RAP_BULLETS_ID] = 2;
        items[ENERGY_DRINKS_ID] = 3;
        items[REPAIR_TOOLKIT_ID] = 3;

        stats[HEAVY_ID] = 0;
        stats[CONST_HP_ID] = 5;
        stats[REAL_HP_ID] = 5;

        updateMarkers();
    }

    public void updateMarkers(){
        currentAllyAmmo = RAP_BULLETS_ID;
        currentPlayerAmmo = prevAmmo;
    }

    public byte getConstHp(){return stats[CONST_HP_ID];}

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
            case RAP_BULLETS_ID:
                if(items[RAP_BULLETS_ID] > 0){
                    this.currentPlayerAmmo = RAP_BULLETS_ID;
                    this.prevAmmo = currentPlayerAmmo;
                    break;
                }
            case AP_BULLETS_ID:
                if(items[AP_BULLETS_ID] > 0){
                    this.currentPlayerAmmo = AP_BULLETS_ID;
                    this.prevAmmo = currentPlayerAmmo;
                    break;
                }
            case DOUBLE_PLASMA_ID:
                if(items[DOUBLE_PLASMA_ID] > 0) {
                    this.currentPlayerAmmo = DOUBLE_PLASMA_ID;
                    this.prevAmmo = currentPlayerAmmo;
                    break;
                }
            case DOUBLE_NORMAL_ID:
                if(items[DOUBLE_NORMAL_ID] > 0){
                    this.currentPlayerAmmo = DOUBLE_NORMAL_ID;
                    this.prevAmmo = currentPlayerAmmo;
                    break;
                }
            case PLASMA_BULLETS_ID:
                if(items[PLASMA_BULLETS_ID]> 0){
                    this.currentPlayerAmmo = PLASMA_BULLETS_ID;
                    this.prevAmmo = currentPlayerAmmo;
                    break;
                }
            case NORMAL_BULLETS_ID:
                this.currentPlayerAmmo = NORMAL_BULLETS_ID;
                this.prevAmmo = currentPlayerAmmo;
                break;
            default:
                currentPlayerAmmo = RAP_BULLETS_ID;
                setCurrentPlayerAmmo(currentPlayerAmmo);
        }
    }

    public void setRealHp(byte realHp){stats[REAL_HP_ID] = realHp;}


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
