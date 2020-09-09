package lioshik.corporation.mainMenuScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import lioshik.corporation.gameScreen.TextureContainer;

public class mainScreen extends ScreenAdapter {
    public Stage stage;
    public ImageButton buttonPlay;

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        buttonPlay = new ImageButton(new TextureRegionDrawable(TextureContainer.buttonPlayUp), new TextureRegionDrawable(TextureContainer.buttonPlayDown));
        float scale = stage.getWidth() / 2.0f / buttonPlay.getWidth();
        buttonPlay.setWidth(buttonPlay.getWidth() * scale);
        buttonPlay.setHeight(buttonPlay.getHeight() * scale);
        buttonPlay.setPosition(stage.getWidth() / 2.0f - buttonPlay.getWidth() / 2.0f, stage.getHeight() / 2.0f - buttonPlay.getHeight() / 2.0f);
        stage.addActor(buttonPlay);
    }

    @Override
    public void render(float dt) {
        stage.draw();
    }

    @Override
    public void dispose() {

    }
}



