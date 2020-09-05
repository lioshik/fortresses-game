package lioshik.corporation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Cell extends Sprite {
    enum State {
        EMPTY,
        COLOR1,
        COLOR2
    }
    public State state;
    public Cell(float posX, float posY, float size) {
        super(new Texture(Gdx.files.internal("texture_empty_cell.png")));
        state = State.EMPTY;
        setBounds(posX, posY, size, size);
    }
}
