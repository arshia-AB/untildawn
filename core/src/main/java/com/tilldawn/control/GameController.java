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
    private AbilityController abilityController;


    public void setView(GameView view) {
        this.view = view;
        playerController = new PlayerController(Main.getApp().getCurrentUser());
        worldController = new WorldController(playerController);
        weaponController = new WeaponController(Main.getApp().getCurrentUser().getWeapon());
        enemyController = new EnemyController();
        abilityController = new AbilityController(Main.getApp().getCurrentUser());
    }

    public void updateGame(float delta) {
        if (view != null) {
            worldController.update();
            playerController.update();
            weaponController.update();
            abilityController.update(delta);
            enemyController.update(delta, playerController.getPlayer().getPosition(), weaponController.getBullets(), playerController.getPlayer().getWeaponEnum());
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
