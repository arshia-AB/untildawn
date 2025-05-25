package com.tilldawn.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class TentacleMonster extends Enemy {
    private static final Texture texture = new Texture("enemy/T_TentacleEnemy_0.png");

    public TentacleMonster(Vector2 spawnPos) {
        super(spawnPos, 25, 100, texture);
    }

    @Override
    public DropItem onDeath() {
        return new DropItem(new Vector2(position));
    }
}
