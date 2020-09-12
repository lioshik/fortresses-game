package lioshik.corporation.gameScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import java.awt.*;

public class GameScreen extends ScreenAdapter {
    public SpriteBatch batch;
    public PlayingField field;
    public GameRulesController rulesController;
    public OrthographicCamera cam;

    public int players;

    public GameScreen(int p) {
        players = p;
    }

    @Override
    public void show () {
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
                field.resetCheckedCell();
                rulesController.updateAvailableCells();
                if (field.anyCellTouched(screenX, screenY)) {
                    int[] cord = field.getTouchedCellCord(screenX, screenY);
                    rulesController.cellTouched(cord[0], cord[1]);
                }
                return true;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                Vector3 touchVector = cam.unproject(new Vector3(screenX, screenY, 0));
                screenX = (int) touchVector.x;
                screenY = (int) touchVector.y;
                field.resetCheckedCell();
                rulesController.updateAvailableCells();
                if (field.anyCellTouched(screenX, screenY)) {
                    int[] cord = field.getTouchedCellCord(screenX, screenY);
                    field.checkCell(cord[0], cord[1]);
                }
                return true;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                Vector3 touchVector = cam.unproject(new Vector3(screenX, screenY, 0));
                screenX = (int) touchVector.x;
                screenY = (int) touchVector.y;
                field.resetCheckedCell();
                rulesController.updateAvailableCells();
                if (field.anyCellTouched(screenX, screenY)) {
                    int[] cord = field.getTouchedCellCord(screenX, screenY);
                    field.checkCell(cord[0], cord[1]);
                }
                return true;
            }
        });
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
    }

    @Override
    public void dispose () {
        batch.dispose();
    }
}
