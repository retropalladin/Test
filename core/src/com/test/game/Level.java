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
import com.test.game.entities.NpcTank;
import com.test.game.entities.PlayerTank;
import com.test.game.entities.Wall;
import com.test.game.utils.Constants;
import com.test.game.utils.Enums.BulletType;
import com.test.game.utils.Enums.DoubleBulletType;
import com.test.game.utils.Enums.Direction;
import com.test.game.utils.Enums.TankType;
import com.test.game.utils.Enums.WallType;


public class Level {

    public PlayerTank playerTank;

    public short[][] objectsMatrix = null; //warning : she is reversed. coord gridX = 0, gridY = 0 is equal objectMatrix[gridY,gridX] so gridY axis is reversed.
    public short objectMatrixWidth = 0;
    public float levelWidth = 0;
    public short objectMatrixHeight = 0;
    public float levelHeigt = 0;

    public Array<Bullet> aliveBullets;
    private static Pool<Bullet> bulletPool = Pools.get(Bullet.class);

    public Array<NpcTank> aliveNpcTanks;
    private static Pool<NpcTank> npcTankPool = Pools.get(NpcTank.class);

    public Array<Wall> aliveWalls;
    private static Pool<Wall> wallPool = Pools.get(Wall.class);

    public World world;

    private float frameTime;
    private float accumulator;

    private Vector2 wallRectangleCenter;

    private BodyDef wallBodyDef;
    private PolygonShape wallRectangle;
    private FixtureDef wallFixtureDef;

    private Vector2 tankCenter;
    private BodyDef tankBodyDef;
    private PolygonShape tankRectangle;
    private FixtureDef tankFixtureDef;

    private Vector2 horizontalBulletCenter;
    private Vector2 verticalBulletCenter;
    private BodyDef bulletBodyDef;
    private PolygonShape horizontalBulletRectangle;
    private PolygonShape verticalBulletRectangle;
    private FixtureDef bulletFixtureDef;

    public Level() {
        aliveBullets = new Array<Bullet>();
        aliveNpcTanks = new Array<NpcTank>();
        aliveWalls = new Array<Wall>();

        world = new World(Vector2.Zero, false);

        wallRectangleCenter = new Vector2(Constants.CELL_SIZE * 0.5f, Constants.CELL_SIZE * 0.5f);

        wallBodyDef = new BodyDef();
        wallBodyDef.type = BodyType.StaticBody;
        wallBodyDef.fixedRotation = true;

        wallRectangle = new PolygonShape();
        wallRectangle.setAsBox(Constants.CELL_SIZE * 0.5f, Constants.CELL_SIZE * 0.5f, wallRectangleCenter, 0);

        wallFixtureDef = new FixtureDef();
        wallFixtureDef.shape = wallRectangle;
        wallFixtureDef.friction = Constants.WALL_FRICTION;
        wallFixtureDef.restitution = Constants.WALL_RESTITUTION;
        wallFixtureDef.filter.categoryBits = Constants.CATEGORY_WALL;
        wallFixtureDef.filter.maskBits = Constants.MASK_WALL;

        tankCenter = new Vector2(Constants.TANK_WIDTH_H, Constants.TANK_HEIGHT_H);

        tankBodyDef = new BodyDef();
        tankBodyDef.type = BodyType.DynamicBody;
        tankBodyDef.fixedRotation = true;

        tankRectangle = new PolygonShape();
        tankRectangle.setAsBox(Constants.TANK_WIDTH_H, Constants.TANK_HEIGHT_H, tankCenter, 0);

        tankFixtureDef = new FixtureDef();
        tankFixtureDef.shape = tankRectangle;
        tankFixtureDef.friction = Constants.TANK_FRICTION;
        tankFixtureDef.restitution = Constants.TANK_RESTITUTION;

        horizontalBulletCenter = new Vector2(Constants.BULLET_WIDTH_H, Constants.BULLET_HEIGHT_H);
        verticalBulletCenter = new Vector2(Constants.BULLET_HEIGHT_H, Constants.BULLET_WIDTH_H);

        bulletBodyDef = new BodyDef();
        bulletBodyDef.type = BodyType.DynamicBody;
        bulletBodyDef.fixedRotation = true;

        horizontalBulletRectangle = new PolygonShape();
        verticalBulletRectangle = new PolygonShape();

        horizontalBulletRectangle.setAsBox(Constants.BULLET_WIDTH_H, Constants.BULLET_HEIGHT_H, horizontalBulletCenter , 0);
        verticalBulletRectangle.setAsBox(Constants.BULLET_HEIGHT_H, Constants.BULLET_WIDTH_H, verticalBulletCenter, 0);

        bulletFixtureDef = new FixtureDef();
        bulletFixtureDef.shape = horizontalBulletRectangle;
        bulletFixtureDef.friction = Constants.BULLET_FRICTION;
        bulletFixtureDef.restitution = Constants.BULLET_RESTITUTION;
    }

    public static Level debugLevel() {
        Level level = new Level();
        level.initializeDebugLevel();
        return level;
    }

    private void initializeDebugLevel() {
        short height = 50;
        short width = 50;
        objectsMatrix = new short[height][width];
        for(int i = 0; i < height; i++)
            for (int j = 0; j < width; j++)
                objectsMatrix[i][j] = Constants.CATEGORY_EMPTY;
        objectMatrixHeight = (short) (height - 1);
        levelHeigt = (height - 2) * Constants.CELL_SIZE;
        objectMatrixWidth = (short) (width - 1);
        levelWidth = (width - 2) * Constants.CELL_SIZE;
        float[] x = new float[]{20,20,20,20,20,20,20,20,20,20,20,21,22,23,24,25,26,27,28,29,30,30,30,30,30,30,30,30,30,30,30,29,28,27,26,25,24,23,22,21};
        float[] y = new float[]{30,29,28,27,26,25,24,23,22,21,20,20,20,20,20,20,20,20,20,20,20,21,22,23,24,25,26,27,28,29,30,30,30,30,30,30,30,30,30,30};

        spawnGridDefinedWalls(x,y,WallType.STONE_WALL);

        playerTank = spawnGridDefinedPlayerTank((short)21,(short)29,TankType.LIGHT_TANK, Direction.RIGHT);
        spawnGridDefinedNpcTank((short)27,(short)25,TankType.LIGHT_TANK,Direction.LEFT,false);
        }


    //player
    private PlayerTank spawnGridDefinedPlayerTank(short posX, short posY, TankType type, Direction direction){
        if(objectsMatrix[posY][posX] == Constants.CATEGORY_EMPTY) {
            objectsMatrix[posY][posX] = Constants.CATEGORY_ALLY_TANK;
            PlayerTank playerTank = spawnDefinedPlayerTank(posX * Constants.CELL_SIZE + Constants.TANK_MARGIN, posY * Constants.CELL_SIZE + Constants.TANK_MARGIN, type, direction);
            playerTank.setGridCoordinates(posX, posY);
            return playerTank;
        }
        return null;
    }

    private PlayerTank spawnDefinedPlayerTank(float posX, float posY, TankType type, Direction direction) {
        PlayerTank playerTank = spawnPlayerTank(posX,posY);
        configurePlayerTankFixture(type);
        playerTank.configurePlayerTankType(Constants.CATEGORY_ALLY_TANK, type, direction);
        playerTank.createFixture(tankFixtureDef);
        return playerTank;
    }

    private PlayerTank spawnPlayerTank(float posX, float posY) {
        tankBodyDef.position.set(posX,posY);
        Body body = world.createBody(tankBodyDef);

        PlayerTank playerTank = new PlayerTank(this, body);
        body.setUserData(playerTank);

        aliveNpcTanks.add(playerTank);
        return playerTank;
    }

    private void configurePlayerTankFixture(TankType type) {
        tankFixtureDef.filter.categoryBits = Constants.CATEGORY_ALLY_TANK;
        tankFixtureDef.filter.maskBits = Constants.MASK_ALLY_TANK;

        switch (type){
            case LIGHT_TANK:
                tankFixtureDef.density = Constants.LIGHT_TANK_DENSITY;
                break;
            case HEAVY_TANK:
                tankFixtureDef.density = Constants.HEAVY_TANK_DENSITY;
                break;
        }
    }
    //end player
    //wall
    //warning: this method doesn't take care about objectMatrix[i][j] == 0. Just brutal spawn!
    //warning: arrays should consist of short values (2. , 3. , etc)!
    //warning: this method doesn't set grid coordinates.
    public void spawnGridDefinedWalls(float[] posX, float[] posY, WallType type) {
        if(posX == null || posY == null)
            throw new NullPointerException("spawnDefineWall(s): null array reference");
        if(posX.length != posY.length)
            throw  new IllegalArgumentException("spawnDefineWall(s): different arrays length ");
        for(int i = 0; i < posX.length; i++)
        {
            objectsMatrix[(short)posY[i]][(short)posX[i]] = Constants.CATEGORY_WALL;
            posX[i] *= Constants.CELL_SIZE;
            posY[i] *= Constants.CELL_SIZE;
        }
        spawnDefinedWalls(posX, posY, type);
    }

    public void spawnDefinedWalls(float[] posX, float[] posY, WallType type) {
        if(posX == null || posY == null)
            throw new NullPointerException("spawnDefineWall(s): null array reference");
        if(posX.length != posY.length)
            throw  new IllegalArgumentException("spawnDefineWall(s): different arrays length ");
        for(int i = 0; i < posX.length; i++) {
            Wall wall = spawnWall(posX[i],posY[i]);
            wall.configureWallType(Constants.CATEGORY_WALL, type);
            wall.createFixture(wallFixtureDef);
        }
    }

    private void spawnGridDefinedWall(short posX, short posY, WallType type) {
        if(objectsMatrix[posY][posX] == Constants.CATEGORY_EMPTY) {
            objectsMatrix[posY][posX] = Constants.CATEGORY_WALL;
            Wall wall = spawnDefinedWall(posX * Constants.CELL_SIZE, posY * Constants.CELL_SIZE, type);
            wall.setGridCoordinates(posX, posY);
        }
    }

    private Wall spawnDefinedWall(float posX, float posY, WallType type) {
        Wall wall = spawnWall(posX,posY);
        wall.configureWallType(Constants.CATEGORY_WALL, type);
        wall.createFixture(wallFixtureDef);
        return wall;
    }

    private Wall spawnWall(float posX, float posY) {
        wallBodyDef.position.set(posX,posY);
        Body body = world.createBody(wallBodyDef);
        Wall wall = wallPool.obtain();

        wall.init(body);
        body.setUserData(wall);

        aliveWalls.add(wall);
        return wall;
    }
    //end wall
    //npc
    private void spawnGridDefinedNpcTank(short posX, short posY, TankType type, Direction direction, boolean isAlly){
        if(objectsMatrix[posY][posX] == Constants.CATEGORY_EMPTY) {
            if (isAlly)
                objectsMatrix[posY][posX] = Constants.CATEGORY_ALLY_TANK;
            else
                objectsMatrix[posY][posX] = Constants.CATEGORY_ENEMY_TANK;
            NpcTank npcTank = spawnDefinedNpcTank(posX * Constants.CELL_SIZE + Constants.TANK_MARGIN, posY * Constants.CELL_SIZE + Constants.TANK_MARGIN, type, direction, isAlly);
            npcTank.setGridCoordinates(posX, posY);
        }
    }

    private NpcTank spawnDefinedNpcTank(float posX, float posY, TankType type, Direction direction, boolean isAlly) {
        NpcTank npcTank = spawnNpcTank(posX,posY);
        configureNpcTankFixture(type, isAlly);
        npcTank.configureNpcTankType(tankFixtureDef.filter.categoryBits, type, direction);
        npcTank.createFixture(tankFixtureDef);
        return npcTank;
    }

    private NpcTank spawnNpcTank(float posX, float posY) {
        tankBodyDef.position.set(posX,posY);
        Body body = world.createBody(tankBodyDef);
        NpcTank npcTank = npcTankPool.obtain();

        npcTank.init(this,body);
        body.setUserData(npcTank);

        aliveNpcTanks.add(npcTank);
        return npcTank;
    }

    private void configureNpcTankFixture(TankType type, boolean isAlly) {
        if(isAlly) {
            tankFixtureDef.filter.categoryBits = Constants.CATEGORY_ALLY_TANK;
            tankFixtureDef.filter.maskBits = Constants.MASK_ALLY_TANK;
        }else{
            tankFixtureDef.filter.categoryBits = Constants.CATEGORY_ENEMY_TANK;
            tankFixtureDef.filter.maskBits = Constants.MASK_ENEMY_TANK;
        }

        switch (type){
            case LIGHT_TANK:
                tankFixtureDef.density = Constants.LIGHT_TANK_DENSITY;
                break;
            case HEAVY_TANK:
                tankFixtureDef.density = Constants.HEAVY_TANK_DENSITY;
                break;
        }
    }
    //end npc
    //bullet
    //warning: bullet doesn't have grid coordinates
    private void spawnCorrectedBullet(float posX, float posY, BulletType type, Direction direction, boolean isAlly) {
        switch (direction)
        {
            case UP:
                posX += tankCenter.x - Constants.BULLET_HEIGHT_H;
                posY += Constants.TANK_HEIGHT - Constants.BULLET_WIDTH - Constants.BULLET_EPS_SPAWN;
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
                posX += Constants.TANK_WIDTH - Constants.BULLET_WIDTH - Constants.BULLET_EPS_SPAWN;
                posY += tankCenter.y - Constants.BULLET_HEIGHT_H;
                break;
        }
        spawnDefinedBullet(posX,posY,type,direction,isAlly);
    }

    private void spawnCorrectedDoubleBullet(float posX, float posY, DoubleBulletType type, Direction direction, boolean isAlly) {
        switch (direction)
        {
            case UP:
                posX += tankCenter.x - Constants.BULLET_HEIGHT - Constants.DOUBLE_BULLET_EPS_SPAWN_H;
                posY += Constants.TANK_HEIGHT - Constants.BULLET_WIDTH - Constants.BULLET_EPS_SPAWN;
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
                posX += Constants.TANK_WIDTH - Constants.BULLET_WIDTH - Constants.BULLET_EPS_SPAWN;
                posY += tankCenter.y - Constants.BULLET_HEIGHT - Constants.DOUBLE_BULLET_EPS_SPAWN_H;
                break;
        }
        spawnDefinedDoubleBullet(posX,posY,type,direction,isAlly);
    }

    private void spawnDefinedBullet(float posX, float posY, BulletType type, Direction direction, boolean isAlly) {
        Bullet bullet = spawnBullet(posX,posY);
        configureBulletFixture(direction, type, isAlly);
        bullet.configureBulletType(bulletFixtureDef.filter.categoryBits, type);
        bullet.createFixture(bulletFixtureDef);
        bullet.launch(direction);
    }

    private void spawnDefinedDoubleBullet(float posX, float posY, DoubleBulletType type, Direction direction, boolean isAlly) {
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
        switch(type){
            case DOUBLE_NORMAL_BULLET:
                configureBulletFixture(direction, BulletType.NORMAL_BULLET, isAlly);
                bullet1.configureBulletType(bulletFixtureDef.filter.categoryBits, BulletType.NORMAL_BULLET);
                bullet2.configureBulletType(bulletFixtureDef.filter.categoryBits, BulletType.NORMAL_BULLET);
                break;
            case DOUBLE_PLASMA_BULLET:
                configureBulletFixture(direction, BulletType.PLASMA_BULLET, isAlly);
                bullet1.configureBulletType(bulletFixtureDef.filter.categoryBits, BulletType.PLASMA_BULLET);
                bullet2.configureBulletType(bulletFixtureDef.filter.categoryBits, BulletType.PLASMA_BULLET);
                break;
        }
        bullet1.createFixture(bulletFixtureDef);
        bullet2.createFixture(bulletFixtureDef);
        bullet1.launch(direction);
        bullet2.launch(direction);
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

    private void configureBulletFixture(Direction direction, BulletType type, boolean isAlly) {
        if(isAlly) {
            bulletFixtureDef.filter.categoryBits = Constants.CATEGORY_ALLY_BULLET;
            bulletFixtureDef.filter.maskBits = Constants.MASK_ALLY_BULLET;
        }else{
            bulletFixtureDef.filter.categoryBits = Constants.CATEGORY_ENEMY_BULLET;
            bulletFixtureDef.filter.maskBits = Constants.MASK_ENEMY_BULLET;
        }

        switch (direction) {
            case UP:
            case DOWN:
                bulletFixtureDef.shape = verticalBulletRectangle;
                break;
            case LEFT:
            case RIGHT:
                bulletFixtureDef.shape = horizontalBulletRectangle;
                break;
        }

        switch (type){
            case NORMAL_BULLET:
                bulletFixtureDef.density = Constants.NORMAL_BULLET_DENSITY;
                break;
            case PLASMA_BULLET:
                bulletFixtureDef.density = Constants.PLASMA_BULLET_DENSITY;
                break;
            case AP_BULLET:
                bulletFixtureDef.density = Constants.AP_BULLET_DENSITY;
                break;
            case RAP_BULLET:
                bulletFixtureDef.density = Constants.AP_BULLET_DENSITY;
                break;
        }
    }
    // end bullet

    public void update(float delta) {
        playerTank.update(delta);
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
        wallRectangle.dispose();
        tankRectangle.dispose();
        horizontalBulletRectangle.dispose();
        verticalBulletRectangle.dispose();
        freeAliveArrays();
    }

    private void freeAliveArrays() {
        bulletPool.freeAll(aliveBullets);
        npcTankPool.freeAll(aliveNpcTanks);
        wallPool.freeAll(aliveWalls);
    }
}
