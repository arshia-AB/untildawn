package com.tilldawn.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.tilldawn.Enum.HeroEnum;
import com.tilldawn.Enum.WeaponEnum;

public class User {
    private String username;
    private String password;
    private String securityAnswer;
    private String avatarPath;
    private HeroEnum heroEnum;
    private WeaponEnum weaponEnum;
    private Weapon weapon;
    private transient Texture playerTexture = new Texture(GameAssetManager.getGameAssetManager().getCharacter1_idle0());
    private transient Sprite playerSprite = new Sprite(playerTexture);

    private float posX;
    private float posY;

    private float playerHP = 100;
    private int score = 0;
    private float time = 0;
    private float Speed = 5;
    private int Elimination = 0;
    private int level = 0;
    private int XP = 0;
    private int xpToNextLevel = 0;
    private int maxLevelXp = 0;


    private boolean isPlayerIdle = true;
    private boolean isPlayerRunning = true;
    private transient CollisionRect rect;

    public int getXP() {
        return XP;
    }

    public void setXP(int XP) {
        this.XP = XP;
    }

    public int getXpToNextLevel() {
        return xpToNextLevel;
    }

    public void setXpToNextLevel(int xpToNextLevel) {
        this.xpToNextLevel = xpToNextLevel;
    }

    public int getMaxLevelXp() {
        return maxLevelXp;
    }

    public void setMaxLevelXp(int maxLevelXp) {
        this.maxLevelXp = maxLevelXp;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getElimination() {
        return Elimination;
    }

    public void setElimination(int elimination) {
        Elimination = elimination;
    }

    public float getPlayerHP() {
        return playerHP;
    }

    public void setPlayerHP(float playerHP) {
        this.playerHP = playerHP;
    }

    public User() {
    }

    public User(String username, String password, String securityAnswer, String avatarPath) {
        this.username = username;
        this.password = password;
        this.securityAnswer = securityAnswer;
        this.avatarPath = avatarPath;

        playerSprite.setPosition((float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight() / 2);

        playerSprite.setSize(playerTexture.getWidth() * 3f, playerTexture.getHeight() * 3f);

        rect = new CollisionRect((float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight(), playerSprite.getWidth(), playerSprite.getHeight());


        this.posX = this.playerSprite.getX();
        this.posY = this.playerSprite.getY();
    }


    public HeroEnum getHeroEnum() {
        return heroEnum;
    }

    public void setHeroEnum(HeroEnum heroEnum) {
        this.heroEnum = heroEnum;
    }

    public WeaponEnum getWeaponEnum() {
        return weaponEnum;
    }

    public void setWeaponEnum(WeaponEnum weaponEnum) {
        this.weaponEnum = weaponEnum;
    }


    public CollisionRect getRect() {
        return rect;
    }

    public void setRect(CollisionRect rect) {
        this.rect = rect;
    }

    public boolean isPlayerIdle() {
        return isPlayerIdle;
    }

    public void setPlayerIdle(boolean playerIdle) {
        isPlayerIdle = playerIdle;
    }

    public boolean isPlayerRunning() {
        return isPlayerRunning;
    }

    public void setPlayerRunning(boolean playerRunning) {
        isPlayerRunning = playerRunning;
    }


    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public float getSpeed() {
        return Speed;
    }

    public void setSpeed(float speed) {
        Speed = speed;
    }

    public float getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    public Texture getPlayerTexture() {
        return playerTexture;
    }

    public void setPlayerTexture(Texture playerTexture) {
        this.playerTexture = playerTexture;
    }

    public Sprite getPlayerSprite() {
        return playerSprite;
    }

    public void setPlayerSprite(Sprite playerSprite) {
        this.playerSprite = playerSprite;
    }

    public HeroEnum getHero() {
        return heroEnum;
    }

    public void setHero(HeroEnum heroEnum) {
        this.heroEnum = heroEnum;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }


}
