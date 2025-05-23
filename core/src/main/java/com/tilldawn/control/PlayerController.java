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
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            player.setPosY(player.getPosY() - player.getSpeed());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.setPosX(player.getPosX() - player.getSpeed());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            player.setPosY(player.getPosY() + player.getSpeed());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            player.setPosX(player.getPosX() + player.getSpeed());
            player.getPlayerSprite().flip(true, false);
        }
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

    public User getPlayer() {
        return player;
    }

    public void setPlayer(User player) {
        this.player = player;
    }
}
