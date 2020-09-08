package lioshik.corporation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

public class TextureContainer {
    static Texture cellEmpty = new Texture(Gdx.files.internal("texture_empty_cell.png"));
    static Texture cellColor1 = new Texture(Gdx.files.internal("texture_color1_cell.png"));
    static Texture cellColor2 = new Texture(Gdx.files.internal("texture_color2_cell.png"));
    static Texture cellColor3 = new Texture(Gdx.files.internal("texture_color3_cell.png"));
    static Texture cellColor1locked = new Texture(Gdx.files.internal("texture_color1_locked_cell.png"));
    static Texture cellColor2locked = new Texture(Gdx.files.internal("texture_color2_locked_cell.png"));
    static Texture cellColor3locked = new Texture(Gdx.files.internal("texture_color3_locked_cell.png"));
    static final Color color1 = new Color(50 / 255f, 215 / 255f, 76 / 255f, 255 / 255f);
    static final Color color1locked = new Color(40 / 255f, 150 / 255f, 68 / 255f, 255 / 255f);
    static final Color color2 = new Color(255 / 255f, 204 / 255f, 0, 255);
    static final Color color2locked = new Color(227 / 255f, 155 / 255f, 0, 255);
    static final Color color3 = new Color(10 / 255f, 132 / 255f, 255 / 255f, 255 / 255f);
    static final Color color3locked = new Color(21 / 255f, 110 / 255f, 193 / 255f, 255 / 255f);
}
