package com.test.game.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Pool;
import com.sun.org.apache.regexp.internal.RE;
import com.test.game.utils.Constants;
import com.test.game.utils.Enums.AmmoType;
import com.test.game.utils.Enums.Direction;
import com.test.game.utils.MaterialEntity;

public class Bullet extends MaterialEntity implements Pool.Poolable {

    ///////////////////////////////////////////
    /// Constants Settings                  ///
    ///////////////////////////////////////////

    public static final byte NORMAL_BULLET_MAX_HP = 1;
    public static final byte PLASMA_BULLET_MAX_HP = 1;
    public static final byte AP_BULLET_MAX_HP = 2;
    public static final byte RAP_BULLET_MAX_HP = 2;

    public static final byte NORMAL_BULLET_DAMAGE = 1;
    public static final byte PLASMA_BULLET_DAMAGE = 1;
    public static final byte AP_BULLET_DAMAGE = 3;
    public static final byte RAP_BULLET_DAMAGE = 5;

    public static final float BULLET_WIDTH = 0.7f;
    public static final float BULLET_HEIGHT = 0.35f;
    public static final float BULLET_WIDTH_H = BULLET_WIDTH * 0.5f;
    public static final float BULLET_HEIGHT_H = BULLET_HEIGHT * 0.5f;

    public static final float BULLET_EPS_SPAWN = 0.1f;
    public static final float DOUBLE_BULLET_EPS_SPAWN = 0.2f;
    public static final float DOUBLE_BULLET_EPS_SPAWN_H = DOUBLE_BULLET_EPS_SPAWN * 0.5f;

    public static final float DOUBLE_NORMAL_BULLET_RELOAD = 1.0f;
    public static final float DOUBLE_PLASMA_BULLET_RELOAD = 0.75f;
    public static final float NORMAL_BULLET_RELOAD = 1.0f;
    public static final float PLASMA_BULLET_RELOAD = 0.75f;
    public static final float AP_BULLET_RELOAD = 1.0f;
    public static final float RAP_BULLET_RELOAD = 1.0f;
    
    public static final float RELOAD_SPEED_UP = 0.25f;
    public static final float DOUBLE_NORMAL_BULLET_RELOAD_SU = DOUBLE_NORMAL_BULLET_RELOAD - RELOAD_SPEED_UP;
    public static final float DOUBLE_PLASMA_BULLET_RELOAD_SU = DOUBLE_PLASMA_BULLET_RELOAD - RELOAD_SPEED_UP;
    public static final float NORMAL_BULLET_RELOAD_SU = NORMAL_BULLET_RELOAD - RELOAD_SPEED_UP;
    public static final float PLASMA_BULLET_RELOAD_SU = PLASMA_BULLET_RELOAD - RELOAD_SPEED_UP;
    public static final float AP_BULLET_RELOAD_SU = AP_BULLET_RELOAD - RELOAD_SPEED_UP ;
    public static final float RAP_BULLET_RELOAD_SU = RAP_BULLET_RELOAD - RELOAD_SPEED_UP;

    ///////////////////////////////////////////
    /// Constants Physics                   ///
    ///////////////////////////////////////////

    public static final float NORMAL_BULLET_DENSITY = 0.7f;
    public static final float PLASMA_BULLET_DENSITY = 0.5f;
    public static final float AP_BULLET_DENSITY = 0.3f; // RAP_BULLET_DENSITY is equal
    public static final float BULLET_FRICTION = 0f;
    public static final float BULLET_RESTITUTION = 0f;

    public static final float BULLET_IMPULSE = 2.0f;
    public static final Vector2 BULLET_UP_IMPULSE = new Vector2(0, BULLET_IMPULSE);
    public static final Vector2 BULLET_DOWN_IMPULSE = new Vector2(0, -BULLET_IMPULSE);
    public static final Vector2 BULLET_RIGHT_IMPULSE = new Vector2(BULLET_IMPULSE, 0);
    public static final Vector2 BULLET_LEFT_IMPULSE = new Vector2(-BULLET_IMPULSE, 0);

    ///////////////////////////////////////////
    /// END CONSTANTS                       ///
    ///////////////////////////////////////////

    public AmmoType type;
    public Direction direction;

    public void init(Body body) {
        this.setAlive(true);
        this.setBody(body);
        this.setGridCoordinates((short)-1, (short)-1);
    }

    public boolean configureBulletType(short category, AmmoType type) {
        this.setCategory(category);
        switch (type){
            case NORMAL_BULLET:
                hp = NORMAL_BULLET_MAX_HP;
                break;
            case PLASMA_BULLET:
                hp = PLASMA_BULLET_MAX_HP;
                break;
            case AP_BULLET:
                hp = AP_BULLET_MAX_HP;
                break;
            case RAP_BULLET:
                hp = RAP_BULLET_MAX_HP;
                break;
            default:
                throw new IllegalArgumentException("No bullet AmmoType in configureBulletType");
        }
        this.type = type;
        return true;
    }

    public void launch(Direction direction){
        this.direction = direction;
        switch (direction) {
            case UP:
                body.applyLinearImpulse(BULLET_UP_IMPULSE, body.getWorldCenter(), true);
                break;
            case DOWN:
                body.applyLinearImpulse(BULLET_DOWN_IMPULSE, body.getWorldCenter(), true);
                break;
            case LEFT:
                body.applyLinearImpulse(BULLET_LEFT_IMPULSE, body.getWorldCenter(), true);
                break;
            case RIGHT:
                body.applyLinearImpulse(BULLET_RIGHT_IMPULSE, body.getWorldCenter(), true);
                break;
        }
    }

    public boolean takeDamage(byte damage){
        return decreaseHp(damage);
    }

    public void freeze(){
        if(category == Constants.Physics.CATEGORY_ENEMY_BULLET)
            body.setLinearVelocity(Vector2.Zero);
    }

    public void unfreeze(){
        if(category == Constants.Physics.CATEGORY_ENEMY_BULLET)
            launch(direction);
    }

    @Override
    public void reset() {
        this.setAlive(false);
        this.setBody(null);
        this.setCategory((short) 0);
        this.setHp((byte) 0);
    }
}
