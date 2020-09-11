package lioshik.corporation.mainMenuScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import lioshik.corporation.gameScreen.GameScreen;
import lioshik.corporation.gameScreen.TextureContainer;

public class mainScreen extends ScreenAdapter {
    public Stage stage;
    public ImageButton buttonSinglePlayer;
    public ImageButton buttonMultiPlayer;
    public ImageButton buttonExit;
    public ImageButton buttonBack;
    public ImageButton buttonVs1;
    public ImageButton buttonVs2;
    public ImageButton buttonPlayers2;
    public ImageButton buttonPlayers3;
    public Image titleImage;
    public Game game;

    public mainScreen(Game g) {
        game = g;
    }

    public void setAllButtonsVisible(boolean v) {
        setMainButtonsVisible(v);
        setSinglePlayerButtonsVisible(v);
        setMultiPlayerButtonsVisible(false);
    }

    public void setMainButtonsVisible(boolean v) {
        buttonExit.setVisible(v);
        buttonSinglePlayer.setVisible(v);
        buttonMultiPlayer.setVisible(v);
    }
    public void setSinglePlayerButtonsVisible(boolean v) {
        buttonBack.setVisible(v);
        buttonVs1.setVisible(v);
        buttonVs2.setVisible(v);
    }
    public void setMultiPlayerButtonsVisible(boolean v) {
        buttonBack.setVisible(v);
        buttonPlayers3.setVisible(v);
        buttonPlayers2.setVisible(v);
    }

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        buttonSinglePlayer = new ImageButton(new TextureRegionDrawable(TextureContainer.buttonSinglePlayerUp), new TextureRegionDrawable(TextureContainer.buttonSinglePlayerDown));
        buttonMultiPlayer = new ImageButton(new TextureRegionDrawable(TextureContainer.buttonMultiPlayerUp), new TextureRegionDrawable(TextureContainer.buttonMultiPlayerDown));
        buttonExit = new ImageButton(new TextureRegionDrawable(TextureContainer.buttonExitUp), new TextureRegionDrawable(TextureContainer.buttonExitDown));
        float scale = stage.getWidth() / 2.0f / buttonSinglePlayer.getWidth();
        buttonSinglePlayer.setWidth(buttonSinglePlayer.getWidth() * scale);
        buttonSinglePlayer.setHeight(buttonSinglePlayer.getHeight() * scale);
        buttonSinglePlayer.setPosition(stage.getWidth() / 2.0f - buttonSinglePlayer.getWidth() / 2.0f, stage.getHeight() / 2.0f - buttonSinglePlayer.getHeight() / 2.0f);
        float padding = buttonSinglePlayer.getHeight() * 1.25f;
        buttonExit.setSize(buttonSinglePlayer.getWidth(), buttonSinglePlayer.getHeight());
        buttonMultiPlayer.setSize(buttonSinglePlayer.getWidth(), buttonSinglePlayer.getHeight());
        buttonMultiPlayer.setPosition(buttonSinglePlayer.getX(), buttonSinglePlayer.getY() - padding);
        buttonExit.setPosition(buttonMultiPlayer.getX(), buttonMultiPlayer.getY() - padding);

        buttonVs1 = new ImageButton(new TextureRegionDrawable(TextureContainer.buttonVs1Up), new TextureRegionDrawable(TextureContainer.buttonVs1Down));
        buttonVs2 = new ImageButton(new TextureRegionDrawable(TextureContainer.buttonVs2Up), new TextureRegionDrawable(TextureContainer.buttonVs2Down));
        buttonBack = new ImageButton(new TextureRegionDrawable(TextureContainer.buttonBackUp), new TextureRegionDrawable(TextureContainer.buttonBackDown));
        buttonPlayers2 = new ImageButton(new TextureRegionDrawable(TextureContainer.buttonPlayers2Up), new TextureRegionDrawable(TextureContainer.buttonPlayers2Down));
        buttonPlayers3 = new ImageButton(new TextureRegionDrawable(TextureContainer.buttonPlayers3Up), new TextureRegionDrawable(TextureContainer.buttonPlayers3Down));
        buttonVs1.setSize(buttonSinglePlayer.getWidth(), buttonSinglePlayer.getHeight());
        buttonVs2.setSize(buttonSinglePlayer.getWidth(), buttonSinglePlayer.getHeight());
        buttonBack.setSize(buttonSinglePlayer.getWidth(), buttonSinglePlayer.getHeight());
        buttonPlayers2.setSize(buttonSinglePlayer.getWidth(), buttonSinglePlayer.getHeight());
        buttonPlayers3.setSize(buttonSinglePlayer.getWidth(), buttonSinglePlayer.getHeight());
        buttonVs1.setPosition(buttonSinglePlayer.getX(), buttonSinglePlayer.getY());
        buttonVs2.setPosition(buttonMultiPlayer.getX(), buttonMultiPlayer.getY());
        buttonBack.setPosition(buttonExit.getX(), buttonExit.getY());
        buttonPlayers2.setPosition(buttonSinglePlayer.getX(), buttonSinglePlayer.getY());
        buttonPlayers3.setPosition(buttonMultiPlayer.getX(), buttonMultiPlayer.getY());

        titleImage = new Image(TextureContainer.titleTexture);
        scale = (float) (stage.getWidth() * 0.8 / titleImage.getWidth());
        titleImage.setSize(titleImage.getWidth() * scale, titleImage.getHeight() * scale);
        titleImage.setPosition(stage.getWidth() / 2.0f - titleImage.getWidth() / 2.0f, stage.getHeight() * 0.75f - titleImage.getHeight() / 2.0f);
        stage.addActor(buttonSinglePlayer);
        stage.addActor(buttonExit);
        stage.addActor(buttonMultiPlayer);
        stage.addActor(titleImage);
        stage.addActor(buttonVs1);
        stage.addActor(buttonVs2);
        stage.addActor(buttonBack);
        stage.addActor(buttonPlayers2);
        stage.addActor(buttonPlayers3);
        setAllButtonsVisible(false);
        setMainButtonsVisible(true);

        buttonExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                Gdx.app.exit();
            }
        });

        buttonSinglePlayer.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                setAllButtonsVisible(false);
                setSinglePlayerButtonsVisible(true);
            }
        });

        buttonBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                setAllButtonsVisible(false);
                setMainButtonsVisible(true);
            }
        });

        buttonMultiPlayer.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                setAllButtonsVisible(false);
                setMultiPlayerButtonsVisible(true);
            }
        });

        buttonPlayers2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                game.setScreen(new GameScreen(2));
            }
        });

        buttonPlayers3.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                game.setScreen(new GameScreen(3));
            }
        });
    }
    @Override
    public void render(float dt) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    @Override
    public void dispose() {

    }
}



