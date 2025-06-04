package com.tilldawn.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.tilldawn.Enum.WeaponEnum;
import com.tilldawn.Main;
import com.tilldawn.model.GameAssetManager;
import com.tilldawn.model.User;

public class PlayerController {
    private User player;
    private float reloadTimer = 0;

    public PlayerController(User player) {
        this.player = player;
    }

    public void update() {
        player.getPlayerSprite().draw(Main.getBatch());
        float delta = Gdx.graphics.getDeltaTime();

        player.updateHit(delta);

        if (player.isReloading()) {
            reloadAnimation();
        } else {
            idleAnimation();
        }

        handlePlayerInput();

    }


    public void handlePlayerInput() {
        float speed = player.getSpeed();
        Vector2 movement = new Vector2(0, 0);

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            movement.y += 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            movement.y -= 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            movement.x -= 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            movement.x += 1;
        }

        if (!movement.isZero()) {
            movement.nor().scl(speed);

            float x = player.getPosX() + movement.x;
            float y = player.getPosY() + movement.y;

            player.setPosX(x);
            player.setPosY(y);
            player.setPosition(new Vector2(x, y));
            player.updatePosition();

            if (movement.x < 0) {
                player.getPlayerSprite().setFlip(true, false);
            } else if (movement.x > 0) {
                player.getPlayerSprite().setFlip(false, false);
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.R) && !player.isReloading()) {
            player.setReloading(true);
            reloadTimer = 0;
        }
    }


    public void centerPlayerOnCamera(OrthographicCamera camera) {
//        camera.position.set(player.getPosition().x, player.getPosition().y, 0);
        Sprite sprite = player.getPlayerSprite();
        float centerX = camera.position.x - sprite.getWidth() / 2f;
        float centerY = camera.position.y - sprite.getHeight() / 2f;
        sprite.setPosition(centerX, centerY);
    }
//    public void centerPlayerOnCamera(OrthographicCamera camera) {
//        float centerX = camera.position.x - player.getPlayerSprite().getWidth() / 2f;
//        float centerY = camera.position.y - player.getPlayerSprite().getHeight() / 2f;
//
//        player.setPosX(centerX);
//        player.setPosY(centerY);
//        player.updatePosition();
//    }


    public void idleAnimation() {
        Animation<Texture> animation = GameAssetManager.getGameAssetManager().getCharacter1_idle_animation();

        player.getPlayerSprite().setRegion(animation.getKeyFrame(player.getTime()));

        if (!animation.isAnimationFinished(player.getTime())) {
            player.setTime(player.getTime() + Gdx.graphics.getDeltaTime());
        } else {
            player.setTime(0);
        }

        animation.setPlayMode(Animation.PlayMode.LOOP);
    }

//
//    public void handleReload() {
//        reloadTimer += Gdx.graphics.getDeltaTime();
//
//        if (reloadTimer >= 1.0f) {
//            reloadTimer = 0;
//            player.setReloading(false);
//
//            player.getWeapon().setAmmo(player.getWeapon().getWeaponEnum().getMaxAmmo());
//        }
//    }

    public void reloadAnimation() {
        Animation<Texture> reloadAnim = GameAssetManager.getGameAssetManager().getCharacter1_reload_anim();

        player.getPlayerSprite().setRegion(reloadAnim.getKeyFrame(player.getTime()));

        if (!reloadAnim.isAnimationFinished(player.getTime())) {
            player.setTime(player.getTime() + Gdx.graphics.getDeltaTime());
        } else {
            player.setTime(0);
            player.setReloading(false);
            player.getWeapon().setAmmo(player.getWeapon().getWeaponEnum().getMaxAmmo());
        }

        reloadAnim.setPlayMode(Animation.PlayMode.NORMAL);
    }

    public User getPlayer() {
        return player;
    }

    public void setPlayer(User player) {
        this.player = player;
    }
}
