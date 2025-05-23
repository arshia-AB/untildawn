package com.tilldawn.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tilldawn.control.GameController;
import com.tilldawn.Main;

public class GameView implements Screen, InputProcessor {
    private Stage stage;
    private GameController controller;
    private OrthographicCamera camera;
    private long startTimeMillis;
    private Texture bgTexture;

    public GameView(GameController controller, Skin skin) {
        this.controller = controller;
        controller.setView(this);
    }

    @Override
    public void show() {

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        stage = new Stage(new ScreenViewport());
        bgTexture = new Texture(Gdx.files.internal("GameBackground.png"));
        bgTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        camera.update();
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

        Main.getBatch().end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {

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
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        controller.getWeaponController().handleWeaponShoot(screenX, screenY);
        return false;
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
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
