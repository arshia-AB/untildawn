package com.tilldawn.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class BossDashEnemy extends Enemy {

    private float dashCooldown = 5f;
    private float dashTimer = 0f;

    private boolean isDashing = false;
    private float dashDuration = 0.3f;
    private float dashTimeLeft = 0f;

    private Vector2 dashVelocity = new Vector2();

    public BossDashEnemy(float x, float y) {
        super(x, y, 400, 100, new Texture("enemy/ElderBrain.png"));
    }

    @Override
    public void update(float delta, Vector2 playerPos) {
        if (isDying) {
            deathTimer += delta;
            if (deathAnimation.isAnimationFinished(deathTimer)) isDead = true;
            return;
        }

        if (knockbackTimer > 0) {
            knockbackTimer -= delta;
            x += knockbackVelocity.x * delta;
            y += knockbackVelocity.y * delta;
        } else {
            if (isDashing) {
                dashTimeLeft -= delta;
                x += dashVelocity.x * delta;
                y += dashVelocity.y * delta;

                if (dashTimeLeft <= 0f) {
                    isDashing = false;
                    dashVelocity.setZero();
                    dashTimer = 0f;
                }
            } else {
                dashTimer += delta;
                if (dashTimer >= dashCooldown) {
                    isDashing = true;
                    dashTimeLeft = dashDuration;

                    Vector2 direction = new Vector2(playerPos).sub(getCenter()).nor();
                    dashVelocity = direction.scl(600f);
                } else {
                    Vector2 move = new Vector2(playerPos).sub(getCenter()).nor().scl(100f * delta);
                    x += move.x;
                    y += move.y;
                }
            }
        }

        sprite.setPosition(x, y);
    }

    private Vector2 getCenter() {
        return new Vector2(x + sprite.getWidth() / 2f, y + sprite.getHeight() / 2f);
    }
}
