package com.tilldawn.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Enum.HeroEnum;
import com.tilldawn.Enum.WeaponEnum;
import com.tilldawn.Main;
import com.tilldawn.control.GameController;
import com.tilldawn.control.MainMenuController;
import com.tilldawn.control.PreGameMenuController;
import com.tilldawn.model.GameAssetManager;
import com.tilldawn.model.Weapon;

public class PreGameMenuView implements Screen {
    private Stage stage;
    private Skin skin;
    private HeroEnum selectedHeroEnum;
    private WeaponEnum selectedWeaponEnum;
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
        for (HeroEnum heroEnum : HeroEnum.values()) {
            Texture texture = new Texture(Gdx.files.internal(heroEnum.getPath()));
            Image image = new Image(texture);
            image.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    selectedHeroEnum = heroEnum;
                    selectedHeroImage.setDrawable(image.getDrawable());
                    Main.getApp().getCurrentUser().setHero(heroEnum);
                }
            });
            heroTable.add(image).size(64).pad(10);
        }
        mainTable.add(heroTable).row();

        mainTable.add(new Label("Choose Weapon", skin)).row();
        Table weaponTable = new Table();
        for (WeaponEnum weaponEnum : WeaponEnum.values()) {
            Texture texture = new Texture(Gdx.files.internal(weaponEnum.getPath()));
            Image image = new Image(texture);
            image.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    selectedWeaponEnum = weaponEnum;
                    selectedGunImage.setDrawable(image.getDrawable());
                    Main.getApp().getCurrentUser().setWeaponEnum(weaponEnum);

                }
            });
            weaponTable.add(image).size(64).pad(10);
        }
        mainTable.add(weaponTable).row();

        mainTable.add(new Label("Game Duration (minutes)", skin)).row();
        SelectBox<Integer> timeSelect = new SelectBox<>(skin);
        timeSelect.setItems(2, 5, 10, 20);
        mainTable.add(timeSelect).row();
        Label message = new Label("", skin);
        message.setScale(2f);
        message.setColor(Color.VIOLET);
        TextButton startBtn = new TextButton("Start Game", skin);
        mainTable.add(startBtn).padTop(20).row();
        startBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
//                SaveUserToJson.saveUserToJson(Main.getApp().getCurrentUser());
//                message.setVisible(true);
//                message.getColor().a = 1f;
//                message.clearActions();
//
//                message.addAction(Actions.sequence(
//                    Actions.delay(1.5f),
//                    Actions.fadeOut(0.5f),
//                    Actions.run(() -> {
//                        message.setVisible(false);
//                        message.getColor().a = 1f;
//                    })
//                ));
                if (selectedHeroEnum != null && selectedWeaponEnum != null) {

//                    message.setText("user hero and weapon saved");
                    Main.getApp().getCurrentUser().setGameTime(timeSelect.getSelected() * 60);
                    Main.getApp().getCurrentUser().setWeapon(new Weapon(selectedWeaponEnum));
                    Main.getApp().getCurrentUser().setPlayerHP(Main.getApp().getCurrentUser().getHero().getHP());
                    Main.getApp().getCurrentUser().setSpeed(Main.getApp().getCurrentUser().getHero().getSpeed());
                    Main.getMain().setScreen(new GameView(new GameController(), GameAssetManager.getGameAssetManager().getSkin()));
                } else {
                    message.setText("please select hero and weapon");
                }
            }
        });

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
        rootTable.row();
        rootTable.add(message).colspan(3).center().padBottom(50);


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
