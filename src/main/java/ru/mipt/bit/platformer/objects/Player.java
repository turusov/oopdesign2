package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import org.w3c.dom.Text;
import ru.mipt.bit.platformer.util.TileMovement;
import ru.mipt.bit.platformer.objects.Player;
import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class Player {

    public TextureRegion playerGraphics;
    public Rectangle playerRectangle;
    // player current position coordinates on level 10x8 grid (e.g. x=0, y=1)
    public GridPoint2 playerCoordinates;
    // which tile the player want to go next
    public GridPoint2 playerDestinationCoordinates;
    public float playerMovementProgress = 1f;
    public float playerRotation;
    public Player(){}
    public Player(TextureRegion graphics, GridPoint2 destinationCoordinates, GridPoint2 coordinates, float rotation){
        playerGraphics = graphics;
        playerRectangle = createBoundingRectangle(playerGraphics);
        playerDestinationCoordinates = destinationCoordinates;
        playerCoordinates = coordinates;
        playerRotation = rotation;
    }
    public void move(Input key, Obstacle obstacle){
        if (key.isKeyPressed(UP) || key.isKeyPressed(W)) {
            if (isEqual(playerMovementProgress, 1f)) {
                // check potential player destination for collision with obstacles
                if (!obstacle.obstacleCoordinates.equals(incrementedY(playerCoordinates))) {
                    playerDestinationCoordinates.y++;
                    playerMovementProgress = 0f;
                }
                playerRotation = 90f;
            }
        }
        if (key.isKeyPressed(LEFT) || key.isKeyPressed(A)) {
            if (isEqual(playerMovementProgress, 1f)) {
                if (!obstacle.obstacleCoordinates.equals(decrementedX(playerCoordinates))) {
                    playerDestinationCoordinates.x--;
                    playerMovementProgress = 0f;
                }
                playerRotation = -180f;
            }
        }
        if (key.isKeyPressed(DOWN) || key.isKeyPressed(S)) {
            if (isEqual(playerMovementProgress, 1f)) {
                if (!obstacle.obstacleCoordinates.equals(decrementedY(playerCoordinates))) {
                    playerDestinationCoordinates.y--;
                    playerMovementProgress = 0f;
                }
                playerRotation = -90f;
            }
        }
        if (key.isKeyPressed(RIGHT) || key.isKeyPressed(D)) {
            if (isEqual(playerMovementProgress, 1f)) {
                if (!obstacle.obstacleCoordinates.equals(incrementedX(playerCoordinates))) {
                    playerDestinationCoordinates.x++;
                    playerMovementProgress = 0f;
                }
                playerRotation = 0f;
            }
        }
    }
}
