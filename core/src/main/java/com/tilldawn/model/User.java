package com.tilldawn.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.tilldawn.Enum.Ability;
import com.tilldawn.Enum.HeroEnum;
import com.tilldawn.Enum.WeaponEnum;
import com.tilldawn.Main;
import com.tilldawn.view.GameView;

public class User {
    private String username;
    private String password;
    private String securityAnswer;
    private String avatarPath;
    private HeroEnum heroEnum;
    private WeaponEnum weaponEnum;
    private Weapon weapon;
    private transient Texture playerTexture = new Texture(GameAssetManager.getGameAssetManager().getCharacter1_idle0());

    private Ability ability;

    private transient Sprite playerSprite = new Sprite(playerTexture);

    private float posX;
    private float posY;

    private float playerHP;
    private int score = 0;
    private float time = 0;
    private float survivalTime;
    private float Speed = 5f;
    private int Elimination = 0;
    private int level = 1;
    private int XP;
    private int xpToNextLevel = 20 * level;
    private int maxLevelXp = 0;
    private int GameTime;


    private boolean isPlayerIdle = true;
    private boolean isPlayerRunning = true;

    private transient CollisionRect rect;

    private float ReloadTime = 1f;
    private boolean IsReloading = false;
    private boolean autoReload = false;
    private Vector2 position = new Vector2();
    private boolean invincible = false;

    public Ability getAbility() {
        return ability;
    }

    public void increaseLevel() {
        this.level++;



    }


    public void setAbility(Ability ability) {
        this.ability = ability;
    }

    public boolean isInvincible() {
        return invincible;
    }

    public void setInvincible(boolean invincible) {
        this.invincible = invincible;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public int getGameTime() {
        return GameTime;
    }

    public void setGameTime(int gameTime) {
        GameTime = gameTime;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void takeDamage(int damage) {
        this.playerHP -= damage;
        if (this.playerHP < 0) this.playerHP = 0;

    }

    public float getSurvivalTime() {
        return survivalTime;
    }

    public void setSurvivalTime(float survivalTime) {
        this.survivalTime = survivalTime;
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
        this.position.set(posX, posY);
    }

    public boolean isAutoReload() {
        return autoReload;
    }

    public void setAutoReload(boolean autoReload) {
        this.autoReload = autoReload;
    }

    public boolean isReloading() {
        return IsReloading;
    }

    public void setReloading(boolean reloading) {
        IsReloading = reloading;
    }

    public float getReloadTime() {
        return ReloadTime;
    }

    public void setReloadTime(float reloadTime) {
        ReloadTime = reloadTime;
    }

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


    public void updatePosition() {
        playerSprite.setPosition(posX, posY);


        rect.move(posX, posY);
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
        this.position.x = posX;


    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;
        this.position.y = posY;

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
