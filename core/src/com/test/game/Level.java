package com.test.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.test.game.entities.Bullet;
import com.test.game.entities.Tank;
import com.test.game.entities.Wall;
import com.test.game.utils.Constants;

public class Level {

    public ExtendViewport viewport;

    private Array<Bullet> aliveBullets;
    private static Pool<Bullet> bulletPool = Pools.get(Bullet.class);

    private Array<Tank> aliveTanks;
    private static Pool<Tank> tankPool = Pools.get(Tank.class);

    private Array<Wall> aliveWalls;
    private static Pool<Wall> wallPool = Pools.get(Wall.class);

    private World world;
    private Box2DDebugRenderer debugRenderer;

    private float frameTime;
    private float accumulator;

    public Level() {
        this.viewport = new ExtendViewport(Constants.WORLD_SIZE, Constants.WORLD_SIZE);
        aliveBullets = new Array<Bullet>();
        aliveTanks = new Array<Tank>();
        aliveWalls = new Array<Wall>();
        world = new World(Vector2.Zero, false);
        if(Constants.DEBUG_PHYSICS_RENDER)
            debugRenderer = new Box2DDebugRenderer();
    }

    public static Level debugLevel() {
        Level level = new Level();
        level.initializeDebugLevel();
        return level;
    }

    private void initializeDebugLevel(){

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

    public void render(SpriteBatch batch) {
        if(Constants.DEBUG_PHYSICS_RENDER)
            debugRenderer.render(world, viewport.getCamera().combined);
    }

    public void dispose(){
        world.dispose();
        if(Constants.DEBUG_PHYSICS_RENDER)
            debugRenderer.dispose();
        freeAliveArrays();
    }

    private void freeAliveArrays()
    {
        bulletPool.freeAll(aliveBullets);
        tankPool.freeAll(aliveTanks);
        wallPool.freeAll(aliveWalls);
    }
}
