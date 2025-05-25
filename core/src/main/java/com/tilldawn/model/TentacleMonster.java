package com.tilldawn.model;

import com.badlogic.gdx.math.Vector2;

public class TentacleMonster extends Enemy {

    public TentacleMonster(Vector2 spawnPos) {
        super(spawnPos, 25, 100); // HP: 25, speed: 100
    }

    @Override
    public DropItem onDeath() {
        return new DropItem(new Vector2(position));
    }
}
