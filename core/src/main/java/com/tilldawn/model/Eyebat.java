package com.tilldawn.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Eyebat extends Enemy {

    private float shootCooldown = 3f;
    private float shootTimer = 0f;

    public Eyebat(float x, float y) {
        super(x, y, 50, 30, new Texture("enemy/T_EyeBat_0.png"));
    }

    @Override
    public void update(float delta, Vector2 playerPos) {
        Vector2 dir = new Vector2(playerPos.x - x, playerPos.y - y).nor();
        x += dir.x * speed * delta;
        y += dir.y * speed * delta;
        sprite.setPosition(x, y);

        shootTimer += delta;
        if (shootTimer >= shootCooldown) {
            shootTimer = 0f;
            shootAtPlayer(playerPos);
        }
    }

    private void shootAtPlayer(Vector2 playerPos) {

    }
}
