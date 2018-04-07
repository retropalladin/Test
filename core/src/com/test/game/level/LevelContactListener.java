package com.test.game.level;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.test.game.level.Level;
import com.test.game.entities.Bullet;
import com.test.game.entities.NpcTank;
import com.test.game.entities.Wall;
import com.test.game.utils.Constants;
import com.test.game.utils.MaterialEntity;

public class LevelContactListener implements ContactListener {

    private Level level;
    private MaterialEntity materialEntityA;
    private MaterialEntity materialEntityB;
    private short categoryA;
    private short categoryB;

    public LevelContactListener(Level level){
        this.level = level;
    }

    @Override
    public void beginContact(Contact contact) {}
    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        contact.setEnabled(false);
        materialEntityA = (MaterialEntity)contact.getFixtureA().getBody().getUserData();
        materialEntityB = (MaterialEntity)contact.getFixtureB().getBody().getUserData();
        categoryA = materialEntityA.getCategory();
        categoryB = materialEntityB.getCategory();
        if(materialEntityA.isAlive() & materialEntityB.isAlive()){
            if(categoryA == Constants.Physics.CATEGORY_ALLY_BULLET && categoryB == Constants.Physics.CATEGORY_ENEMY_TANK ||
                    categoryA == Constants.Physics.CATEGORY_ENEMY_BULLET && categoryB == Constants.Physics.CATEGORY_ALLY_TANK){
                level.bulletAndTankCollisionProcedure((Bullet)materialEntityA,(NpcTank)materialEntityB);
                return;
            }
            if(categoryB == Constants.Physics.CATEGORY_ALLY_BULLET && categoryA == Constants.Physics.CATEGORY_ENEMY_TANK ||
                    categoryB == Constants.Physics.CATEGORY_ENEMY_BULLET && categoryA == Constants.Physics.CATEGORY_ALLY_TANK) {
                level.bulletAndTankCollisionProcedure((Bullet)materialEntityB,(NpcTank)materialEntityA);
                return;
            }
            if(categoryA == Constants.Physics.CATEGORY_ALLY_BULLET && categoryB == Constants.Physics.CATEGORY_ENEMY_BULLET ||
                    categoryA == Constants.Physics.CATEGORY_ENEMY_BULLET && categoryB == Constants.Physics.CATEGORY_ALLY_BULLET){
                level.bulletAndBulletCollisionProcedure((Bullet)materialEntityA,(Bullet)materialEntityB);
                return;
            }
            if((categoryA == Constants.Physics.CATEGORY_ALLY_BULLET || categoryA == Constants.Physics.CATEGORY_ENEMY_BULLET) && categoryB == Constants.Physics.CATEGORY_WALL){
                level.bulletAndWallCollisionProcedure((Bullet)materialEntityA,(Wall)materialEntityB);
                return;
            }
            if((categoryB == Constants.Physics.CATEGORY_ALLY_BULLET || categoryB == Constants.Physics.CATEGORY_ENEMY_BULLET) && categoryA == Constants.Physics.CATEGORY_WALL){
                level.bulletAndWallCollisionProcedure((Bullet)materialEntityB,(Wall)materialEntityA);
                return;
            }
        }
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {}
    @Override
    public void endContact(Contact contact) {}

}

