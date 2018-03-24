package com.test.game.utils;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public abstract class MaterialEntity {
    protected boolean alive;
    protected short category;
    protected int hp;
    protected Body body;

    public boolean isAlive(){
        return alive;
    }
    public void  setAlive(boolean alive){
        this.alive = alive;
    }
    public void setBody(Body body){
        this.body = body;
    }
    public void setCategory(short category){
        this.category = category;
    }
    public void setHp(int hp){
        if(hp < 0)
            hp = 0;
        this.hp = hp;
    }

    public Body getBody(){
        return body;
    }
    public short getCategory(){
        return category;
    }
    public int getHp(){
        return hp;
    }
    public void createFixture(FixtureDef fixtureDef) {
        body.createFixture(fixtureDef);
    }
}
