package lioshik.corporation.LANscreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import lioshik.corporation.Server.GameServer;
import lioshik.corporation.client.GameClient;
import lioshik.corporation.gameScreen.TextureContainer;
import lioshik.corporation.mainMenuScreen.mainScreen;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class LANMenuScreen extends ScreenAdapter {

    public enum State{
        MAIN,
        SERVER_DIALOG,
        CLIENT_DIALOG;
    }
    public State crState;
    public Stage stage;
    public Game g;
    public ImageButton buttonHost;
    public ImageButton buttonJoin;
    public ImageButton buttonBack;
    public ScreenAdapter prevScreen;
    public Image titleLAN;
    public GameClient client;
    public GameServer server;

    public LANMenuScreen(ScreenAdapter prev, final Game g) {
        crState = State.MAIN;
        this.g = g;
        this.prevScreen = prev;
        stage = new Stage();
        Image titleImage = new Image(TextureContainer.titleTexture);
        float scaleTitle = (float) Math.min(stage.getWidth() * 0.8 / titleImage.getWidth(), stage.getHeight() * 0.25 / titleImage.getHeight());
        titleImage.setSize(titleImage.getWidth() * scaleTitle, titleImage.getHeight() * scaleTitle);
        titleImage.setPosition(stage.getWidth() / 2.0f - titleImage.getWidth() / 2.0f, stage.getHeight() * 0.85f - titleImage.getHeight() / 2.0f);

        titleLAN = new Image(TextureContainer.titleLAN);
        scaleTitle = (float) (stage.getWidth() * 0.95 / titleLAN.getWidth());
        titleLAN.setSize(titleLAN.getWidth() * scaleTitle, titleLAN.getHeight() * scaleTitle);
        titleLAN.setPosition(stage.getWidth() / 2.0f - titleLAN.getWidth() / 2.0f, titleImage.getY() - titleLAN.getHeight() * 1.5f);
        stage.addActor(titleLAN);
        stage.addActor(titleImage);

        buttonHost = new ImageButton(new TextureRegionDrawable(TextureContainer.buttonHostUp), new TextureRegionDrawable(TextureContainer.buttonHostDown));
        buttonJoin = new ImageButton(new TextureRegionDrawable(TextureContainer.buttonJoinUp), new TextureRegionDrawable(TextureContainer.buttonJoinDown));
        buttonBack = new ImageButton(new TextureRegionDrawable(TextureContainer.buttonBackUp), new TextureRegionDrawable(TextureContainer.buttonBackDown));
        float scale = Math.min(stage.getWidth() / 1.25f / buttonHost.getWidth(), stage.getHeight() / 10 / buttonHost.getHeight());
        buttonHost.setSize(buttonHost.getWidth() * scale, buttonHost.getHeight() * scale);
        buttonJoin.setSize(buttonHost.getWidth(), buttonHost.getHeight());
        buttonBack.setSize(buttonHost.getWidth(), buttonHost.getHeight());
        buttonHost.setPosition((stage.getWidth() - buttonHost.getWidth()) / 2.0f, (stage.getHeight() - buttonHost.getHeight()) / 2.0f);
        float padding = buttonHost.getHeight() * 1.25f;
        buttonJoin.setPosition(buttonHost.getX(), buttonHost.getY() - padding);
        buttonBack.setPosition(buttonJoin.getX(), buttonJoin.getY() - padding);
        buttonBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                if (crState != State.MAIN) return;
                Gdx.input.setInputProcessor(null);
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mainScreen.addTransformAnim(buttonHost, true, true, 0, 0.2f);
                        mainScreen.addTransformAnim(buttonJoin, false, true, 0, 0.2f);
                        mainScreen.addTransformAnim(buttonBack, true, true, 0, 0.2f);
                        try {
                            Thread.currentThread().sleep(250);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                        Gdx.app.postRunnable(new Runnable() {
                            @Override
                            public void run() {
                                g.setScreen(prevScreen);
                            }
                        });
                        try {
                            Thread.currentThread().sleep(10);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                        buttonBack.setVisible(true);
                        buttonHost.setVisible(true);
                        buttonJoin.setVisible(true);
                    }
                });
                t.start();
            }
        });
        buttonHost.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                if (crState != State.MAIN) return;
                showServerOpenDialog();
            }
        });
        buttonJoin.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                if (crState != State.MAIN) return;
                showClientDialog();
            }
        });
        stage.addActor(buttonHost);
        stage.addActor(buttonJoin);
        stage.addActor(buttonBack);
    }

    public void setConnectedPlayersCount(int c) {
        playersLabel.setText("Подключено игроков: " + c + "/4");
    }

    private Label playersLabel;
    Table serverDialogStage;
    public void showServerOpenDialog(){
        crState = State.SERVER_DIALOG;
        serverDialogStage = new Table();
        serverDialogStage.setPosition(0, 0);
        float tSize = Math.min(stage.getWidth() * 0.75f, stage.getHeight() * 0.75f);
        serverDialogStage.setSize(tSize, tSize);
        serverDialogStage.setPosition((stage.getWidth() - serverDialogStage.getWidth()) / 2.0f, (stage.getHeight() - serverDialogStage.getHeight()) / 2.0f);
        Pixmap bgPixmap = new Pixmap(1,1, Pixmap.Format.RGBA8888);
        bgPixmap.setColor(new Color(0.94f, 0.94f, 0.94f, 0.95f));
        bgPixmap.fill();
        serverDialogStage.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(bgPixmap))));
        BitmapFont font;
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("game_font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 90;
        parameter.characters = "ПКйцукенгшщзхъфывапролджэячсмитьбю1234567890: /";
        font = generator.generateFont(parameter);
        int code = new Random().nextInt() % (20000) + 30000;
        Label serverCodeText = new Label("Код доступа: " + code, new Label.LabelStyle(font, Color.BLACK));
        serverCodeText.setFontScale(serverDialogStage.getWidth() * 0.55f  / serverCodeText.getWidth());
        serverCodeText.setHeight(serverDialogStage.getHeight() / 5);
        serverCodeText.setAlignment(Align.center);
        serverCodeText.setPosition((serverDialogStage.getWidth() - serverCodeText.getWidth()) / 2.0f, (serverDialogStage.getHeight() * 0.75f - serverCodeText.getHeight() / 2.0f));
        playersLabel = new Label("Подключено игроков: " + 0 + "/4", new Label.LabelStyle(font, Color.BLACK));
        playersLabel.setFontScale(serverDialogStage.getWidth()* 0.9f  / playersLabel.getWidth());
        playersLabel.setHeight(serverDialogStage.getHeight() / 5);
        playersLabel.setAlignment(Align.center);
        playersLabel.setPosition((serverDialogStage.getWidth()- playersLabel.getWidth()) / 2.0f, (serverDialogStage.getHeight() * 0.75f - playersLabel.getHeight() / 2.0f) - serverCodeText.getHeight() * 1.5f);
        ImageButton buttonCancel = new ImageButton(new TextureRegionDrawable(TextureContainer.buttonCancelUp), new TextureRegionDrawable(TextureContainer.buttonCancelDown));
        ImageButton buttonLaunch = new ImageButton(new TextureRegionDrawable(TextureContainer.buttonLaunchUp), new TextureRegionDrawable(TextureContainer.buttonLaunchDown));
        float scale = serverDialogStage.getWidth() / 2.5f / buttonCancel.getWidth();
        buttonCancel.setSize(buttonCancel.getWidth() * scale, buttonCancel.getHeight() * scale);
        buttonLaunch.setSize(buttonCancel.getWidth(), buttonCancel.getHeight());
        buttonCancel.setPosition(serverDialogStage.getWidth() * 0.25f - buttonCancel.getWidth() / 2f, buttonCancel.getHeight());
        buttonLaunch.setPosition(serverDialogStage.getWidth() - (buttonCancel.getWidth() + buttonCancel.getX()), buttonCancel.getY());
        serverDialogStage.addActor(serverCodeText);
        serverDialogStage.addActor(playersLabel);
        serverDialogStage.addActor(buttonCancel);
        serverDialogStage.addActor(buttonLaunch);
        stage.addActor(serverDialogStage);
        generator.dispose();
        server = new GameServer(code, this);
        client = new GameClient(this, code, g);
        buttonCancel.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                serverDialogStage.remove();
                crState = State.MAIN;
                server.server.stop();
            }
        });
        buttonLaunch.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                server.startGame();
            }
        });
    }

    public Table clientDialogStage;
    public void showClientDialog(){
        crState = State.CLIENT_DIALOG;
        clientDialogStage = new Table();
        clientDialogStage.setPosition(0, 0);
        float tSize = Math.min(stage.getWidth() * 0.75f, stage.getHeight() * 0.75f);
        clientDialogStage.setSize(tSize, tSize);
        clientDialogStage.setPosition((stage.getWidth() - clientDialogStage.getWidth()) / 2.0f, (stage.getHeight() - clientDialogStage.getHeight()) / 2.0f);
        Pixmap bgPixmap = new Pixmap(1,1, Pixmap.Format.RGBA8888);
        bgPixmap.setColor(new Color(0.94f, 0.94f, 0.94f, 0.95f));
        bgPixmap.fill();
        clientDialogStage.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(bgPixmap))));
        BitmapFont font;
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("game_font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 90;
        parameter.characters = "ПВОКйцукенгшщзхъфывапролджэячсмитьбю1234567890: /";
        font = generator.generateFont(parameter);
        final TextField serverCodeText = new TextField("Введите код", new TextField.TextFieldStyle(font, Color.BLACK, null, null, null));
        serverCodeText.setWidth(clientDialogStage.getWidth() * 0.9f);
        serverCodeText.setTextFieldFilter(new TextField.TextFieldFilter() {
            @Override
            public boolean acceptChar(TextField textField, char c) {
                return "1234567890".contains(Character.toString(c));
            }
        });
        serverCodeText.setAlignment(Align.center);
        Pixmap labelColor = new Pixmap((int)serverCodeText.getWidth(), (int)serverCodeText.getHeight(), Pixmap.Format.RGB888);
        labelColor.setColor(1, 1, 1, 1);
        labelColor.fill();
        serverCodeText.getStyle().background = new TextureRegionDrawable(new Texture(labelColor));
        serverCodeText.setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char c) {
                if (textField.getText().contains("В")) {
                    textField.setText(Character.toString(c));
                    textField.setMaxLength(5);
                }
                textField.setCursorPosition(textField.getText().length());
            }
        });
        serverCodeText.setPosition((clientDialogStage.getWidth() - serverCodeText.getWidth()) / 2.0f, (clientDialogStage.getHeight() * 0.75f - serverCodeText.getHeight() / 2.0f));
        playersLabel = new Label("Подключено игркоков: 0/4", new Label.LabelStyle(font, Color.BLACK));
        playersLabel.setFontScale(clientDialogStage.getWidth()* 0.9f  / playersLabel.getWidth());
        playersLabel.setPosition((clientDialogStage.getWidth() - playersLabel.getWidth()) / 2.0f, (clientDialogStage.getHeight() * 0.75f - playersLabel.getHeight() / 2.0f) - serverCodeText.getHeight() * 1.5f);
        playersLabel.setText("");
        playersLabel.setHeight(clientDialogStage.getHeight() / 5);
        playersLabel.setAlignment(Align.center);
        ImageButton buttonCancel = new ImageButton(new TextureRegionDrawable(TextureContainer.buttonCancelUp), new TextureRegionDrawable(TextureContainer.buttonCancelDown));
        final ImageButton buttonLaunch = new ImageButton(new TextureRegionDrawable(TextureContainer.buttonJoinUp), new TextureRegionDrawable(TextureContainer.buttonJoinDown));
        float scale = clientDialogStage.getWidth() / 2.5f / buttonCancel.getWidth();
        buttonCancel.setSize(buttonCancel.getWidth() * scale, buttonCancel.getHeight() * scale);
        buttonLaunch.setSize(buttonCancel.getWidth(), buttonCancel.getHeight());
        buttonCancel.setPosition(clientDialogStage.getWidth() * 0.25f - buttonCancel.getWidth() / 2f, buttonCancel.getHeight());
        buttonLaunch.setPosition(clientDialogStage.getWidth() - (buttonCancel.getWidth() + buttonCancel.getX()), buttonCancel.getY());
        clientDialogStage.addActor(serverCodeText);
        clientDialogStage.addActor(playersLabel);
        clientDialogStage.addActor(buttonCancel);
        clientDialogStage.addActor(buttonLaunch);
        stage.addActor(clientDialogStage);
        generator.dispose();
        buttonCancel.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                clientDialogStage.remove();
                crState = State.MAIN;
                if (client != null) client.client.stop();
            }
        });
        buttonLaunch.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                try {
                    client = new GameClient(LANMenuScreen.this, Integer.parseInt(serverCodeText.getText()), g);
                } catch (Exception e) {
                    playersLabel.setText("Ошибка подключения");
                } finally {
                    buttonLaunch.remove();
                }
            }
        });
    }

    @Override
    public void show() {
        buttonBack.setVisible(true);
        buttonHost.setVisible(true);
        buttonJoin.setVisible(true);
        titleLAN.setVisible(false);
        titleLAN.addAction(Actions.sequence(Actions.delay(0.2f, Actions.visible(true))));
        mainScreen.addTransformAnim(buttonHost, true, false, 0, 0.2f);
        mainScreen.addTransformAnim(buttonJoin, false, false, 0, 0.2f);
        mainScreen.addTransformAnim(buttonBack, true, false, 0, 0.2f);
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                Gdx.input.setInputProcessor(stage);
            }
        }, 200);
        buttonBack.setVisible(true);
        buttonHost.setVisible(true);
        buttonJoin.setVisible(true);
    }

    @Override
    public void render(float dt) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(dt);
        stage.draw();
    }
}
