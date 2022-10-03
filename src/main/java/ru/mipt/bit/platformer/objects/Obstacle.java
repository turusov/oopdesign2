package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;

import javax.swing.plaf.TextUI;

import java.awt.*;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;

public class Obstacle {
    public Texture obstacleTexture;
    public TextureRegion obstacleGraphics;
    public GridPoint2 obstacleCoordinates;
    public Rectangle obstacleRectangle;
    public Obstacle(){}
    public Obstacle(Texture texture, GridPoint2 textureCoordinates ){
        obstacleTexture = texture;
        obstacleGraphics = new TextureRegion(texture);
        obstacleCoordinates = textureCoordinates;
        obstacleRectangle = createBoundingRectangle(obstacleGraphics);
    }
}
