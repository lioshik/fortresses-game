package lioshik.corporation.splashScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import lioshik.corporation.mainMenuScreen.mainScreen;

public class splashScreen extends ScreenAdapter {
    Game g;
    Stage uiStage;
    float timer = 1.5f;
    public  splashScreen(Game g){
        this.g = g;
        uiStage = new Stage();
    }

    @Override
    public void show() {
        Image i = new Image(new TextureRegion(new Texture(Gdx.files.internal("logo.png"))));
        i.setSize(uiStage.getWidth(), uiStage.getHeight());
        i.setAlign(Align.center);
        //i.setScaling(Scaling.);
        uiStage.addActor(i);
    }

    @Override
    public void render(float dt) {
        uiStage.draw();
        timer -= dt;
        if (timer <= 0) {
            g.setScreen(new mainScreen(g));
        }
    }
}
