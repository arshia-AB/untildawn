package com.tilldawn.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Main;
import com.tilldawn.control.GameController;
import com.tilldawn.model.App;
import com.tilldawn.model.GameAssetManager;
import com.tilldawn.model.User;

public class GameView implements Screen, InputProcessor {

    private Stage stage;
    private GameController controller;
    private OrthographicCamera camera;
    private Texture bgTexture;
    private ShaderProgram grayscaleShader;

    private Skin skin;
    private boolean shiftPressed = false;

    // HUD
    private ProgressBar healthBar;
    private ProgressBar levelProgressBar;
    private Label ammoLabel;
    private Label levelLabel;
    private User player = Main.getApp().getCurrentUser();
    private float health = player.getPlayerHP();
    private int ammoLeft = 30;
//    private int ammoLeft = player.getWeapon().getAmmo();
    private int level = player.getLevel();
    private float levelProgress = player.getXP();//todo meghdarsh ro hava set bashe

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
        if (!grayscaleShader.isCompiled()) {
            System.err.println(grayscaleShader.getLog());
        }

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        bgTexture = new Texture(Gdx.files.internal("GameBackground.png"));
        bgTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

        stage = new Stage(new ScreenViewport());

        setupHUD();

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(multiplexer);
    }

    private void setupHUD() {
        Table table = new Table();
        table.bottom().left().pad(15);
        table.setFillParent(true);
        stage.addActor(table);

        healthBar = new ProgressBar(0f, 1f, 0.01f, false, skin, "health");
        healthBar.setValue(health);
        healthBar.setAnimateDuration(0.2f);

        ammoLabel = new Label("Ammo: " + ammoLeft, skin);
        levelLabel = new Label("Level: " + level, skin);

        levelProgressBar = new ProgressBar(0f, 1f, 0.01f, false, skin, "default-horizontal");
        levelProgressBar.setValue(levelProgress);
        levelProgressBar.setAnimateDuration(0.2f);

        table.add(new Label("Health:", skin)).left();
        table.row();
        table.add(healthBar).width(200).padBottom(10).left();
        table.row();
        table.add(ammoLabel).padBottom(5).left();
        table.row();
        table.add(levelLabel).padBottom(5).left();
        table.row();
        table.add(levelProgressBar).width(200).left();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        if (App.grayscaleEnabled) {
            Main.getBatch().setShader(grayscaleShader);
        } else {
            Main.getBatch().setShader(null);
        }

        camera.update();
        controller.getPlayerController().centerPlayerOnCamera(camera);
        Main.getBatch().setProjectionMatrix(camera.combined);

        Main.getBatch().begin();

        float camX = camera.position.x;
        float camY = camera.position.y;
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();

        Main.getBatch().draw(
                bgTexture,
                camX - width / 2f, camY - height / 2f,
                width, height,
                0, 0,
                width / bgTexture.getWidth(), height / bgTexture.getHeight()
        );

        controller.updateGame();
        controller.getPlayerController().getPlayer().getPlayerSprite().draw(Main.getBatch());
        Main.getBatch().end();

        healthBar.setValue(health);
        ammoLabel.setText("Ammo: " + ammoLeft);
        levelLabel.setText("Level: " + level);
        levelProgressBar.setValue(levelProgress);
        levelProgressBar.setWidth(Gdx.graphics.getWidth()/5f);
        healthBar.setWidth(Gdx.graphics.getWidth()/5f);

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
        grayscaleShader.dispose();
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
        if (ammoLeft > 0) {

            controller.getWeaponController().handleWeaponShoot(screenX, screenY);
        }
        ammoLeft = Math.max(0, ammoLeft - 1);
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
