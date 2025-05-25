package com.tilldawn.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class Enemy {
    protected float x, y;
    protected float speed;
    protected int hp;
    protected Texture texture;
    protected Sprite sprite;

    public Enemy(float x, float y, int hp, float speed, Texture texture) {
        this.x = x;
        this.y = y;
        this.hp = hp;
        this.speed = speed;
        this.texture = texture;
        this.sprite = new Sprite(texture);
        this.sprite.setPosition(x, y);
    }

    public abstract void update(float delta, Vector2 playerPos);

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public boolean isDead() {
        return hp <= 0;
    }

    public void takeDamage(int dmg) {
        this.hp -= dmg;
    }

    public Sprite getSprite() {
        return sprite;
    }
}

