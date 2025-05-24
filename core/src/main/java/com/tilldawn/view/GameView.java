package com.tilldawn.view;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tilldawn.control.GameController;
import com.tilldawn.Main;
import com.tilldawn.model.App;
import com.tilldawn.model.GameAssetManager;
import com.tilldawn.model.User;


public class GameView implements Screen, InputProcessor {
    private Stage stage;
    private GameController controller;
    private OrthographicCamera camera;
    private long startTimeMillis;
    private Texture bgTexture;
    private boolean shiftPressed = false;
    private ShaderProgram grayscaleShader;
    //label hay info
    private Label healthLabel, ammoLabel, killsLabel, timeLabel, levelLabel;
    private ProgressBar xpProgressBar;
    private Table HUDTable;
    private User player;

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
        Gdx.input.setInputProcessor(this);
        player = Main.getApp().getCurrentUser();

        BitmapFont font = new BitmapFont();
        healthLabel = new Label("HP: " + player.getPlayerHP(), new Label.LabelStyle(font, Color.RED));
        ammoLabel = new Label("Ammo: " + player.getWeapon().getAmmo(), new Label.LabelStyle(font, Color.WHITE));
        killsLabel = new Label("Kills: " + player.getElimination(), new Label.LabelStyle(font, Color.WHITE));
        timeLabel = new Label("Time: 00:00", new Label.LabelStyle(font, Color.WHITE));
        levelLabel = new Label("Level: " + player.getLevel(), new Label.LabelStyle(font, Color.GOLD));

        ProgressBar.ProgressBarStyle barStyle = new ProgressBar.ProgressBarStyle();
        Pixmap bg = new Pixmap(200, 20, Pixmap.Format.RGBA8888);
        bg.setColor(Color.GRAY);
        bg.fill();
        barStyle.background = new TextureRegionDrawable(new Texture(bg));

        Pixmap knob = new Pixmap(1, 20, Pixmap.Format.RGBA8888);
        knob.setColor(Color.GREEN);
        knob.fill();
        barStyle.knobBefore = new TextureRegionDrawable(new Texture(knob));

        xpProgressBar = new ProgressBar(0f, player.getXpToNextLevel(), 1f, false, barStyle);
        xpProgressBar.setValue(player.getXP() - player.getMaxLevelXp());

        HUDTable = new Table();
        HUDTable.top().left();
        HUDTable.setFillParent(true);
        HUDTable.add(healthLabel).pad(10).padRight(25).left();
        HUDTable.add(ammoLabel).pad(10).padRight(25).left();
        HUDTable.add(killsLabel).pad(10).padRight(25).left();
        HUDTable.add(levelLabel).pad(10).padRight(25).left();
        HUDTable.add(xpProgressBar).pad(10).padRight(25).left();
        HUDTable.add(timeLabel).pad(10).expandX().right();
        stage.addActor(HUDTable);

        startTimeMillis = System.currentTimeMillis();

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        if (App.grayscaleEnabled) {
            Main.getBatch().setShader(grayscaleShader);
        } else {
            Main.getBatch().setShader(null);
        }
        ammoLabel.setText("Ammo: " + player.getWeapon().getAmmo());
        killsLabel.setText("Kills: " + player.getElimination());
        healthLabel.setText("HP: " + player.getPlayerHP());
        levelLabel.setText("Level: " + player.getLevel());
        xpProgressBar.setRange(0, player.getXpToNextLevel());
        xpProgressBar.setValue(player.getXP() - player.getMaxLevelXp());

        int totalGameTimeInSeconds = Main.getApp().getTimePassed() * 60;
        int elapsedSeconds = (int) ((System.currentTimeMillis() - startTimeMillis) / 1000);
        int remainingSeconds = Math.max(0, totalGameTimeInSeconds - elapsedSeconds);
        int minutes = remainingSeconds / 60;
        int seconds = remainingSeconds % 60;
        timeLabel.setText(String.format("Time: %02d:%02d", minutes, seconds));
        camera.update();
        controller.getPlayerController().centerPlayerOnCamera(camera);
        Main.getBatch().setProjectionMatrix(camera.combined);
        Main.getBatch().begin();
        controller.getPlayerController().getPlayer().getPlayerSprite().draw(Main.getBatch());

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
        grayscaleShader.dispose();

    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.SHIFT_LEFT || keycode == Input.Keys.SHIFT_RIGHT) {
            shiftPressed = true;
        }

        if (shiftPressed && keycode == Input.Keys.Q) {
            Main.getMain().setScreen(new PauseMenuView(GameAssetManager.getGameAssetManager().getSkin(), this));
            return true;
        }
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
