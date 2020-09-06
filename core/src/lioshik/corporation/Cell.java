package lioshik.corporation;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Cell extends Sprite {
    enum ColorState {
        EMPTY,
        COLOR1,
        COLOR21Locked
    }
    public ColorState state;

    public Cell(float posX, float posY, float size) {
        super(TextureContainer.cellEmpty);
        prevTexture = this.getTexture();
        state = ColorState.EMPTY;
        setBounds(posX, posY, size, size);
    }

    private Texture prevTexture;
    private float animPaintDuration = 0.1f;
    private float crAnimPaintDuration = 2.0f;

    private void drawAnimPaint(SpriteBatch batch) {
        Sprite background = new Sprite(this);
        background.setTexture(prevTexture);
        background.draw(batch);
        //this.setOrigin(this.getBoundingRectangle().x + this.getBoundingRectangle().width / 2, this.getBoundingRectangle().y + this.getBoundingRectangle().height / 2);
        Rectangle realBounds = this.getBoundingRectangle();
        Rectangle newBounds = new Rectangle(realBounds);
        float rectScale = crAnimPaintDuration / animPaintDuration;
        newBounds.x = newBounds.x + newBounds.width * (1.0f - rectScale) / 2;
        newBounds.y = newBounds.y + newBounds.height * (1.0f - rectScale) / 2;
        newBounds.width *= rectScale;
        newBounds.height *= rectScale;
        this.setBounds(newBounds.x, newBounds.y, newBounds.width, newBounds.height);
        this.draw(batch);
        this.setBounds(realBounds.x, realBounds.y, realBounds.width, realBounds.height);
    }

    public void changeColor(ColorState newState) {
        state = newState;
        prevTexture = this.getTexture();
        switch (newState) {
            case EMPTY:
                this.setTexture(TextureContainer.cellEmpty);
                break;
            case COLOR1:
                this.setTexture(TextureContainer.cellColor1);
                break;
            case COLOR21Locked:
                this.setTexture(TextureContainer.cellColor1locked);
                break;
        }
        crAnimPaintDuration = 0.0f;
    }

    public void update(float dt, SpriteBatch batch) {
        if (crAnimPaintDuration < animPaintDuration) {
            crAnimPaintDuration += dt;
            drawAnimPaint(batch);
        } else {
            draw(batch);
        }
    }
}
