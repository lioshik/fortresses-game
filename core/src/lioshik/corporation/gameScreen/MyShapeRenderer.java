package lioshik.corporation.gameScreen;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class MyShapeRenderer extends ShapeRenderer {

    public void negativeArc(float x, float y, float radius, float start, float degrees) {
        if (degrees >= 0) {
            super.arc(x, y, radius, start, degrees);
            return;
        }
        super.arc(x, y, radius, (360 + start + degrees) % 360, -degrees);
    }

    public void roundedRect(Rectangle mainRect, float lineWidth, float lenScale) {
        float circleLen = (float) (Math.PI  * (lineWidth * 2));
        float totalLen = mainRect.width * 2 + mainRect.height * 2 + circleLen * 4;
        totalLen *= lenScale;
        float scale = 1.0f;
        // left
        if (totalLen < mainRect.height) scale = (totalLen / mainRect.height);
        super.rect(mainRect.x - lineWidth, mainRect.y, lineWidth, mainRect.height * scale);
        totalLen -= mainRect.height;
        if (scale != 1.0f) return;

        //left top
        if (totalLen < circleLen) scale = (totalLen / circleLen);
        negativeArc(mainRect.x, mainRect.y + mainRect.height, lineWidth, 180, -90 * scale);
        if (scale != 1.0f) return;
        totalLen -= circleLen;

        // top
        if (totalLen < mainRect.width) scale = (totalLen / mainRect.width);
        super.rect(mainRect.x, mainRect.y + mainRect.height, mainRect.width * scale, lineWidth);
        if (scale != 1.0f) return;
        totalLen -= mainRect.width;

        // arc right top
        if (totalLen < circleLen) scale = (totalLen / circleLen);
        negativeArc(mainRect.x + mainRect.width, mainRect.y + mainRect.height, lineWidth, 90, -90 * scale);
        if (scale != 1.0f) return;
        totalLen -= circleLen;

        // right
        if (totalLen < mainRect.height) scale = (totalLen / mainRect.height);
        super.rect(mainRect.x + mainRect.width, mainRect.y + mainRect.height * (1.0f - scale), lineWidth, mainRect.height * scale);
        if (scale != 1.0f) return;
        totalLen -= mainRect.height;

        // right bottom
        if (totalLen < circleLen) scale = (totalLen / circleLen);
        negativeArc(mainRect.x + mainRect.width, mainRect.y, lineWidth, 0, -90 * scale);
        if (scale != 1.0f) return;
        totalLen -= circleLen;

        // bottom
        if (totalLen < mainRect.width) scale = (totalLen / mainRect.width);
        super.rect(mainRect.x + mainRect.width * (1.0f - scale), mainRect.y - lineWidth, mainRect.width * scale, lineWidth);
        if (scale != 1.0f) return;
        totalLen -= mainRect.width;

        // left bottom
        if (totalLen < circleLen) scale = (totalLen / circleLen);
        negativeArc(mainRect.x, mainRect.y, lineWidth, 270, -90 * scale);
        if (scale != 1.0f) return;
        totalLen -= circleLen;
    }
}
