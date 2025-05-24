package com.tilldawn.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.tilldawn.Main;
import com.tilldawn.model.GameAssetManager;
import com.tilldawn.model.User;

public class PlayerController {
    private User player;

    public PlayerController(User player) {
        this.player = player;
    }

    public void update() {
        player.getPlayerSprite().draw(Main.getBatch());

        if (player.isPlayerIdle()) {
            idleAnimation();
        }
        handlePlayerInput();

    }


    public void handlePlayerInput() {
        float speed = player.getSpeed();
        float x = player.getPosX();
        float y = player.getPosY();

        if (Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.A)) {
            x += speed;
            y -= speed;
            player.getPlayerSprite().setFlip(true, false);
        } else if (Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.D)) {
            x -= speed;
            y -= speed;
            player.getPlayerSprite().setFlip(false, false);
        } else if (Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.A)) {
            x += speed;
            y += speed;
            player.getPlayerSprite().setFlip(true, false);
        } else if (Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.D)) {
            x -= speed;
            y += speed;
            player.getPlayerSprite().setFlip(false, false);
        } else {
            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                y -= speed;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                y += speed;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                x += speed;
                player.getPlayerSprite().setFlip(true, false);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                x -= speed;
                player.getPlayerSprite().setFlip(false, false);
            }
        }

        player.setPosX(x);
        player.setPosY(y);


    }

    public void centerPlayerOnCamera(OrthographicCamera camera) {
        Sprite sprite = player.getPlayerSprite();
        float centerX = camera.position.x - sprite.getWidth() / 2f;
        float centerY = camera.position.y - sprite.getHeight() / 2f;
        sprite.setPosition(centerX, centerY);
    }


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

    public void reloadAnimation() {
        Animation<Texture> reloadAnim = GameAssetManager.getGameAssetManager().getCharacter1_reload_anim();

        player.getPlayerSprite().setRegion(reloadAnim.getKeyFrame(player.getReloadTime()));

        if (!reloadAnim.isAnimationFinished(player.getReloadTime())) {
            player.setReloadTime(player.getReloadTime() + Gdx.graphics.getDeltaTime());
        } else {
            player.setReloadTime(0);
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
