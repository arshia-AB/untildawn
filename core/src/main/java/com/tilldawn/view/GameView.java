package com.tilldawn.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Main;
import com.tilldawn.control.EnemyController;
import com.tilldawn.control.GameController;
import com.tilldawn.model.App;
import com.tilldawn.model.GameAssetManager;
import com.tilldawn.model.User;

public class GameView implements Screen, InputProcessor {

    private Stage stage;
    private GameController controller;
    private OrthographicCamera camera;
    private Texture bgTexture;
    private Texture blackTexture;
    private ShaderProgram grayscaleShader;
    private ShaderProgram radialShader;

    private Skin skin;
    private boolean shiftPressed = false;

    // HUD
    private ProgressBar healthBar;
    private ProgressBar levelProgressBar;
    private Label ammoLabel;
    private Label levelLabel;
    private User player = Main.getApp().getCurrentUser();
    private float survivalTime = 0;

    private int level = player.getLevel();
    private float levelProgress = player.getXP();


    private Label timeLabel;
    private ProgressBar remainingTimeBar;
    private float maxGameTime = player.getGameTime();


    public GameView(GameController controller, Skin skin) {
        this.controller = controller;
        this.skin = skin;
        controller.setView(this);
    }

    @Override
    public void show() {
        ShaderProgram.pedantic = false;
        grayscaleShader = new ShaderProgram(
            Gdx.files.internal("shader/grayscale.vertex.glsl"),
            Gdx.files.internal("shader/grayscale.fragment.glsl")
        );

        radialShader = new ShaderProgram(
            Gdx.files.internal("Light/radialLight.vertex.glsl"),
            Gdx.files.internal("Light/radialLight.fragment.glsl")
        );

        if (!radialShader.isCompiled()) {
            System.err.println(radialShader.getLog());
        }

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        bgTexture = new Texture(Gdx.files.internal("GameBackground.png"));
        bgTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);


        // blackTexture
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(0, 0, 0, 1);
        pixmap.fill();
        blackTexture = new Texture(pixmap);
        pixmap.dispose();

        stage = new Stage(new ScreenViewport());
        setupHUD();

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(multiplexer);
    }

    private void setupHUD() {
        Table table = new Table();
        table.top().left().pad(15);
        table.setFillParent(true);
        stage.addActor(table);

        healthBar = new ProgressBar(0f, 1f, 0.01f, false, skin, "health");
        healthBar.setValue(player.getPlayerHP());
        healthBar.setAnimateDuration(0.2f);

        levelProgressBar = new ProgressBar(0f, 1f, 0.01f, false, skin, "default-horizontal");
        levelProgressBar.setValue(levelProgress);
        levelProgressBar.setAnimateDuration(0.2f);

        ammoLabel = new Label("Ammo: " + player.getWeapon().getAmmo(), skin);
        levelLabel = new Label("Level: " + level, skin);
        remainingTimeBar = new ProgressBar(0f, maxGameTime, 1f, false, skin, "default-horizontal");
        remainingTimeBar.setValue(maxGameTime);
        remainingTimeBar.setAnimateDuration(0.5f);


        table.row().padBottom(10);
        table.add(new Label("Time Left:", skin)).left().padRight(10);
        table.add(remainingTimeBar).colspan(4).width(Gdx.graphics.getWidth() / 2f).left();
        timeLabel = new Label("Time: 0s", skin);

        table.row().padBottom(10);
        table.add(timeLabel).colspan(5).center().expandX();

        table.row().padTop(10);

        table.add(new Label("HP:" + player.getPlayerHP(), skin)).left().padRight(5);
        table.add(healthBar).width(Gdx.graphics.getWidth() / 5f).left().padRight(30);

        table.add(ammoLabel).left().padRight(30);
        table.add(levelLabel).left().padRight(20);
        table.add(levelProgressBar).width(Gdx.graphics.getWidth() / 5f).left();
    }


    @Override
    public void render(float delta) {
        if (Main.getApp().getCurrentUser().getPlayerHP() <= 0) {
            Main.getMain().setScreen(new EndGameScreen(Main.getApp().getCurrentUser(), true));
        }
        ScreenUtils.clear(0, 0, 0, 1);
        survivalTime += delta;
        player.setSurvivalTime(survivalTime);
        float timeLeft = Math.max(0, maxGameTime - survivalTime);
        remainingTimeBar.setValue(timeLeft);
        if (timeLeft <= 0) {
            Main.getMain().setScreen(new EndGameScreen(player, false));
            return;
        }

        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();

        if (App.grayscaleEnabled) {
            Main.getBatch().setShader(grayscaleShader);
        } else {
            Main.getBatch().setShader(null);
        }

        camera.update();
        controller.getPlayerController().centerPlayerOnCamera(camera);

//        camera.position.set(player.getPosX(), player.getPosY(), 0);
//        camera.update();

        Main.getBatch().setProjectionMatrix(camera.combined);

        Main.getBatch().begin();

        float camX = camera.position.x;
        float camY = camera.position.y;

        Main.getBatch().draw(
            bgTexture,
            camX - width / 2f, camY - height / 2f,
            width, height,
            0, 0,
            width / bgTexture.getWidth(), height / bgTexture.getHeight()
        );

        controller.updateGame(delta);
//        controller.getPlayerController().getPlayer().getPlayerSprite().draw(Main.getBatch());///todo in baes mishe dota player bebinam
        controller.getEnemyController().draw(Main.getBatch());

        Main.getBatch().end();


        // Light Shader
        Main.getBatch().setShader(radialShader);
        Main.getBatch().begin();
        radialShader.setUniformf("u_lightPos", new Vector2(0.5f, 0.5f));
        radialShader.setUniformf("u_radius", 0.4f);
        radialShader.setUniformf("u_color", 1f, 1f, 1f, 1f);
        Main.getBatch().draw(blackTexture, camX - width / 2f, camY - height / 2f, width, height);
        Main.getBatch().end();
        Main.getBatch().setShader(null);

        // HUD Update
        healthBar.setValue(player.getPlayerHP() / player.getHero().getHP());
        ammoLabel.setText("Ammo: " + player.getWeapon().getAmmo());
        levelLabel.setText("Level: " + player.getLevel());
        levelProgressBar.setValue(player.getXP() / (float) player.getXpToNextLevel());
//        levelProgressBar.setWidth(Gdx.graphics.getWidth() / 5f);
//        healthBar.setWidth(Gdx.graphics.getWidth() / 5f);
        timeLabel.setText("Time: " + (int) survivalTime + "s");

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        camera.setToOrtho(false, width, height);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        stage.dispose();
        bgTexture.dispose();
        blackTexture.dispose();
        grayscaleShader.dispose();
        radialShader.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.SHIFT_LEFT || keycode == Input.Keys.SHIFT_RIGHT)
            shiftPressed = true;

        if (shiftPressed && keycode == Input.Keys.Q) {
            Main.getMain().setScreen(new PauseMenuView(GameAssetManager.getGameAssetManager().getSkin(), this));
            return true;
        }


        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.SHIFT_LEFT || keycode == Input.Keys.SHIFT_RIGHT)
            shiftPressed = false;
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (player.getWeapon().getAmmo() > 0 && !player.isReloading()) {
            controller.getWeaponController().handleWeaponShoot(screenX, screenY);
            player.getWeapon().setAmmo(Math.max(0, player.getWeapon().getAmmo() - 1));
        }
        if (player.isAutoReload() && player.getWeapon().getAmmo() <= 0) {
            player.setReloading(true);

        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        controller.getWeaponController().handleWeaponRotation(screenX, screenY);
        return true;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
