package lioshik.corporation;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Cell extends Sprite {
    enum ColorState {
        EMPTY,
        COLOR1,
        COLOR1Locked,
        COLOR2,
        COLOR2Locked
    }
    public ColorState state;
    public boolean isLocked() {
        return state == ColorState.COLOR2Locked || state == ColorState.COLOR1Locked;
    }

    public Cell(float posX, float posY, float size) {
        super(TextureContainer.cellEmpty);
        prevTexture = this.getTexture();
        state = ColorState.EMPTY;
        setBounds(posX, posY, size, size);
    }

    private Texture prevTexture;
    private float animPaintDuration = 0.1f;
    private float crAnimPaintDuration = 2.0f; // > animPaintDuration

    private void drawAnimPaint(SpriteBatch batch) {
        Sprite background = new Sprite(this);
        background.setTexture(prevTexture);
        background.draw(batch);
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

    private float animShakeDuration = 0.3f;
    private float crAnimShakeDuration = 2.0f; // > animPaintDuration
    private int shakeCount = 5;

    private void drawShakeAnim(SpriteBatch batch) {
        float oneShakeDur = animShakeDuration / shakeCount;
        int totalShakes = 0;
        for (; oneShakeDur * (totalShakes + 1) < crAnimShakeDuration ; totalShakes++) { }
        float addX;
        if (crAnimShakeDuration - oneShakeDur * totalShakes <= oneShakeDur / 2) {
            addX = this.getWidth() * 0.1f * (crAnimShakeDuration - oneShakeDur * totalShakes) / (oneShakeDur / 2);
        } else {
            addX = this.getWidth() * 0.1f * (1 - (crAnimShakeDuration - oneShakeDur * totalShakes - oneShakeDur / 2) / (oneShakeDur / 2));
        }
        if (totalShakes % 2 == 0) addX = -addX;
        this.setX(this.getX() + addX);
        this.draw(batch);
        this.setX(this.getX() - addX);
    }

    public void startShakeAnim() {
        crAnimShakeDuration = 0.0f;
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
            case COLOR1Locked:
                this.setTexture(TextureContainer.cellColor1locked);
                break;
            case COLOR2:
                this.setTexture(TextureContainer.cellColor2);
                break;
            case COLOR2Locked:
                this.setTexture(TextureContainer.cellColor2locked);
                break;
        }
        crAnimPaintDuration = 0.0f;
    }

    public void update(float dt, SpriteBatch batch) {
        crAnimPaintDuration += dt;
        crAnimShakeDuration += dt;
        if (crAnimPaintDuration < animPaintDuration) {
            drawAnimPaint(batch);
        } else if (crAnimShakeDuration < animShakeDuration) {
            drawShakeAnim(batch);
        } else {
                draw(batch);
        }
    }
}
