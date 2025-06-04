package com.tilldawn.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Barrier {
    private float width;
    private final float height;
    private final float shrinkRate = 50f;
    private final float minWidth = 200f;
    private Texture texture;
    private boolean active = true;

    public Barrier(float screenWidth, float screenHeight) {
        this.width = screenWidth;
        this.height = screenHeight;
        this.texture = new Texture("barrier.png");
    }

    public void update(float delta) {
        if (active && width > minWidth) {
            width -= shrinkRate * delta;
        }
    }

    public void render(SpriteBatch batch) {
        if (active) {
            batch.draw(texture, 0, 0, width, height);
        }
    }

    public boolean collidesWith(Sprite playerSprite) {
        return active && !playerSprite.getBoundingRectangle().contains(width, height);
    }

    public void deactivate() {
        active = false;
    }

    public boolean isActive() {
        return active;
    }
}
