package com.test.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.test.game.utils.Constants;

public class Level {

    public ExtendViewport viewport;

    private World world;
    private Box2DDebugRenderer debugRenderer;

    private float frameTime;
    private float accumulator;

    public Level() {
        this.viewport = new ExtendViewport(Constants.WORLD_SIZE, Constants.WORLD_SIZE);

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

    public void render() {
        if(Constants.DEBUG_PHYSICS_RENDER)
            debugRenderer.render(world, viewport.getCamera().combined);
    }

    public void dispose(){
        world.dispose();
        if(Constants.DEBUG_PHYSICS_RENDER)
            debugRenderer.dispose();
    }
}
