package com.tilldawn.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Enum.Hero;
import com.tilldawn.Enum.Weapon;
import com.tilldawn.Main;
import com.tilldawn.control.MainMenuController;
import com.tilldawn.control.PreGameMenuController;
import com.tilldawn.model.GameAssetManager;


public class PreGameMenuView implements Screen {
    private Stage stage;
    private Skin skin;
    private Hero selectedHero;
    private Weapon selectedWeapon;
    private int selectedTime;
    private Image selectedHeroImage;
    private Image selectedGunImage;

    public PreGameMenuView(PreGameMenuController controller, Skin skin) {
        this.skin = skin;
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        Texture bgTexture = new Texture("backgrounds/25.png");
        Image background = new Image(bgTexture);
        background.setFillParent(true);
        stage.addActor(background);

        Table mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.center().padRight(500);

        selectedHeroImage = new Image();
        selectedGunImage = new Image();

        Table gunPreviewTable = new Table();
        gunPreviewTable.add(new Label("Selected Gun", skin)).row();
        gunPreviewTable.add(selectedGunImage).size(200).pad(10);

        Table heroPreviewTable = new Table();
        heroPreviewTable.add(new Label("Selected Hero", skin)).row();
        heroPreviewTable.add(selectedHeroImage).size(200).pad(10);


        mainTable.add(new Label("Choose Hero", skin)).row();
        Table heroTable = new Table();
        for (Hero hero : Hero.values()) {
            Texture texture = new Texture(Gdx.files.internal(hero.getPath()));
            Image image = new Image(texture);
            image.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    selectedHero = hero;
                    selectedHeroImage.setDrawable(image.getDrawable());
                }
            });
            heroTable.add(image).size(64).pad(10);
        }
        mainTable.add(heroTable).row();

        mainTable.add(new Label("Choose Weapon", skin)).row();
        Table weaponTable = new Table();
        for (Weapon weapon : Weapon.values()) {
            Texture texture = new Texture(Gdx.files.internal(weapon.getPath()));
            Image image = new Image(texture);
            image.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    selectedWeapon = weapon;
                    selectedGunImage.setDrawable(image.getDrawable());
                }
            });
            weaponTable.add(image).size(64).pad(10);
        }
        mainTable.add(weaponTable).row();

        mainTable.add(new Label("Game Duration (minutes)", skin)).row();
        SelectBox<Integer> timeSelect = new SelectBox<>(skin);
        timeSelect.setItems(2, 5, 10, 20);
        mainTable.add(timeSelect).row();

        TextButton startBtn = new TextButton("Start Game", skin);
        mainTable.add(startBtn).padTop(20).row();

        TextButton backBtn = new TextButton("Back", skin);
        backBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getMain().setScreen(new MainMenuView(new MainMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
            }
        });
        mainTable.add(backBtn).padTop(10).row();

        Table rootTable = new Table();
        rootTable.setFillParent(true);
        stage.addActor(rootTable);

        rootTable.add(gunPreviewTable).expandY().left().pad(20);
        rootTable.add(mainTable).expand().fill().pad(20);
        rootTable.add(heroPreviewTable).expandY().right().pad(20);


    }

    public Stage getStage() {
        return stage;
    }

    @Override
    public void show() {

    }

    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int i, int i1) {

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

    public void dispose() {
        stage.dispose();
    }
}
