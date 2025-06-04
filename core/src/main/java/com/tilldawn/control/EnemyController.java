package com.tilldawn.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.tilldawn.Enum.WeaponEnum;
import com.tilldawn.Main;
import com.tilldawn.model.*;
import com.tilldawn.view.AbilitySelectScreen;
import com.tilldawn.view.GameView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EnemyController {

    private List<Enemy> enemies = new ArrayList<>();
    private float tentacleSpawnTimer = 0f;
    private float eyebatSpawnTimer = 14f;
    private float totalGameTime = 0f;
    private User player = Main.getApp().getCurrentUser();
    private int t = player.getGameTime();
    private float InvincibleTimer = 0f;


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
//        if (totalGameTime > t / 4f) {
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
                    enemy.applyKnockback(bullet.getPosition());
                    if (enemy.isDead()) {

                        player.setElimination(player.getElimination() + 1);
                        player.setXP(player.getXP() + 3);
                        if (player.getXP() > player.getXpToNextLevel()) {
                            player.setXP(0);
                            player.increaseLevel();
                            player.setXpToNextLevel(player.getLevel() * 20);
                        }

                    }
                    bullet.setAlive(false);
                    break;
                }
            }
        }
        // Enemy hit player
        for (Enemy enemy : enemies) {
            if (enemy.getRect().collideswith(player.getRect()) && !player.isInvincible()) {
                Main.getApp().getCurrentUser().takeDamage(1);
                enemy.setHp(0);
                player.setElimination(player.getElimination() + 1);

                player.setInvincible(true);
                InvincibleTimer += delta;
            }
        }
        if (InvincibleTimer >= 1f) {
            InvincibleTimer = 0;
            player.setInvincible(false);
        }

        bullets.removeIf(b -> !b.isAlive());
        // Update enemies
        for (Enemy enemy : enemies) {
            enemy.update(delta, playerPos);
        }
// Enemy bullets hit player
        for (Enemy enemy : enemies) {
            if (enemy instanceof Eyebat eyebat) {
                Iterator<EnemyBullet> it = eyebat.getBullets().iterator();
                while (it.hasNext()) {
                    EnemyBullet bullet = it.next();
                    bullet.update(delta);

                    if (bullet.getRect().collideswith(Main.getApp().getCurrentUser().getRect())) {
                        Main.getApp().getCurrentUser().takeDamage(1);
                        player.onHit();
                        it.remove();
                    }
                }
            }
        }
        // Remove dead enemies
        enemies.removeIf(Enemy::isAnimationDead);
    }

    public void draw(SpriteBatch batch) {
        for (Enemy enemy : enemies) {
            enemy.draw(batch);

            if (enemy instanceof Eyebat) {
                Eyebat eyebat = (Eyebat) enemy;
                for (EnemyBullet bullet : eyebat.getBullets()) {
                    bullet.render(batch);
                }
            }
        }
    }

    private float randomX() {
        return MathUtils.random(0, Gdx.graphics.getWidth());
    }

    private float randomY() {
        return MathUtils.random(0, Gdx.graphics.getHeight());
    }

    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }
}

