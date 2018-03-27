package com.test.game.entities;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Pool;
import com.test.game.utils.Constants;
import com.test.game.utils.Enums.AmmoType;
import com.test.game.utils.Enums.Direction;
import com.test.game.utils.MaterialEntity;

public class Bullet extends MaterialEntity implements Pool.Poolable {

    public AmmoType type;

    public void init(Body body) {
        this.setAlive(true);
        this.setBody(body);
        this.setGridCoordinates((short)-1, (short)-1);
    }

    public boolean configureBulletType(short category, AmmoType type) {
        this.setCategory(category);
        switch (type){
            case NORMAL_BULLET:
                hp = Constants.NORMAL_BULLET_MAX_HP;
                break;
            case PLASMA_BULLET:
                hp = Constants.PLASMA_BULLET_MAX_HP;
                break;
            case AP_BULLET:
                hp = Constants.AP_BULLET_MAX_HP;
                break;
            case RAP_BULLET:
                hp = Constants.RAP_BULLET_MAX_HP;
                break;
            default:
                return false;
        }
        this.type = type;
        return true;
    }

    public void launch(Direction direction){
        switch (direction) {
            case UP:
                body.applyLinearImpulse(Constants.BULLET_UP_IMPULSE, body.getWorldCenter(), true);
                break;
            case DOWN:
                body.applyLinearImpulse(Constants.BULLET_DOWN_IMPULSE, body.getWorldCenter(), true);
                break;
            case LEFT:
                body.applyLinearImpulse(Constants.BULLET_LEFT_IMPULSE, body.getWorldCenter(), true);
                break;
            case RIGHT:
                body.applyLinearImpulse(Constants.BULLET_RIGHT_IMPULSE, body.getWorldCenter(), true);
                break;
        }
    }

    @Override
    public void reset() {
        this.setAlive(false);
        this.setBody(null);
        this.setCategory((short) 0);
        this.setHp(0);
    }
}
