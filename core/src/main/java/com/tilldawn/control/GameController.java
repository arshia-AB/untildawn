package com.tilldawn.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.tilldawn.Enum.WeaponEnum;
import com.tilldawn.Main;
import com.tilldawn.model.User;
import com.tilldawn.model.Weapon;
import com.tilldawn.view.GameView;

import java.awt.*;

public class GameController {
    private GameView view;
    private PlayerController playerController;
    private WorldController worldController;
    private WeaponController weaponController;
    private EnemyController enemyController;


    public void setView(GameView view) {
        this.view = view;
        playerController = new PlayerController(Main.getApp().getCurrentUser());
        worldController = new WorldController(playerController);
        weaponController = new WeaponController(Main.getApp().getCurrentUser().getWeapon());
        enemyController = new EnemyController();
    }

    public void updateGame(float delta) {
        if (view != null) {
            worldController.update();
            playerController.update();
            weaponController.update();
            enemyController.update(delta, new Vector2(weaponController.getWaponSprite().getX(), weaponController.getWaponSprite().getY()), weaponController.getBullets(), playerController.getPlayer().getWeaponEnum());
        }
    }


    public PlayerController getPlayerController() {
        return playerController;
    }

    public WeaponController getWeaponController() {
        return weaponController;
    }

    public WorldController getWorldController() {
        return worldController;
    }

    public EnemyController getEnemyController() {
        return enemyController;
    }
}
