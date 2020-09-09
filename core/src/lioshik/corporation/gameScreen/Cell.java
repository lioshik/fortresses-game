package lioshik.corporation.gameScreen;

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
        COLOR2Locked,
        COLOR3,
        COLOR3Locked
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

    private void transformAnimPaint(SpriteBatch batch) {
        if (crAnimPaintDuration >= animPaintDuration) {
            return;
        }
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
    }

    private float animShakeDuration = 0.3f;
    private float crAnimShakeDuration = 2.0f; // > animShakeDuration
    private int shakeCount = 6;

    private void transformShakeAnim(SpriteBatch batch) {
        if (crAnimShakeDuration >= animShakeDuration) {
            return;
        }
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
    }

    private float animUpDuration = 0.05f;
    private float crAnimUpDuration = 2.0f; // > animUpDuration
    private float animDownDuration = 0.05f;
    private float crAnimDownDuration = 2.0f; // > animDownDuration
    private boolean isUp = false;

    private void transformUpAnimation(SpriteBatch batch) {
        if (isUp) {
            float scale = Math.min(1.0f, crAnimUpDuration / animUpDuration);
            this.setPosition(this.getX() + this.getWidth() / 10.0f * scale, this.getY() + this.getHeight() / 10.0f * scale);
            this.setSize(this.getWidth()  + this.getWidth() * 0.1f * scale, this.getHeight() + this.getHeight()* 0.1f * scale);
        } else {
            float scale = Math.min(1.0f, crAnimDownDuration / animDownDuration);
            scale = 1.0f - scale;
            this.setPosition(this.getX() + this.getWidth() / 10.0f * scale, this.getY() + this.getHeight() / 10.0f * scale);
            this.setSize(this.getWidth() + this.getWidth() * 0.1f * scale, this.getHeight() + this.getHeight()* 0.1f * scale);
        }
    }

    public void startShakeAnim() {
        crAnimShakeDuration = 0.0f;
    }

    public void startUpAnimation() {
        if (!isUp) {
            isUp = true;
            crAnimUpDuration = 0.0f;
        }
    }

    public void startDownAnimation() {
        if (isUp) {
            isUp = false;
            crAnimDownDuration = 0.0f;
        }
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
            case COLOR3:
                this.setTexture(TextureContainer.cellColor3);
                break;
            case COLOR3Locked:
                this.setTexture(TextureContainer.cellColor3locked);
                break;
        }
        crAnimPaintDuration = 0.0f;
    }

    public void update(float dt, SpriteBatch batch) {
        crAnimPaintDuration += dt;
        crAnimShakeDuration += dt;
        crAnimDownDuration += dt;
        crAnimUpDuration += dt;
        Rectangle defaultBounds = this.getBoundingRectangle();
        transformAnimPaint(batch);
        transformShakeAnim(batch);
        transformUpAnimation(batch);
        draw(batch);
        this.setBounds(defaultBounds.x, defaultBounds.y, defaultBounds.width, defaultBounds.width);
    }
}
