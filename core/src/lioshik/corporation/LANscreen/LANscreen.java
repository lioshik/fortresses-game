package lioshik.corporation.LANscreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.VisibleAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import lioshik.corporation.gameScreen.TextureContainer;
import lioshik.corporation.mainMenuScreen.mainScreen;

public class LANscreen extends ScreenAdapter {

    Stage stage;
    Game g;
    ImageButton buttonHost;
    ImageButton buttonJoin;
    ImageButton buttonBack;
    ScreenAdapter prevScreen;
    Image titleLAN;

    public LANscreen(ScreenAdapter prev, final Game g) {
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
                Gdx.input.setInputProcessor(null);
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mainScreen.addTransformAnim(buttonHost, true, true, 0, 0.2f);
                        mainScreen.addTransformAnim(buttonJoin, false, true, 0, 0.2f);
                        mainScreen.addTransformAnim(buttonBack, true, true, 0, 0.2f);
                        try {
                            Thread.currentThread().sleep(200);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                        Gdx.app.postRunnable(new Runnable() {
                            @Override
                            public void run() {
                                g.setScreen(prevScreen);
                            }
                        });
                    }
                });
                t.start();
            }
        });
        stage.addActor(buttonHost);
        stage.addActor(buttonJoin);
        stage.addActor(buttonBack);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        titleLAN.setVisible(false);
        titleLAN.addAction(Actions.sequence(Actions.delay(0.2f, Actions.visible(true))));
        mainScreen.addTransformAnim(buttonHost, true, false, 0, 0.2f);
        mainScreen.addTransformAnim(buttonJoin, false, false, 0, 0.2f);
        mainScreen.addTransformAnim(buttonBack, true, false, 0, 0.2f);
    }

    @Override
    public void render(float dt) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(dt);
        stage.draw();
    }
}
