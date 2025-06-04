package com.tilldawn.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;


public class TentacleMonster extends Enemy {

    public TentacleMonster(float x, float y) {
        super(x, y, 25, 50, new Texture("enemy/T_TentacleEnemy_0.png"));
    }

    @Override
    public void update(float delta, Vector2 playerPos) {
        if (isDying) {
            deathTimer += delta;
            if (deathAnimation.isAnimationFinished(deathTimer)) {
                isDead = true;
            }
            return;
        }
        Vector2 dir = new Vector2(playerPos.x - x, playerPos.y - y).nor();
        x += dir.x * speed * delta;
        y += dir.y * speed * delta;
        sprite.setPosition(x, y);
    }
}


