package com.tilldawn.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class EnemyBullet {
    private static final float SPEED = 300f;
    private static final int SIZE = 20;

    private Vector2 position;
    private Vector2 velocity;
    private Sprite sprite;
    private boolean alive = true;

    public EnemyBullet(Vector2 start, Vector2 target) {
        position = new Vector2(start);
        velocity = target.sub(start).nor().scl(SPEED);

        Texture texture = new Texture("bullet.png");
        sprite = new Sprite(texture);
        sprite.setSize(SIZE, SIZE);
        sprite.setPosition(position.x, position.y);
    }

    public void update(float delta) {
        position.mulAdd(velocity, delta);
        sprite.setPosition(position.x, position.y);
    }

    public void render(com.badlogic.gdx.graphics.g2d.SpriteBatch batch) {
        if (alive) sprite.draw(batch);
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public Vector2 getPosition() {
        return position;
    }
}
