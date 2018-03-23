package com.test.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import com.sun.org.apache.bcel.internal.generic.SWITCH;
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

import sun.security.krb5.internal.PAData;


public class Level {

    public PlayerTank playerTank;

    public short[][] objectsMatrix = null;
    public int objectMatrixWidth = 0;
    public float levelWidth = 0;
    public int objectMatrixHeight = 0;
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
        int height = 50;
        int width = 50;
        objectsMatrix = new short[height][width];
        objectMatrixHeight = height - 1;
        levelHeigt = (height - 2) * Constants.CELL_SIZE;
        objectMatrixWidth = width - 1;
        levelWidth = (width - 2) * Constants.CELL_SIZE;
        float[] x = new float[]{20,20,20,20,20,20,20,20,20,20,20,21,22,23,24,25,26,27,28,29,30,30,30,30,30,30,30,30,30,30,30,29,28,27,26,25,24,23,22,21};
        float[] y = new float[]{30,29,28,27,26,25,24,23,22,21,20,20,20,20,20,20,20,20,20,20,20,21,22,23,24,25,26,27,28,29,30,30,30,30,30,30,30,30,30,30};

        spawnGridDefinedWalls(x,y,WallType.STONE_WALL);
        spawnGridDefinedWall(3,6, WallType.WOODEN_WALL);
        spawnGridDefinedWall( 5,5, WallType.BUSH_WALL);
        spawnGridDefinedWall(6,3, WallType.WOODEN_WALL);
        spawnGridDefinedWall(10,10, WallType.WOODEN_WALL);

        playerTank = spawnGridDefinedPlayerTank(23,25,TankType.LIGHT_TANK, Direction.RIGHT);
        spawnCorrectedDoubleBullet(playerTank.body.getPosition().x,playerTank.body.getPosition().y,DoubleBulletType.DOUBLE_NORMAL_BULLET,Direction.RIGHT,true); //change Normal, Plasma, AP and have fun :)
        spawnGridDefinedNpcTank(27, 25,TankType.LIGHT_TANK,Direction.LEFT,false);
        }


    //player
    private PlayerTank spawnGridDefinedPlayerTank(int posX, int posY, TankType type, Direction direction){
        if(objectsMatrix[objectMatrixHeight - posY][posX] == 0) {
            objectsMatrix[objectMatrixHeight - posY][posX] = Constants.CATEGORY_ALLY_TANK;
            return spawnDefinedPlayerTank(posX * Constants.CELL_SIZE + Constants.TANK_MARGIN, posY * Constants.CELL_SIZE + Constants.TANK_MARGIN, type, direction);
        }
        return null;
    }

    private PlayerTank spawnDefinedPlayerTank(float posX, float posY, TankType type, Direction direction) {
        PlayerTank playerTank = spawnPlayerTank(posX,posY);
        playerTank.direction = direction;
        switch (type){
            case LIGHT_TANK:
                tankFixtureDef.density = Constants.LIGHT_TANK_DENSITY;
                playerTank.type = TankType.LIGHT_TANK;
                break;
            case HEAVY_TANK:
                playerTank.type = TankType.HEAVY_TANK;
                tankFixtureDef.density = Constants.HEAVY_TANK_DENSITY;
                break;
        }
        tankFixtureDef.filter.categoryBits = Constants.CATEGORY_ALLY_TANK;
        tankFixtureDef.filter.maskBits = Constants.MASK_ALLY_TANK;
        playerTank.body.createFixture(tankFixtureDef);
        return playerTank;
    }

    private PlayerTank spawnPlayerTank(float posX, float posY) {
        tankBodyDef.position.set(posX,posY);
        Body body = world.createBody(tankBodyDef);
        PlayerTank playerTank = new PlayerTank(this, body);

        playerTank.init(this,body);
        body.setUserData(playerTank);

        aliveNpcTanks.add(playerTank);
        return playerTank;
    }
    //end player
    //wall
    //warning: this method doesn't take care about objectMatrix[i][j] == 0. Just brutal spawn!
    //warning: arrays should consist of integer values (2. , 3. , etc)!
    public void spawnGridDefinedWalls(float[] posX, float[] posY, WallType type) {
        if(posX == null || posY == null)
            throw new NullPointerException("spawnDefineWall(s): null array reference");
        if(posX.length != posY.length)
            throw  new IllegalArgumentException("spawnDefineWall(s): different arrays length ");
        for(int i = 0; i < posX.length; i++)
        {
            objectsMatrix[objectMatrixHeight - (int)posY[i]][(int)posX[i]] = Constants.CATEGORY_WALL;
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
        boolean immortal = false;
        int hp = 0;
        switch (type){
            case WOODEN_WALL:
                hp = Constants.WOODEN_WALL_HP_MAX;
                immortal = false;
                break;
            case STONE_WALL:
                immortal = true;
                break;
            case BUSH_WALL:
                hp = Constants.BUSH_WALL_HP_MAX;
                immortal = false;
                break;
        }

        for(int i = 0; i < posX.length; i++) {
            Wall wall = spawnWall(posX[i],posY[i]);
            wall.hp = hp;
            wall.type = type;
            wall.immortal = immortal;
            wall.body.createFixture(wallFixtureDef);
        }
    }

    private void spawnGridDefinedWall(int posX, int posY, WallType type) {
        if(objectsMatrix[objectMatrixHeight - posY][posX] == 0) {
            objectsMatrix[objectMatrixHeight - posY][posX] = Constants.CATEGORY_WALL;
            spawnDefinedWall(posX * Constants.CELL_SIZE, posY * Constants.CELL_SIZE, type);
        }
    }

    private void spawnDefinedWall(float posX, float posY, WallType type) {
        Wall wall = spawnWall(posX,posY);
        switch (type){
            case WOODEN_WALL:
                wall.type = WallType.WOODEN_WALL;
                wall.hp = Constants.WOODEN_WALL_HP_MAX;
                wall.immortal = false;
                break;
            case STONE_WALL:
                wall.type = WallType.STONE_WALL;
                wall.immortal = true;
                break;
            case BUSH_WALL:
                wall.type = WallType.BUSH_WALL;
                wall.hp = Constants.BUSH_WALL_HP_MAX;
                wall.immortal = false;
                break;
        }
        wall.body.createFixture(wallFixtureDef);
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
    private void spawnGridDefinedNpcTank(int posX, int posY, TankType type, Direction direction, boolean isAlly){
        if(objectsMatrix[objectMatrixHeight - posY][posX] == 0) {
            if (isAlly)
                objectsMatrix[objectMatrixHeight - posY][posX] = Constants.CATEGORY_ALLY_TANK;
            else
                objectsMatrix[objectMatrixHeight - posY][posX] = Constants.CATEGORY_ENEMY_TANK;
            spawnDefinedNpcTank(posX * Constants.CELL_SIZE + Constants.TANK_MARGIN, posY * Constants.CELL_SIZE + Constants.TANK_MARGIN, type, direction, isAlly);
        }
    }

    private void spawnDefinedNpcTank(float posX, float posY, TankType type, Direction direction, boolean isAlly) {
        NpcTank npcTank = spawnNpcTank(posX,posY);
        npcTank.direction = direction;
        switch (type){
            case LIGHT_TANK:
                tankFixtureDef.density = Constants.LIGHT_TANK_DENSITY;
                npcTank.type = TankType.LIGHT_TANK;
                break;
            case HEAVY_TANK:
                npcTank.type = TankType.HEAVY_TANK;
                tankFixtureDef.density = Constants.HEAVY_TANK_DENSITY;
                break;
        }
        if(isAlly) {
            tankFixtureDef.filter.categoryBits = Constants.CATEGORY_ALLY_TANK;
            tankFixtureDef.filter.maskBits = Constants.MASK_ALLY_TANK;
        }else{
            tankFixtureDef.filter.categoryBits = Constants.CATEGORY_ENEMY_TANK;
            tankFixtureDef.filter.maskBits = Constants.MASK_ENEMY_TANK;
        }
        npcTank.body.createFixture(tankFixtureDef);
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
    //end npc
    //bullet
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
        configureBulletFixture(direction, isAlly);
        configureBulletType(bullet, type);
        bullet.body.createFixture(bulletFixtureDef);
        launchBullet(bullet, direction);
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
        configureBulletFixture(direction, isAlly);
        switch(type){
            case DOUBLE_NORMAL_BULLET:
                configureBulletType(bullet1, BulletType.NORMAL_BULLET);
                configureBulletType(bullet2, BulletType.NORMAL_BULLET);
                break;
            case DOUBLE_PLASMA_BULLET:
                configureBulletType(bullet1, BulletType.PLASMA_BULLET);
                configureBulletType(bullet2, BulletType.PLASMA_BULLET);
                break;
        }
        bullet1.body.createFixture(bulletFixtureDef);
        bullet2.body.createFixture(bulletFixtureDef);
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
    }

    private void configureBulletType(Bullet bullet, BulletType type) {
        switch (type){
            case NORMAL_BULLET:
                bullet.type = BulletType.NORMAL_BULLET;
                bullet.hp = Constants.NORMAL_BULLET_MAX_HP;
                bulletFixtureDef.density = Constants.NORMAL_BULLET_DENSITY;
                break;
            case PLASMA_BULLET:
                bullet.type = BulletType.PLASMA_BULLET;
                bullet.hp = Constants.PLASMA_BULLET_MAX_HP;
                bulletFixtureDef.density = Constants.PLASMA_BULLET_DENSITY;
                break;
            case AP_BULLET:
                bullet.type = BulletType.AP_BULLET;
                bullet.hp = Constants.AP_BULLET_MAX_HP;
                bulletFixtureDef.density = Constants.AP_BULLET_DENSITY;
                break;
            case RAP_BULLET:
                bullet.type = BulletType.RAP_BULLET;
                bullet.hp = Constants.RAP_BULLET_MAX_HP;
                bulletFixtureDef.density = Constants.AP_BULLET_DENSITY;
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

    // end bullet

    public void update(float delta) {
        playerTank.update();
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
