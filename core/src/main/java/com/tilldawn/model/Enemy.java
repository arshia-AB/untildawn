package com.tilldawn.model;

import com.badlogic.gdx.math.Vector2;

public abstract class Enemy {
    protected Vector2 position;
    protected Vector2 velocity;
    protected int hp;
    protected float speed;
    protected boolean alive = true;
    protected CollisionRect rect;
    protected static final int SIZE = 32;

    public Enemy(Vector2 spawnPos, int hp, float speed) {
        this.position = spawnPos;
        this.hp = hp;
        this.speed = speed;
        this.velocity = new Vector2();
        this.rect = new CollisionRect(position.x, position.y, SIZE, SIZE);
    }

    public void update(float delta, Vector2 playerPos) {
        Vector2 direction = new Vector2(playerPos).sub(position).nor();
        velocity.set(direction.scl(speed));
        position.add(velocity.x * delta, velocity.y * delta);
        rect.move(position.x, position.y);
    }

    public void takeDamage(int damage) {
        hp -= damage;
        if (hp <= 0) {
            alive = false;
        }
    }

    public boolean isAlive() {
        return alive;
    }

    public CollisionRect getRect() {
        return rect;
    }

    public Vector2 getPosition() {
        return position;
    }

    public abstract DropItem onDeath();
}
