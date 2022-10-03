package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;

public class TankGraphics {
    private Texture tank;
    private TextureRegion graphics;
    private Rectangle rectangle;

    public TankGraphics(Texture tankTexture){
        tank = tankTexture;
        this.graphics = new TextureRegion(tank);
        this.rectangle = createBoundingRectangle(this.graphics);
    }

    public TextureRegion getGraphics() {
        return graphics;
    }

    public Texture getTank(){
        return tank;
    }
    public Rectangle getRectangle() {
        return rectangle;
    }
}