package com.tilldawn.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.tilldawn.Main;
import com.tilldawn.model.Bullet;
import com.tilldawn.model.Enemy;
import com.tilldawn.model.User;
import com.tilldawn.model.Weapon;
import com.tilldawn.view.GameView;

import java.util.ArrayList;
import java.util.Iterator;

public class WeaponController {
    private Weapon weapon;
    private ArrayList<Bullet> bullets = new ArrayList<>();
    private GameView gameview;

    public WeaponController(Weapon weapon, GameView gameView) {
        this.weapon = weapon;
        this.gameview = gameView;
    }

    public void update() {
        updateWeaponPosition();
        weapon.getSprite().draw(Main.getBatch());
        updateBullets(Gdx.graphics.getDeltaTime());

    }

    private void updateWeaponPosition() {
        User player = Main.getApp().getCurrentUser();
        Vector2 playerPos = player.getPosition();


        float offsetX = 20f;
        float offsetY = 10f;

        weapon.getSprite().setPosition(playerPos.x + offsetX, playerPos.y + offsetY);
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
        Vector3 screenCoords = new Vector3(x, y, 0);
        Vector3 worldCoords3D = gameview.getCamera().unproject(screenCoords);
        Vector2 targetPos = new Vector2(worldCoords3D.x, worldCoords3D.y);

        Vector2 weaponPos = new Vector2(weapon.getSprite().getX(), weapon.getSprite().getY());

        Vector2 baseDir = new Vector2(targetPos).sub(weaponPos).nor();

        int projectileCount = Main.getApp().getCurrentUser().getWeapon().getWeaponEnum().getProjectTile();
        float spreadAngle = 10f;
        int mid = projectileCount / 2;

        for (int i = 0; i < projectileCount; i++) {
            float angleOffset = (i - mid) * spreadAngle;
            if (projectileCount % 2 == 0) {
                angleOffset += spreadAngle / 2f;
            }

            Vector2 rotatedDir = new Vector2(baseDir).rotateDeg(angleOffset);
            Vector2 offsetTarget = new Vector2(weaponPos).add(rotatedDir.cpy().scl(1000)); // کپی مهمه

            bullets.add(new Bullet((int) offsetTarget.x, (int) offsetTarget.y, weaponPos));
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
