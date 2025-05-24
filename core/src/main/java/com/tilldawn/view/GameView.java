package com.tilldawn.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Main;
import com.tilldawn.control.GameController;
import com.tilldawn.model.App;
import com.tilldawn.model.GameAssetManager;

public class GameView implements Screen, InputProcessor {
    private Stage stage;
    private GameController controller;
    private OrthographicCamera camera;
    private Texture bgTexture;
    private ShaderProgram grayscaleShader;
    private BitmapFont font;
    private boolean shiftPressed = false;

    // HUD values
    private int health = 3;
    private float timeLeft = 120f;
    private int killCount = 0;
    private int ammoLeft = 30;
    private int characterLevel = 1;
    private float levelProgress = 0.5f;

    public GameView(GameController controller, Skin skin) {
        this.controller = controller;
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
        stage = new Stage(new ScreenViewport());

        bgTexture = new Texture(Gdx.files.internal("GameBackground.png"));
        bgTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

        font = new BitmapFont(); // پیش‌فرض
        font.getData().setScale(2);

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        timeLeft -= delta;

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

        // رسم بک‌گراند
        Main.getBatch().draw(
                bgTexture,
                camX - width / 2f, camY - height / 2f,
                width, height,
                0, 0,
                width / bgTexture.getWidth(), height / bgTexture.getHeight()
        );

        controller.updateGame();

        // رسم پلیر
        controller.getPlayerController().getPlayer().getPlayerSprite().draw(Main.getBatch());

        // === HUD پایین صفحه ===
        float hudX = camX - width / 2f + 20;
        float hudY = camY - height / 2f + 150;
        float spacing = 30;

        font.setColor(1, 1, 1, 1);
        font.draw(Main.getBatch(), "HP: " + health, hudX, hudY + spacing * 4);
        font.draw(Main.getBatch(), "Time: " + (int) timeLeft + "s", hudX, hudY + spacing * 3);
        font.draw(Main.getBatch(), "Kills: " + killCount, hudX, hudY + spacing * 2);
        font.draw(Main.getBatch(), "Ammo: " + ammoLeft, hudX, hudY + spacing);
        font.draw(Main.getBatch(), "Level: " + characterLevel, hudX, hudY);

        // نوار پیشرفت
        float barX = hudX;
        float barY = hudY - 40;
        float barWidth = 200;
        float barHeight = 20;

        // بک نوار
        Main.getBatch().setColor(0.3f, 0.3f, 0.3f, 1);
        Main.getBatch().draw(bgTexture, barX, barY, barWidth, barHeight);
        // پر شده
        Main.getBatch().setColor(0f, 1f, 0f, 1);
        Main.getBatch().draw(bgTexture, barX, barY, barWidth * levelProgress, barHeight);
        Main.getBatch().setColor(1, 1, 1, 1); // ریست رنگ

        Main.getBatch().end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
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
        font.dispose();
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
        controller.getWeaponController().handleWeaponShoot(screenX, screenY);
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
