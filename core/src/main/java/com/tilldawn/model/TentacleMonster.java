package com.tilldawn.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class TentacleMonster extends Enemy {
    private static final Texture texture = new Texture("enemy/T_TentacleEnemy_0.png");

    public TentacleMonster(Vector2 spawnPos) {
        super(spawnPos, 25, 100, texture);

    }

    @Override
    public void update(float delta, Vector2 playerPos) {
        Vector2 dir = new Vector2(playerPos.x - x, playerPos.y - y).nor();
        x += dir.x * speed * delta;
        y += dir.y * speed * delta;
        sprite.setPosition(x, y);
    }

    @Override
    public DropItem onDeath() {
        return new DropItem(new Vector2(position));
    }
}
