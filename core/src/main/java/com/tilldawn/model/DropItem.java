package com.tilldawn.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class DropItem {
    private Vector2 position;
    private boolean collected = false;
    private CollisionRect rect;
    private static final int SIZE = 16;

    public DropItem(Vector2 position) {
        this.position = new Vector2(position);
        this.rect = new CollisionRect(position.x, position.y, SIZE, SIZE);
    }

    public Vector2 getPosition() {
        return position;
    }

    public boolean isCollected() {
        return collected;
    }

    public void collect() {
        this.collected = true;
    }

    public void update() {
        rect.move(position.x, position.y);
    }

    public void render(SpriteBatch batch, Texture texture) {
        if (!collected) {
            batch.draw(texture, position.x, position.y, SIZE, SIZE);
        }
    }

    public CollisionRect getRect() {
        return rect;
    }
}
