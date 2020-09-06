package lioshik.corporation;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import java.awt.*;

public class MyGdxGame extends ApplicationAdapter {
	public SpriteBatch batch;
	public PlayingField field;
	public GameRulesController rulesController;
	public OrthographicCamera cam;
	
	@Override
	public void create () {
		rulesController = new GameRulesController(this, 2);
		cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
		batch = new SpriteBatch();
		field = new PlayingField(10, 10, new Rectangle(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		field.cellArray.get(0).get(0).changeColor(Cell.ColorState.COLOR1);
		field.cellArray.get(field.widthCount - 1).get(field.heightCount - 1).changeColor(Cell.ColorState.COLOR2);
		Gdx.input.setInputProcessor(new InputAdapter(){
			@Override
			public boolean touchUp(int screenX, int screenY, int pointer, int button) {
				Vector3 touchVector = cam.unproject(new Vector3(screenX, screenY, 0));
				screenX = (int) touchVector.x;
				screenY = (int) touchVector.y;
				field.resetCheckedCell();
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
				if (field.anyCellTouched(screenX, screenY)) {
					int[] cord = field.getTouchedCellCord(screenX, screenY);
					field.checkCell(cord[0], cord[1]);
				}
				return true;
			}
		});
	}

	@Override
	public void render () {
		float dt = Gdx.graphics.getDeltaTime();
		cam.update();
		batch.enableBlending();
		batch.setProjectionMatrix(cam.combined);
		Gdx.gl.glClearColor(255, 255, 255, 255);
		batch.begin();
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		field.drawOnBatch(dt, batch);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
