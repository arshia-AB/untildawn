package com.tilldawn.Enum;

public enum Weapon {
    REVOLVER("weapons/RevolverStill.png", "REVOLVER", 20, 1, 1, 6),
    SHOTGUN("weapons/T_Shotgun_SS_0.png", "SHOTGUN", 10, 4, 1, 2),
    SMG("weapons/SMGStill.png", "SMG", 8, 1, 2, 24);
    private String path;
    private String name;
    private int damage;
    private int projectTile;
    private int reloadTime;
    private int maxAmmo;

    Weapon(String path, String name, int damage, int projecttile, int reloadTime, int maxAmmo) {
        this.path = path;
        this.name = name;
        this.damage = damage;
        this.projectTile = projecttile;
        this.reloadTime = reloadTime;
        this.maxAmmo = maxAmmo;
    }

    public String getPath() {
        return path;
    }

    public int getDamage() {
        return damage;
    }

    public int getProjectTile() {
        return projectTile;
    }

    public int getReloadTime() {
        return reloadTime;
    }

    public int getMaxAmmo() {
        return maxAmmo;
    }

    public String getName() {
        return name;
    }

    public Weapon getWeaponByName(String name) {
        for (Weapon weapon : Weapon.values()) {
            if (weapon.getName().equals(name)) return weapon;
        }
        return null;
    }
}
