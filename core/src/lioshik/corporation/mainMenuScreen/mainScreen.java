package lioshik.corporation.mainMenuScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Null;
import lioshik.corporation.LANscreen.LANMenuScreen;
import lioshik.corporation.gameScreen.GameScreen;
import lioshik.corporation.gameScreen.TextureContainer;

import java.util.Timer;
import java.util.TimerTask;

public class mainScreen extends ScreenAdapter {
    public Stage stage;
    public ImageButton buttonSinglePlayer;
    public ImageButton buttonMultiPlayer;
    public ImageButton buttonExit;
    public ImageButton buttonLAN;
    public ImageButton buttonBack;
    public ImageButton buttonVs1;
    public ImageButton buttonVs2;
    public ImageButton buttonVs3;
    public ImageButton buttonPlayers2;
    public ImageButton buttonPlayers3;
    public ImageButton buttonPlayers4;
    public Image titleImage;
    public Game game;
    public ScreenAdapter screenLAN;

    enum buttonsState {
        Main,
        SinglePlayer,
        Multiplayer,
        LAN
    }

    public buttonsState crState = buttonsState.Main;

    public mainScreen(Game g) {
        game = g;
        screenLAN = new LANMenuScreen(this, game);
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        buttonSinglePlayer = new ImageButton(new TextureRegionDrawable(TextureContainer.buttonSinglePlayerUp), new TextureRegionDrawable(TextureContainer.buttonSinglePlayerDown));
        buttonMultiPlayer = new ImageButton(new TextureRegionDrawable(TextureContainer.buttonMultiPlayerUp), new TextureRegionDrawable(TextureContainer.buttonMultiPlayerDown));
        buttonLAN = new ImageButton(new TextureRegionDrawable(TextureContainer.buttonLanUp), new TextureRegionDrawable(TextureContainer.buttonLanDown));
        buttonExit = new ImageButton(new TextureRegionDrawable(TextureContainer.buttonExitUp), new TextureRegionDrawable(TextureContainer.buttonExitDown));
        float scale = Math.min(stage.getWidth() / 1.25f / buttonSinglePlayer.getWidth(), stage.getHeight() / 10 / buttonSinglePlayer.getHeight());
        buttonSinglePlayer.setWidth(buttonSinglePlayer.getWidth() * scale);
        buttonSinglePlayer.setHeight(buttonSinglePlayer.getHeight() * scale);
        buttonSinglePlayer.setPosition(stage.getWidth() / 2.0f - buttonSinglePlayer.getWidth() / 2.0f, stage.getHeight() / 2.0f - buttonSinglePlayer.getHeight() / 2.0f);
        float padding = buttonSinglePlayer.getHeight() * 1.25f;
        buttonExit.setSize(buttonSinglePlayer.getWidth(), buttonSinglePlayer.getHeight());
        buttonMultiPlayer.setSize(buttonSinglePlayer.getWidth(), buttonSinglePlayer.getHeight());
        buttonMultiPlayer.setPosition(buttonSinglePlayer.getX(), buttonSinglePlayer.getY() - padding);
        buttonLAN.setSize(buttonSinglePlayer.getWidth(), buttonSinglePlayer.getHeight());
        buttonLAN.setPosition(buttonMultiPlayer.getX(), buttonMultiPlayer.getY() - padding);
        buttonExit.setPosition(buttonMultiPlayer.getX(), buttonLAN.getY() - padding);

        buttonVs1 = new ImageButton(new TextureRegionDrawable(TextureContainer.buttonVs1Up), new TextureRegionDrawable(TextureContainer.buttonVs1Down));
        buttonVs2 = new ImageButton(new TextureRegionDrawable(TextureContainer.buttonVs2Up), new TextureRegionDrawable(TextureContainer.buttonVs2Down));
        buttonVs3 = new ImageButton(new TextureRegionDrawable(TextureContainer.buttonVs3Up), new TextureRegionDrawable(TextureContainer.buttonVs3Down));
        buttonBack = new ImageButton(new TextureRegionDrawable(TextureContainer.buttonBackUp), new TextureRegionDrawable(TextureContainer.buttonBackDown));
        buttonPlayers2 = new ImageButton(new TextureRegionDrawable(TextureContainer.buttonPlayers2Up), new TextureRegionDrawable(TextureContainer.buttonPlayers2Down));
        buttonPlayers3 = new ImageButton(new TextureRegionDrawable(TextureContainer.buttonPlayers3Up), new TextureRegionDrawable(TextureContainer.buttonPlayers3Down));
        buttonPlayers4 = new ImageButton(new TextureRegionDrawable(TextureContainer.buttonPlayers4Up), new TextureRegionDrawable(TextureContainer.buttonPlayers4Down));
        buttonVs1.setSize(buttonSinglePlayer.getWidth(), buttonSinglePlayer.getHeight());
        buttonVs2.setSize(buttonSinglePlayer.getWidth(), buttonSinglePlayer.getHeight());
        buttonVs3.setSize(buttonSinglePlayer.getWidth(), buttonSinglePlayer.getHeight());
        buttonBack.setSize(buttonSinglePlayer.getWidth(), buttonSinglePlayer.getHeight());
        buttonPlayers2.setSize(buttonSinglePlayer.getWidth(), buttonSinglePlayer.getHeight());
        buttonPlayers3.setSize(buttonSinglePlayer.getWidth(), buttonSinglePlayer.getHeight());
        buttonPlayers4.setSize(buttonSinglePlayer.getWidth(), buttonSinglePlayer.getHeight());
        buttonVs1.setPosition(buttonSinglePlayer.getX(), buttonSinglePlayer.getY());
        buttonVs2.setPosition(buttonMultiPlayer.getX(), buttonMultiPlayer.getY());
        buttonVs3.setPosition(buttonLAN.getX(), buttonLAN.getY());
        buttonBack.setPosition(buttonExit.getX(), buttonExit.getY());
        buttonPlayers2.setPosition(buttonSinglePlayer.getX(), buttonSinglePlayer.getY());
        buttonPlayers3.setPosition(buttonMultiPlayer.getX(), buttonMultiPlayer.getY());
        buttonPlayers4.setPosition(buttonLAN.getX(), buttonLAN.getY());

        titleImage = new Image(TextureContainer.titleTexture);
        scale = (float) Math.min(stage.getWidth() * 0.8 / titleImage.getWidth(), stage.getHeight() * 0.25 / titleImage.getHeight());
        titleImage.setSize(titleImage.getWidth() * scale, titleImage.getHeight() * scale);
        titleImage.setPosition(stage.getWidth() / 2.0f - titleImage.getWidth() / 2.0f, stage.getHeight() * 0.85f - titleImage.getHeight() / 2.0f);
        stage.addActor(buttonSinglePlayer);
        stage.addActor(buttonExit);
        stage.addActor(buttonLAN);
        stage.addActor(buttonMultiPlayer);
        stage.addActor(titleImage);
        stage.addActor(buttonVs1);
        stage.addActor(buttonVs2);
        stage.addActor(buttonVs3);
        stage.addActor(buttonBack);
        stage.addActor(buttonPlayers2);
        stage.addActor(buttonPlayers3);
        stage.addActor(buttonPlayers4);
        buttonExit.setVisible(false);
        buttonMultiPlayer.setVisible(false);
        buttonSinglePlayer.setVisible(false);
        buttonBack.setVisible(false);
        buttonVs2.setVisible(false);
        buttonLAN.setVisible(false);
        buttonVs1.setVisible(false);
        buttonVs3.setVisible(false);
        buttonPlayers2.setVisible(false);
        buttonPlayers3.setVisible(false);
        buttonPlayers4.setVisible(false);

        buttonExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                Gdx.app.exit();
            }
        });

        buttonSinglePlayer.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                hideAllButtons();
                setSinglePlayerButtonsVisible();
            }
        });

        buttonLAN.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                Gdx.input.setInputProcessor(null);
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        hideAllButtons();
                        try {
                            Thread.currentThread().sleep(250);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                        Gdx.app.postRunnable(new Runnable() {
                            @Override
                            public void run() {
                                game.setScreen(screenLAN);
                            }
                        });
                    }
                });
                t.start();
            }
        });

        buttonBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                hideAllButtons();
                setMainButtonsVisible();
            }
        });

        buttonMultiPlayer.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                hideAllButtons();
                setMultiPlayerButtonsVisible();
            }
        });

        buttonPlayers2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                game.setScreen(new GameScreen(2, game));
            }
        });

        buttonPlayers3.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                game.setScreen(new GameScreen(3, game));
            }
        });

        buttonPlayers4.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                game.setScreen(new GameScreen(4, game));
            }
        });
    }

    public static void addTransformAnim(ImageButton imageButton, boolean right, boolean reverse, float delay, float duration) {
        imageButton.setTransform(true);
        float moveLength = imageButton.getWidth() / 5.0f;
        if (right) {
            if (!reverse)
                imageButton.addAction(Actions.sequence(Actions.delay(delay),
                        Actions.moveBy(moveLength, 0, 0),
                        Actions.visible(true),
                        Actions.moveBy(-moveLength, 0, duration)
                        ));
            if (reverse)
                imageButton.addAction(Actions.sequence(Actions.delay(delay),
                        Actions.moveBy(moveLength, 0, duration),
                        Actions.visible(false),
                        Actions.moveBy(-moveLength, 0, 0)
                ));
        } else {
            if (!reverse)
                imageButton.addAction(Actions.sequence(Actions.delay(delay),
                        Actions.moveBy(-moveLength, 0, 0),
                        Actions.visible(true),
                        Actions.moveBy(moveLength, 0, duration)
                ));
            if (reverse)
                imageButton.addAction(Actions.sequence(Actions.delay(delay),
                        Actions.moveBy(-moveLength, 0, duration),
                        Actions.visible(false),
                        Actions.moveBy(moveLength, 0, 0)
                ));
        }
    }

    public void hideAllButtons() {
        switch (crState) {
            case Main:
                addTransformAnim(buttonSinglePlayer, true, true, 0, 0.2f);
                addTransformAnim(buttonMultiPlayer, false, true, 0, 0.2f);
                addTransformAnim(buttonLAN, true, true, 0, 0.2f);
                addTransformAnim(buttonExit, false, true, 0, 0.2f);
                break;
            case SinglePlayer:
                addTransformAnim(buttonVs1, true, true, 0, 0.2f);
                addTransformAnim(buttonVs2, false, true, 0, 0.2f);
                addTransformAnim(buttonVs3, true, true, 0, 0.2f);
                addTransformAnim(buttonBack, false, true, 0, 0.2f);
                break;
            case Multiplayer:
                addTransformAnim(buttonPlayers2, true, true, 0, 0.2f);
                addTransformAnim(buttonPlayers3, false, true, 0, 0.2f);
                addTransformAnim(buttonPlayers4, true, true, 0, 0.2f);
                addTransformAnim(buttonBack, false, true, 0, 0.2f);
                break;
            case LAN:
                break;
        }
    }

    public void setMainButtonsVisible() {
        addTransformAnim(buttonSinglePlayer, true, false, 0.2f, 0.2f);
        addTransformAnim(buttonMultiPlayer, false, false, 0.2f, 0.2f);
        addTransformAnim(buttonLAN, true, false, 0.2f, 0.2f);
        addTransformAnim(buttonExit, false, false, 0.2f, 0.2f);
        crState = buttonsState.Main;
    }
    public void setSinglePlayerButtonsVisible() {
        addTransformAnim(buttonVs1, true, false, 0.2f, 0.2f);
        addTransformAnim(buttonVs2, false, false, 0.2f, 0.2f);
        addTransformAnim(buttonVs3, true, false, 0.2f, 0.2f);
        addTransformAnim(buttonBack, false, false, 0.2f, 0.2f);
        crState = buttonsState.SinglePlayer;
    }
    public void setMultiPlayerButtonsVisible() {
        addTransformAnim(buttonPlayers2, true, false, 0.2f, 0.2f);
        addTransformAnim(buttonPlayers3, false, false, 0.2f, 0.2f);
        addTransformAnim(buttonPlayers4, true, false, 0.2f, 0.2f);
        addTransformAnim(buttonBack, false, false, 0.2f, 0.2f);
        crState = buttonsState.Multiplayer;
    }

    @Override
    public void show() {
        buttonLAN.setVisible(true);
        buttonSinglePlayer.setVisible(true);
        buttonExit.setVisible(true);
        buttonMultiPlayer.setVisible(true);
        addTransformAnim(buttonSinglePlayer, true, false, 0f, 0.2f);
        addTransformAnim(buttonMultiPlayer, false, false, 0f, 0.2f);
        addTransformAnim(buttonLAN, true, false, 0f, 0.2f);
        addTransformAnim(buttonExit, false, false, 0f, 0.2f);
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                Gdx.input.setInputProcessor(stage);
            }
        }, 250);
    }
    @Override
    public void render(float dt) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(dt);
        stage.draw();
    }

    @Override
    public void dispose() {

    }
}



