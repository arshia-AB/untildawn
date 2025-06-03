package com.tilldawn.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.tilldawn.Main;
import com.tilldawn.model.Bullet;
import com.tilldawn.model.Enemy;
import com.tilldawn.model.User;
import com.tilldawn.model.Weapon;

import java.util.ArrayList;
import java.util.Iterator;

public class WeaponController {
    private Weapon weapon;
    private ArrayList<Bullet> bullets = new ArrayList<>();

    public WeaponController(Weapon weapon) {
        this.weapon = weapon;
    }

    public void update() {
        weapon.getSprite().draw(Main.getBatch());
        updateBullets(Gdx.graphics.getDeltaTime());

    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public void handleWeaponRotation(int x, int y) {
        Sprite weaponSprite = weapon.getSprite();

        float weaponCenterX = (float) Gdx.graphics.getWidth() / 2;
        float weaponCenterY = (float) Gdx.graphics.getHeight() / 2;

        float angle = (float) Math.atan2(y - weaponCenterY, x - weaponCenterX);

        weaponSprite.setRotation((float) (3.14 - angle * MathUtils.radiansToDegrees));
    }

    public void handleWeaponShoot(int x, int y) {
//        int correctedY = Gdx.graphics.getHeight() - y;
//
//        bullets.add(new Bullet(x, correctedY));
        int correctedY = Gdx.graphics.getHeight() - y;

        Vector2 center = new Vector2(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);
        Vector2 targetPos = new Vector2(x, correctedY);
        Vector2 baseDir = targetPos.sub(center).nor();

        int projectileCount = Main.getApp().getCurrentUser().getWeapon().getWeaponEnum().getProjectTile();
        float spreadAngle = 10f;
        int mid = projectileCount / 2;

        for (int i = 0; i < projectileCount; i++) {
            float angleOffset = (i - mid) * spreadAngle;
            if (projectileCount % 2 == 0) {
                angleOffset += spreadAngle / 2f;
            }

            Vector2 rotatedDir = new Vector2(baseDir).rotateDeg(angleOffset);

            Vector2 offsetTarget = new Vector2(center).add(rotatedDir.scl(1000));

            bullets.add(new Bullet((int) offsetTarget.x, (int) offsetTarget.y));
        }
    }

    public void updateBullets(float delta) {
        Iterator<Bullet> it = bullets.iterator();
        while (it.hasNext()) {
            Bullet b = it.next();
            b.update(delta);
            b.render(Main.getBatch());

            if (b.getSprite().getX() < 0 || b.getSprite().getX() > Gdx.graphics.getWidth()
                || b.getSprite().getY() < 0 || b.getSprite().getY() > Gdx.graphics.getHeight()) {
                it.remove();
            }
        }


    }

    public Sprite getWaponSprite() {
        return weapon.getSprite();
    }
}
