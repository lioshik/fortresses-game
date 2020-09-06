package lioshik.corporation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class TextureContainer {
    static Texture cellEmpty = new Texture(Gdx.files.internal("texture_empty_cell.png"));
    static Texture cellColor1 = new Texture(Gdx.files.internal("texture_color1_cell.png"));
    static Texture cellColor1locked = new Texture(Gdx.files.internal("texture_color1_locked_cell.png"));
}
