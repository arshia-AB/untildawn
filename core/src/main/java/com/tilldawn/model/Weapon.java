package com.tilldawn.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.tilldawn.Enum.WeaponEnum;

public class Weapon {
    private Sprite sprite;
    private int ammo;
    private WeaponEnum weaponEnum;


    public Weapon(WeaponEnum weaponEnum) {
        this.weaponEnum = weaponEnum;

        sprite = new Sprite(new Texture(weaponEnum.getPath()));
        sprite.setX((float) Gdx.graphics.getWidth() / 2);
        sprite.setY((float) Gdx.graphics.getHeight() / 2);
        sprite.setSize(50, 50);
        ammo = weaponEnum.getMaxAmmo();

    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public int getAmmo() {
        return ammo;
    }

    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }

    public WeaponEnum getWeaponEnum() {
        return weaponEnum;
    }


    public void setWeaponEnum(WeaponEnum weaponEnum) {
        this.weaponEnum = weaponEnum;
    }
}
