package com.test.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import com.test.game.entities.Bullet;
import com.test.game.entities.Tank;
import com.test.game.entities.Wall;
import com.test.game.utils.Constants;
import com.test.game.utils.Enums.BulletType;
import com.test.game.utils.Enums.Direction;
import com.test.game.utils.Enums.TankType;


public class Level {

    public Array<Bullet> aliveBullets;
    private static Pool<Bullet> bulletPool = Pools.get(Bullet.class);

    public Array<Tank> aliveTanks;
    private static Pool<Tank> tankPool = Pools.get(Tank.class);

    public Array<Wall> aliveWalls;
    private static Pool<Wall> wallPool = Pools.get(Wall.class);

    public World world;

    private float frameTime;
    private float accumulator;

    private Vector2 tankCenter;
    private BodyDef tankBodyDef;
    private PolygonShape tankRectangle;
    private FixtureDef TankFixtureDef;

    private Vector2 horizontalBulletCenter;
    private Vector2 verticalBulletCenter;
    private BodyDef bulletBodyDef;
    private PolygonShape horizontalBulletRectangle;
    private PolygonShape verticalBulletRectangle;
    private FixtureDef BulletFixtureDef;

    public Level() {
        aliveBullets = new Array<Bullet>();
        aliveTanks = new Array<Tank>();
        aliveWalls = new Array<Wall>();

        world = new World(Vector2.Zero, false);

        tankCenter = new Vector2(Constants.CELL_SIZE * 0.5f, Constants.CELL_SIZE * 0.5f);

        tankBodyDef = new BodyDef();
        tankBodyDef.type = BodyType.DynamicBody;
        tankBodyDef.fixedRotation = true;

        tankRectangle = new PolygonShape();
        tankRectangle.setAsBox(Constants.CELL_SIZE * 0.5f, Constants.CELL_SIZE * 0.5f, tankCenter, 0);

        TankFixtureDef = new FixtureDef();
        TankFixtureDef.shape = tankRectangle;
        TankFixtureDef.friction = Constants.TANK_FRICTION;
        TankFixtureDef.restitution = Constants.TANK_RESTITUTION;

        horizontalBulletCenter = new Vector2(Constants.BULLET_WIDTH_H, Constants.BULLET_HEIGHT_H);
        verticalBulletCenter = new Vector2(Constants.BULLET_HEIGHT_H, Constants.BULLET_WIDTH_H);

        bulletBodyDef = new BodyDef();
        bulletBodyDef.type = BodyType.DynamicBody;
        bulletBodyDef.fixedRotation = true;

        horizontalBulletRectangle = new PolygonShape();
        verticalBulletRectangle = new PolygonShape();

        horizontalBulletRectangle.setAsBox(Constants.BULLET_WIDTH_H, Constants.BULLET_HEIGHT_H, horizontalBulletCenter , 0);
        verticalBulletRectangle.setAsBox(Constants.BULLET_HEIGHT_H, Constants.BULLET_WIDTH_H, verticalBulletCenter, 0);

        BulletFixtureDef = new FixtureDef();
        BulletFixtureDef.shape = horizontalBulletRectangle;
        BulletFixtureDef.friction = Constants.BULLET_FRICTION;
        BulletFixtureDef.restitution = Constants.BULLET_RESTITUTION;
    }

    public static Level debugLevel() {
        Level level = new Level();
        level.initializeDebugLevel();
        return level;
    }

    private void initializeDebugLevel() {
        spawnDefinedTank(-10,0,TankType.LIGHT_TANK,true);
        spawnTankCorrectedDoubleBullet(-10,0,BulletType.NORMAL_BULLET,Direction.RIGHT,true); //change Normal, Plama, AP and have fun :)

        spawnDefinedTank(10,0,TankType.LIGHT_TANK,false);
        spawnTankCorrectedBullet(10,0,BulletType.AP_BULLET,Direction.LEFT,false);
        }

    private Tank spawnDefinedTank(float posX, float posY, TankType type, boolean isAlly) {
        Tank tank = spawnTank(posX,posY);
        switch (type){
            case LIGHT_TANK:
                TankFixtureDef.density = Constants.LIGHT_TANK_DENSITY;
                tank.type = TankType.LIGHT_TANK;
                break;
            case HEAVY_TANK:
                tank.type = TankType.HEAVY_TANK;
                TankFixtureDef.density = Constants.HEAVY_TANK_DENSITY;
                break;
        }
        if(isAlly) {
            TankFixtureDef.filter.categoryBits = Constants.CATEGORY_ALLY_TANK;
            TankFixtureDef.filter.maskBits = Constants.MASK_ALLY_TANK;
        }else{
            TankFixtureDef.filter.categoryBits = Constants.CATEGORY_ENEMY_TANK;
            TankFixtureDef.filter.maskBits = Constants.MASK_ENEMY_TANK;
        }
        tank.body.createFixture(TankFixtureDef);
        return tank;
    }

    private Tank spawnTank(float posX, float posY) {
        tankBodyDef.position.set(posX,posY);
        Body body = world.createBody(tankBodyDef);
        Tank tank = tankPool.obtain();

        tank.init(body);
        body.setUserData(tank);

        aliveTanks.add(tank);
        return tank;
    }

    private void spawnTankCorrectedBullet(float posX, float posY, BulletType type, Direction direction, boolean isAlly) {
        switch (direction)
        {
            case UP:
                posX += tankCenter.x - Constants.BULLET_HEIGHT_H;
                posY += Constants.CELL_SIZE - Constants.BULLET_WIDTH - Constants.BULLET_EPS_SPAWN;
                break;
            case DOWN:
                posX += tankCenter.x - Constants.BULLET_HEIGHT_H;
                posY += Constants.BULLET_EPS_SPAWN;
                break;
            case LEFT:
                posY += tankCenter.y - Constants.BULLET_HEIGHT_H;
                posX += Constants.BULLET_EPS_SPAWN;
                break;
            case RIGHT:
                posX += Constants.CELL_SIZE - Constants.BULLET_WIDTH - Constants.BULLET_EPS_SPAWN;
                posY += tankCenter.y - Constants.BULLET_HEIGHT_H;
                break;
        }
        spawnDefinedBullet(posX,posY,type,direction,isAlly);
    }

    private void spawnTankCorrectedDoubleBullet(float posX, float posY, BulletType type, Direction direction, boolean isAlly) {
        switch (direction)
        {
            case UP:
                posX += tankCenter.x - Constants.BULLET_HEIGHT - Constants.DOUBLE_BULLET_EPS_SPAWN_H;
                posY += Constants.CELL_SIZE - Constants.BULLET_WIDTH - Constants.BULLET_EPS_SPAWN;
                break;
            case DOWN:
                posX += tankCenter.x - Constants.BULLET_HEIGHT - Constants.DOUBLE_BULLET_EPS_SPAWN_H;
                posY += Constants.BULLET_EPS_SPAWN;
                break;
            case LEFT:
                posY += tankCenter.y - Constants.BULLET_HEIGHT - Constants.DOUBLE_BULLET_EPS_SPAWN_H;
                posX += Constants.BULLET_EPS_SPAWN;
                break;
            case RIGHT:
                posX += Constants.CELL_SIZE - Constants.BULLET_WIDTH - Constants.BULLET_EPS_SPAWN;
                posY += tankCenter.y - Constants.BULLET_HEIGHT - Constants.DOUBLE_BULLET_EPS_SPAWN_H;
                break;
        }
        spawnDefinedDoubleBullet(posX,posY,type,direction,isAlly);
    }

    private void spawnDefinedBullet(float posX, float posY, BulletType type, Direction direction, boolean isAlly) {
        Bullet bullet = spawnBullet(posX,posY);
        configureBulletFixture(direction, isAlly);
        configureBulletType(bullet, type);
        bullet.body.createFixture(BulletFixtureDef);
        launchBullet(bullet, direction);
    }

    private void spawnDefinedDoubleBullet(float posX, float posY, BulletType type, Direction direction, boolean isAlly) {
        Bullet bullet1 = spawnBullet(posX,posY);
        switch (direction){
            case UP:
            case DOWN:
                posX += Constants.BULLET_HEIGHT + Constants.DOUBLE_BULLET_EPS_SPAWN;
                break;
            case RIGHT:
            case LEFT:
                posY += Constants.BULLET_HEIGHT + Constants.DOUBLE_BULLET_EPS_SPAWN;
                break;
        }
        Bullet bullet2 = spawnBullet(posX,posY);
        configureBulletFixture(direction, isAlly);
        configureBulletType(bullet1, type);
        configureBulletType(bullet2, type);
        bullet1.body.createFixture(BulletFixtureDef);
        bullet2.body.createFixture(BulletFixtureDef);
        launchBullet(bullet1, direction);
        launchBullet(bullet2, direction);
    }

    private Bullet spawnBullet(float posX, float posY) {
        bulletBodyDef.position.set(posX,posY);
        Body body = world.createBody(bulletBodyDef);
        Bullet bullet = bulletPool.obtain();

        bullet.init(body);
        body.setUserData(bullet);

        aliveBullets.add(bullet);
        return bullet;
    }

    private void configureBulletFixture(Direction direction, boolean isAlly) {
        if(isAlly) {
            BulletFixtureDef.filter.categoryBits = Constants.CATEGORY_ALLY_BULLET;
            BulletFixtureDef.filter.maskBits = Constants.MASK_ALLY_BULLET;
        }else{
            BulletFixtureDef.filter.categoryBits = Constants.CATEGORY_ENEMY_BULLET;
            BulletFixtureDef.filter.maskBits = Constants.MASK_ENEMY_BULLET;
        }

        switch (direction) {
            case UP:
            case DOWN:
                BulletFixtureDef.shape = verticalBulletRectangle;
                break;
            case LEFT:
            case RIGHT:
                BulletFixtureDef.shape = horizontalBulletRectangle;
                break;
        }
    }

    private void configureBulletType(Bullet bullet, BulletType type) {
        switch (type){
            case NORMAL_BULLET:
                bullet.type = BulletType.NORMAL_BULLET;
                BulletFixtureDef.density = Constants.NORMAL_BULLET_DENSITY;
                break;
            case PLASMA_BULLET:
                bullet.type = BulletType.PLASMA_BULLET;
                BulletFixtureDef.density = Constants.PLASMA_BULLET_DENSITY;
                break;
            case AP_BULLET:
                bullet.type = BulletType.AP_BULLET;
                BulletFixtureDef.density = Constants.AP_BULLET_DENSITY;
                break;
        }
    }

    private void launchBullet(Bullet bullet, Direction direction){
        switch (direction) {
            case UP:
                bullet.body.applyLinearImpulse(Constants.BULLET_UP_IMPULSE, bullet.body.getWorldCenter(), true);
                break;
            case DOWN:
                bullet.body.applyLinearImpulse(Constants.BULLET_DOWN_IMPULSE, bullet.body.getWorldCenter(), true);
                break;
            case LEFT:
                bullet.body.applyLinearImpulse(Constants.BULLET_LEFT_IMPULSE, bullet.body.getWorldCenter(), true);
                break;
            case RIGHT:
                bullet.body.applyLinearImpulse(Constants.BULLET_RIGHT_IMPULSE, bullet.body.getWorldCenter(), true);
                break;
        }
    }

    public void update(float delta) {
        frameTime = Math.min(delta, Constants.FRAME_TIME_MAX);
        accumulator += frameTime;
        while (accumulator >= Constants.PHYSICS_STEP) {
            world.step(Constants.PHYSICS_STEP, Constants.VELOCITY_ITERATIONS, Constants.POSITION_ITERATIONS);
            // processing collisions
            accumulator -= Constants.PHYSICS_STEP;
        }
    }

    public void dispose() {
        world.dispose();
        tankRectangle.dispose();
        horizontalBulletRectangle.dispose();
        verticalBulletRectangle.dispose();
        freeAliveArrays();
    }

    private void freeAliveArrays() {
        bulletPool.freeAll(aliveBullets);
        tankPool.freeAll(aliveTanks);
        wallPool.freeAll(aliveWalls);
    }
}
