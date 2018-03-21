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

import static com.badlogic.gdx.math.Interpolation.circle;

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
    private FixtureDef tankFixtureDef;


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

        tankFixtureDef = new FixtureDef();
        tankFixtureDef.shape = tankRectangle;
    }

    public static Level debugLevel() {
        Level level = new Level();
        level.initializeDebugLevel();
        return level;
    }

    private void initializeDebugLevel() {
        spawnLightTank(0,0);
    }

    private Tank spawnLightTank(float posX, float posY)
    {
        Tank lightTank = spawnTank(posX,posY);
        tankFixtureDef.density = 0.5f;
        tankFixtureDef.friction = 0.0f;
        tankFixtureDef.restitution = 0.0f;
        lightTank.body.createFixture(tankFixtureDef);
        return lightTank;
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

    public void update(float delta) {
        frameTime = Math.min(delta, Constants.FRAME_TIME_MAX);
        accumulator += frameTime;
        while (accumulator >= Constants.PHYSICS_STEP) {
            world.step(Constants.PHYSICS_STEP, Constants.VELOCITY_ITERATIONS, Constants.POSITION_ITERATIONS);
            // processing collisions
            accumulator -= Constants.PHYSICS_STEP;
        }
    }

    public void dispose(){
        world.dispose();
        tankRectangle.dispose();
        freeAliveArrays();
    }

    private void freeAliveArrays()
    {
        bulletPool.freeAll(aliveBullets);
        tankPool.freeAll(aliveTanks);
        wallPool.freeAll(aliveWalls);
    }
}
