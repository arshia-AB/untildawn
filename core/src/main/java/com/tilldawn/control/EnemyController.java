package com.tilldawn.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.tilldawn.model.DropItem;
import com.tilldawn.model.Enemy;
import com.tilldawn.model.TentacleMonster;

import java.util.ArrayList;
import java.util.Iterator;

public class EnemyController {
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private ArrayList<DropItem> drops = new ArrayList<>();

    private float spawnTimer = 0;
    private float spawnRate = 3f;
    private float spawnAcceleration = 0.05f;

    public void update(float delta, Vector2 playerPos, SpriteBatch batch) {
        Iterator<Enemy> it = enemies.iterator();
        while (it.hasNext()) {
            Enemy e = it.next();
            e.update(delta, playerPos);
            e.render(batch);

            if (!e.isAlive()) {
                drops.add(e.onDeath());
                it.remove();
            }
        }


        Texture dropTexture = new Texture("Chest_0.png");

        for (DropItem d : drops) {
            d.render(batch, dropTexture);
        }

        spawnTimer += delta;
        if (spawnTimer >= spawnRate) {
            spawnTimer = 0;
            spawnEnemy();
            spawnRate = Math.max(0.8f, spawnRate - spawnAcceleration);
        }
    }

    private void spawnEnemy() {
        Vector2 spawnPos = getRandomEdgeSpawn();
        enemies.add(new TentacleMonster(spawnPos));
    }

    private Vector2 getRandomEdgeSpawn() {
        int edge = MathUtils.random(3);
        float x = 0, y = 0;

        switch (edge) {
            case 0:
                x = MathUtils.random(Gdx.graphics.getWidth());
                y = Gdx.graphics.getHeight();
                break;
            case 1:
                x = MathUtils.random(Gdx.graphics.getWidth());
                y = -32;
                break;
            case 2:
                x = -32;
                y = MathUtils.random(Gdx.graphics.getHeight());
                break;
            case 3:
                x = Gdx.graphics.getWidth();
                y = MathUtils.random(Gdx.graphics.getHeight());
                break;
        }

        return new Vector2(x, y);
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public ArrayList<DropItem> getDrops() {
        return drops;
    }
}
