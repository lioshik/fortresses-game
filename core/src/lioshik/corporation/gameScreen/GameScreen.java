package lioshik.corporation.gameScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import lioshik.corporation.mainMenuScreen.mainScreen;

import java.util.List;

public class GameScreen extends ScreenAdapter {
    public SpriteBatch batch;
    public PlayingField field;
    public GameRulesController rulesController;
    public OrthographicCamera cam;
    public Stage uiStage;

    public int players;
    public Game game;

    public GameScreen(int p, Game g) {
        players = p;
        game = g;
    }

    @Override
    public void show () {
        uiStage = new Stage();
        cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
        batch = new SpriteBatch();
        field = new PlayingField(10, 10, new Rectangle(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        rulesController = new GameRulesController(this, players);
        Gdx.input.setInputProcessor(new InputAdapter(){
            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                Vector3 touchVector = cam.unproject(new Vector3(screenX, screenY, 0));
                screenX = (int) touchVector.x;
                screenY = (int) touchVector.y;
                if (field.anyCellTouched(screenX, screenY)) {
                    int[] cord = field.getTouchedCellCord(screenX, screenY);
                    rulesController.cellTouched(cord[0], cord[1]);
                    field.resetClickedCell();
                }
                return true;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                Vector3 touchVector = cam.unproject(new Vector3(screenX, screenY, 0));
                screenX = (int) touchVector.x;
                screenY = (int) touchVector.y;
                if (field.anyCellTouched(screenX, screenY)) {
                    int[] cord = field.getTouchedCellCord(screenX, screenY);
                    field.clickCell(cord[0], cord[1]);
                }
                return true;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                Vector3 touchVector = cam.unproject(new Vector3(screenX, screenY, 0));
                screenX = (int) touchVector.x;
                screenY = (int) touchVector.y;
                if (field.anyCellTouched(screenX, screenY)) {
                    int[] cord = field.getTouchedCellCord(screenX, screenY);
                    field.clickCell(cord[0], cord[1]);
                }
                return true;
            }
        });
    }

    public void gameEndDialog(int whichTurn) {
        Table t = new Table();
        t.setWidth(field.tableRectangle.width);
        t.setHeight(field.tableRectangle.height);
        t.setPosition(field.tableRectangle.getX(), field.tableRectangle.getY());
        Pixmap bgPixmap = new Pixmap(1,1, Pixmap.Format.RGBA8888);
        bgPixmap.setColor(new Color(1, 1, 1, 0.75f));
        bgPixmap.fill();
        t.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(bgPixmap))));
        Image title = new Image(new Texture[] {TextureContainer.titleColor1lost, TextureContainer.titleColor2lost, TextureContainer.titleColor3lost, TextureContainer.titleColor4ost}[whichTurn]);
        float szScale = 0.9f;
        title.setSize(t.getWidth() * szScale, title.getHeight() * t.getWidth() / title.getWidth() * szScale);
        title.setPosition(t.getWidth() / 2 - title.getWidth() / 2, t.getHeight() * 0.7f);
        t.addActor(title);
        ImageButton buttonMenu = new ImageButton(new TextureRegionDrawable(TextureContainer.buttonMenuUp), new TextureRegionDrawable(TextureContainer.buttonMenuDown));
        ImageButton buttonAgain = new ImageButton(new TextureRegionDrawable(TextureContainer.buttonAgainUp), new TextureRegionDrawable(TextureContainer.buttonAgainDown));
        float btnScale = Math.min(t.getHeight() / 4.0f / buttonAgain.getHeight(), t.getWidth() / 2.4f / buttonAgain.getWidth());
        buttonAgain.setHeight(buttonAgain.getHeight() * btnScale);
        buttonAgain.setWidth(buttonAgain.getWidth() * btnScale);
        buttonMenu.setSize(buttonAgain.getWidth(), buttonAgain.getHeight());
        buttonMenu.setPosition((t.getWidth() / 2.0f - buttonAgain.getWidth()) / 2.0f, t.getHeight() * 0.25f );
        buttonAgain.setPosition(t.getWidth() - buttonMenu.getX() - buttonAgain.getWidth(), t.getHeight() * 0.25f );
        buttonMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                game.setScreen(new mainScreen(game));
            }
        });
        buttonAgain.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                game.setScreen(new GameScreen(players, game));
            }
        });
        t.addActor(buttonAgain);
        t.addActor(buttonMenu);
        uiStage.addActor(t);
        Gdx.input.setInputProcessor(uiStage);
    }

    @Override
    public void render (float dt) {
        cam.update();
        batch.enableBlending();
        batch.setProjectionMatrix(cam.combined);

        Gdx.gl.glClearColor(255, 255, 255, 255);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        MyShapeRenderer sr = new MyShapeRenderer();
        sr.setProjectionMatrix(cam.combined);
        field.drawOnBatch(dt, batch, sr);
        sr.dispose();
        uiStage.draw();
    }

    @Override
    public void dispose () {
        batch.dispose();
    }
}
