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

    protected boolean isDying = false;
    protected boolean isDead = false;
    protected float deathTimer = 0f;

    protected com.badlogic.gdx.graphics.g2d.Animation<Texture> deathAnimation;
    protected Texture[] deathFrames;

    public boolean isAnimationDead() {
        return isDead;
    }

    public Enemy(float x, float y, int hp, float speed, Texture texture) {
        this.x = x;
        this.y = y;
        this.hp = hp;
        this.speed = speed;
        this.texture = texture;
        this.sprite = new Sprite(texture);
        this.sprite.setPosition(x, y);
        deathFrames = new Texture[4];
        for (int i = 0; i < 4; i++) {
            deathFrames[i] = new Texture("DeathFX/DeathFX_" + i + ".png");
        }
        deathAnimation = new com.badlogic.gdx.graphics.g2d.Animation<>(0.1f, deathFrames);
    }

    public void die() {
        if (!isDying) {
            isDying = true;
            deathTimer = 0f;

        }
    }

    public abstract void update(float delta, Vector2 playerPos);

    public void draw(SpriteBatch batch) {
        if (isDying) {
            Texture currentFrame = deathAnimation.getKeyFrame(deathTimer, false);
            batch.draw(currentFrame, x, y);
        } else {
            batch.draw(texture, x, y);
        }
    }

    public boolean isDead() {
        return hp <= 0;
    }

    public void takeDamage(int dmg) {
        this.hp -= dmg;
        if (hp <= 0 && !isDying) {
            die();
        }

    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public CollisionRect getRect() {
        return new CollisionRect(x, y, sprite.getWidth(), sprite.getHeight());
    }


}

