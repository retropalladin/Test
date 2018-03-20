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

    private Stage stage;

    private World world;
    private Box2DDebugRenderer debugRenderer;

    private float frameTime;
    private float accumulator;

    public Level(SpriteBatch batch) {
        this.viewport = new ExtendViewport(Constants.WORLD_SIZE, Constants.WORLD_SIZE);

        world = new World(Vector2.Zero, false);
        if(Constants.DEBUG_PHYSICS_RENDER)
            debugRenderer = new Box2DDebugRenderer();

        stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);
    }

    public static Level debugLevel(SpriteBatch batch) {
        Level level = new Level(batch);
        level.initializeDebugLevel();
        return level;
    }

    private void initializeDebugLevel(){

    }

    public void update(float delta) {
        frameTime = Math.min(delta, 0.25f);
        accumulator += frameTime;
        while (accumulator >= 1/45f) {
            world.step(1/45f, 6, 2);
            accumulator -= 1/45f;
        }
    }

    public void render() {
        if(Constants.DEBUG_PHYSICS_RENDER)
            debugRenderer.render(world, viewport.getCamera().combined);
    }
}
