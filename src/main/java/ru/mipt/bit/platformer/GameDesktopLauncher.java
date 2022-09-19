package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.objects.Obstacle;
import ru.mipt.bit.platformer.util.TileMovement;
import ru.mipt.bit.platformer.objects.Player;
import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class GameDesktopLauncher implements ApplicationListener {

    private static final float MOVEMENT_SPEED = 0.4f;

    private Batch batch;

    private TiledMap level;
    private MapRenderer levelRenderer;
    private TileMovement tileMovement;

    private Texture blueTankTexture;
//    private TextureRegion playerGraphics;
//    private Rectangle playerRectangle;
//    // player current position coordinates on level 10x8 grid (e.g. x=0, y=1)
//    private GridPoint2 playerCoordinates;
//    // which tile the player want to go next
//    private GridPoint2 playerDestinationCoordinates;
//    private float playerMovementProgress = 1f;
//    private float playerRotation;
    private Player player;

//    private Texture greenTreeTexture;
//    private TextureRegion treeObstacleGraphics;
//    private GridPoint2 treeObstacleCoordinates = new GridPoint2();
//    private Rectangle treeObstacleRectangle = new Rectangle();
    private Obstacle obstacle;
    @Override
    public void create() {
        batch = new SpriteBatch();
        // load level tiles
        level = new TmxMapLoader().load("level.tmx");
        levelRenderer = createSingleLayerMapRenderer(level, batch);
        TiledMapTileLayer groundLayer = getSingleLayer(level);
        tileMovement = new TileMovement(groundLayer, Interpolation.smooth);

        // Texture decodes an image file and loads it into GPU memory, it represents a native resource
        // blueTankTexture = new Texture("images/tank_blue.png");
        // TextureRegion represents Texture portion, there may be many TextureRegion instances of the same Texture
        // set player initial position

//        playerGraphics = new TextureRegion(blueTankTexture);
//        playerRectangle = createBoundingRectangle(playerGraphics);
//        // set player initial position
//        playerDestinationCoordinates = new GridPoint2(1, 1);
//        playerCoordinates = new GridPoint2(playerDestinationCoordinates);
//        playerRotation = 0f;

        player = new Player(
                new TextureRegion(new Texture("images/tank_blue.png")),
                new GridPoint2(1,1),
                new GridPoint2(1,1),
                0f
        );

        obstacle = new Obstacle(
                new Texture("images/greenTree.png"),
                new GridPoint2(1, 3)
        );
//        greenTreeTexture = new Texture("images/greenTree.png");
//        treeObstacleGraphics = new TextureRegion(greenTreeTexture);
//        treeObstacleCoordinates = new GridPoint2(1, 3);
//        treeObstacleRectangle = createBoundingRectangle(treeObstacleGraphics);
        moveRectangleAtTileCenter(groundLayer, obstacle.obstacleRectangle, obstacle.obstacleCoordinates);
    }

    @Override
    public void render() {
        // clear the screen
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);

        // get time passed since the last render
        float deltaTime = Gdx.graphics.getDeltaTime();

        player.move(Gdx.input, obstacle);


        // calculate interpolated player screen coordinates
        tileMovement.moveRectangleBetweenTileCenters(player.playerRectangle, player.playerCoordinates,
                player.playerDestinationCoordinates, player.playerMovementProgress);

        player.playerMovementProgress = continueProgress(player.playerMovementProgress, deltaTime, MOVEMENT_SPEED);
        if (isEqual(player.playerMovementProgress, 1f)) {
            // record that the player has reached his/her destination
            player.playerCoordinates.set(player.playerDestinationCoordinates);
        }

        // render each tile of the level
        levelRenderer.render();

        // start recording all drawing commands
        batch.begin();

        // render player
        drawTextureRegionUnscaled(batch, player.playerGraphics, player.playerRectangle, player.playerRotation);

        // render tree obstacle
        drawTextureRegionUnscaled(batch, obstacle.obstacleGraphics, obstacle.obstacleRectangle, 0f);

        // submit all drawing requests
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        // do not react to window resizing
    }

    @Override
    public void pause() {
        // game doesn't get paused
    }

    @Override
    public void resume() {
        // game doesn't get paused
    }

    @Override
    public void dispose() {
        // dispose of all the native resources (classes which implement com.badlogic.gdx.utils.Disposable)
        obstacle.obstacleTexture.dispose();
        blueTankTexture.dispose();
        level.dispose();
        batch.dispose();
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        // level width: 10 tiles x 128px, height: 8 tiles x 128px
        config.setWindowedMode(800, 800);
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }
}
