package com.tilldawn.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Bullet {
    private Texture texture = new Texture(GameAssetManager.getGameAssetManager().getBullet());
    private Sprite sprite;
    private CollisionRect rect;
    private int damage = 5;
    private boolean alive = true;

    private Vector2 position;
    private Vector2 velocity;
    private static final float SPEED = 500f;
    private static final int SIZE = 20;

    public Bullet(int x, int y) {
        position = new Vector2(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);
        Vector2 target = new Vector2(x, y);
        velocity = target.sub(position).nor().scl(SPEED);

        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        sprite = new Sprite(texture);
        sprite.setSize(SIZE, SIZE);
        sprite.setPosition(position.x, position.y);

        rect = new CollisionRect(position.x, position.y, SIZE, SIZE);
    }

    public void update(float delta) {
        position.add(velocity.x * delta, velocity.y * delta);
        sprite.setPosition(position.x, position.y);
        rect.move(position.x, position.y);
    }

    public void render(com.badlogic.gdx.graphics.g2d.SpriteBatch batch) {
        if (alive) {
            sprite.draw(batch);
        }
    }

    public int getDamage() {
        return damage;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public CollisionRect getRect() {
        return rect;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public Texture getTexture() {
        return texture;
    }
}
