package lioshik.corporation.gameScreen;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.Color;

import java.util.*;
import java.util.List;

public class PlayingField {
    public List<List<Cell>> cellArray;
    public Set<int[]> checkedSet;
    public int widthCount, heightCount;
    public Rectangle tableRectangle;
    private float lineWidth;
    public float targetLineScale = 0.0f;
    public float crLineScale = 0.0f;
    public Cell.ColorState crLineColorState = Cell.ColorState.COLOR1;
    public Cell.ColorState nextLineColorState;

    public PlayingField(int widthCount, int heightCount, Rectangle rect) {
        checkedSet = new TreeSet<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[0] == o2[0] && o1[1] == o2[1]) return 0;
                if (o1[0] == o2[0]) return o1[1] - o2[1];
                return o1[0] - o2[0];
            }
        });
        this.widthCount = widthCount;
        this.heightCount = heightCount;
        float cellSize = (float) Math.min(rect.getWidth() / widthCount, rect.getHeight() / heightCount);
        float cellDist = cellSize * 0.05f;
        cellSize *= 0.8;
        lineWidth = (float) (Math.min((rect.getWidth() - (cellSize * widthCount + cellDist * (widthCount - 1))) / 2.0d, (rect.getHeight() - (cellSize * heightCount + cellDist * (heightCount - 1))) / 2.0d));
        float linePadding = lineWidth * 0.4f;
        lineWidth -= linePadding;
        float cornerX = (float) (rect.x + (rect.getWidth() - (cellSize * widthCount + cellDist * (widthCount - 1))) / 2.0d);
        float cornerY = (float) (rect.y + (rect.getHeight() - (cellSize * heightCount + cellDist * (heightCount - 1))) / 2.0d);
        tableRectangle = new Rectangle((int) (cornerX - linePadding), (int) (cornerY - linePadding), (int) (cellSize * widthCount + cellDist * (widthCount - 1) + linePadding * 2), (int) (cellSize * heightCount + cellDist * (heightCount - 1) + linePadding * 2));
        cellArray = new ArrayList<List<Cell>>();
        for (int i = 0; i < widthCount; i++) {
            cellArray.add(new ArrayList<Cell>(heightCount));
            for (int j = 0; j < heightCount; j++) {
                cellArray.get(i).add(new Cell(cornerX + cellSize * i + cellDist * (i - 1), cornerY + cellSize * j + + cellDist * (j - 1), cellSize));
            }
        }
    }

    private int crClickedX = -1;
    private int crClickedY = -1;

    public void clickCell(int x, int y) {
        if (x == crClickedX && y == crClickedY) return;
        if (crClickedX != -1) {
            cellArray.get(crClickedX).get(crClickedY).startClickDownAnim();
        }
        cellArray.get(x).get(y).startClickUpAnim();
        crClickedX = x;
        crClickedY = y;
    }

    public void resetClickedCell() {
        if (crClickedX != -1) cellArray.get(crClickedX).get(crClickedY).startClickDownAnim();
        crClickedX = -1;
        crClickedY = -1;
    }

    public void checkCell(int x, int y) {
        checkedSet.add(new int[]{x, y});
        cellArray.get(x).get(y).startUpAnimation();
    }

    public void resetCheckedCell() {

        Iterator<int[]> it = checkedSet.iterator();
        while (it.hasNext()) {
            int[] cr = it.next();
            cellArray.get(cr[0]).get(cr[1]).startDownAnimation();
        }
        checkedSet.clear();
    }

    private float speedLineMove = 1f / 3f * 6;
    private void moveLine(float dt) {
        if (crLineScale < targetLineScale) {
            crLineScale += speedLineMove * dt;
            crLineScale = Math.min(crLineScale, targetLineScale);
        } else if (targetLineScale == 0 && crLineScale != 0){
            crLineScale += speedLineMove * dt;
            if (crLineScale > 1.0f) {
                crLineScale = 0;
                crLineColorState = nextLineColorState;
            }
        }
    }

    private Color getColorByState(Cell.ColorState state) {
        switch (state) {
            case COLOR1:
                return TextureContainer.color1;
            case COLOR2:
                return TextureContainer.color2;
            case COLOR3:
                return TextureContainer.color3;
            case COLOR4:
                return TextureContainer.color4;
        }
        // never reached
        return null;
    }
    private Color getLockedColorByState(Cell.ColorState state) {
        switch (state) {
            case COLOR1:
                return TextureContainer.color1locked;
            case COLOR2:
                return TextureContainer.color2locked;
            case COLOR3:
                return  TextureContainer.color3locked;
            case COLOR4:
                return TextureContainer.color4locked;
        }
        // never reached
        return null;
    }

    public void drawOnBatch(float dt, SpriteBatch batch, MyShapeRenderer sr) {
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(getColorByState(crLineColorState));
        sr.roundedRect(tableRectangle, lineWidth, 1.0f);
        sr.end();
        moveLine(dt);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(getLockedColorByState(crLineColorState));
        sr.roundedRect(tableRectangle, lineWidth, crLineScale);
        sr.end();
        batch.begin();
        for (int i = 0; i < widthCount; i++) {
            for (int j = 0; j < heightCount; j++) {
                if (checkedSet.contains(new int[] {i, j}) || i == crClickedX && j == crClickedY) continue;
                Cell crCell = cellArray.get(i).get(j);
                crCell.update(dt, batch);
            }
        }
        Iterator<int[]> it = checkedSet.iterator();
        while (it.hasNext()) {
            int[] cr = it.next();
            if (cr.equals(new int[] {crClickedX, crClickedY})) continue;
            cellArray.get(cr[0]).get(cr[1]).update(dt, batch);
        }
        if (crClickedX != -1) {
            cellArray.get(crClickedX).get(crClickedY).update(dt, batch);
        }
        batch.end();
    }

    public boolean anyCellTouched(int screenX, int screenY) {
        for (List<Cell> lst : cellArray) {
            for (Cell c : lst) {
                if (c.getBoundingRectangle().contains(new Vector2(screenX, screenY))) {
                    return true;
                }
            }
        }
        return false;
    }

    public int[] getTouchedCellCord(int screenX, int screenY) {
        for (int i = 0; i < widthCount; i++) {
            for (int j = 0; j < heightCount; j++) {
                if (cellArray.get(i).get(j).getBoundingRectangle().contains(new Vector2(screenX, screenY))){
                    return new int[] {i, j};
                }
            }
        }
        // never reached
        return new int[] {0, 0};
    }
}
