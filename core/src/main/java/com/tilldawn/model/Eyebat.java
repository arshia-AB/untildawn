package com.tilldawn.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Eyebat extends Enemy {

    private float shootCooldown = 3f;
    private float shootTimer = 0f;
    private ArrayList<EnemyBullet> bullets = new ArrayList<>();

    public ArrayList<EnemyBullet> getBullets() {
        return bullets;
    }

    public Eyebat(float x, float y) {
        super(x, y, 50, 30, new Texture("enemy/T_EyeBat_0.png"));
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
        if (knockbackTimer > 0) {
            float dt = Math.min(delta, knockbackTimer);
            knockbackTimer -= dt;

            x += knockbackVelocity.x * dt;
            y += knockbackVelocity.y * dt;

            sprite.setPosition(x, y);
        }
        Vector2 dir = new Vector2(playerPos.x - x, playerPos.y - y).nor();
        x += dir.x * speed * delta;
        y += dir.y * speed * delta;
        sprite.setPosition(x, y);

        shootTimer += delta;
        if (shootTimer >= shootCooldown) {
            shootTimer = 0f;
            shootAtPlayer(playerPos);
        }
        for (EnemyBullet bullet : bullets) {
            bullet.update(delta);
        }
    }

    private void shootAtPlayer(Vector2 playerPos) {
        Vector2 start = new Vector2(x + sprite.getWidth() / 2, y + sprite.getHeight() / 2);
        Vector2 target = new Vector2(playerPos);
        bullets.add(new EnemyBullet(start, target));
    }
}
