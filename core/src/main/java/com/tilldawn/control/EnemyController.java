package com.tilldawn.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.tilldawn.Enum.WeaponEnum;
import com.tilldawn.Main;
import com.tilldawn.model.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EnemyController {

    private List<Enemy> enemies = new ArrayList<>();
    private float tentacleSpawnTimer = 0f;
    private float eyebatSpawnTimer = 0f;
    private float totalGameTime = 0f;

    public void update(float delta, Vector2 playerPos, ArrayList<Bullet> bullets, WeaponEnum weaponEnum) {
        totalGameTime += delta;


        // Tentacle spawn logic
        tentacleSpawnTimer += delta;
        float tentacleSpawnRate = Math.max(3, 30 - totalGameTime);
        if (tentacleSpawnTimer >= tentacleSpawnRate) {
            tentacleSpawnTimer = 0;
            enemies.add(new TentacleMonster(randomX(), randomY()));
        }


        // Eyebat spawn logic
        if (totalGameTime > 4) {
            eyebatSpawnTimer += delta;
            float eyebatSpawnRate = Math.max(10, 10 - (totalGameTime - 4));
            if (eyebatSpawnTimer >= eyebatSpawnRate) {
                eyebatSpawnTimer = 0;
                enemies.add(new Eyebat(randomX(), randomY()));
            }
        }
        for (Bullet bullet : bullets) {
            if (!bullet.isAlive()) continue;

            for (Enemy enemy : enemies) {
                if (bullet.getRect().collideswith(enemy.getRect())) {
                    enemy.takeDamage(weaponEnum.getDamage());
                    bullet.setAlive(false);
                    break;
                }
            }
        }

        bullets.removeIf(b -> !b.isAlive());
        // Update enemies
        for (Enemy enemy : enemies) {
            enemy.update(delta, playerPos);
        }

        // Remove dead enemies
        enemies.removeIf(Enemy::isDead);
    }

    public void draw(SpriteBatch batch) {
        for (Enemy enemy : enemies) {
            enemy.draw(batch);
        }
    }

    private float randomX() {
        return MathUtils.random(0, Gdx.graphics.getWidth());
    }

    private float randomY() {
        return MathUtils.random(0, Gdx.graphics.getHeight());
    }
}

