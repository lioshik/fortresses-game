package lioshik.corporation;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.Color;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PlayingField {
    public List<List<Cell>> cellArray;
    public int widthCount, heightCount;
    private Rectangle tableRectangle;
    private float lineWidth;
    public float targetLineScale = 0.0f;
    private float crLineScale = 0.0f;
    public Cell.ColorState crLineColorState = Cell.ColorState.COLOR1;
    public Cell.ColorState nextLineColorState;

    public PlayingField(int widthCount, int heightCount, Rectangle rect) {
        this.widthCount = widthCount;
        this.heightCount = heightCount;
        float cellSize = (float) Math.min(rect.getWidth() / widthCount, rect.getHeight() / heightCount);
        cellSize *= 0.9;
        lineWidth = (float) (Math.min((rect.getWidth() - (cellSize * widthCount)) / 2.0d, (rect.getHeight() - (cellSize * heightCount)) / 2.0d));
        float linePadding = lineWidth * 0.4f;
        lineWidth -= linePadding;
        float cornerX = (float) (rect.x + (rect.getWidth() - (cellSize * widthCount)) / 2.0d);
        float cornerY = (float) (rect.y + (rect.getHeight() - (cellSize * heightCount)) / 2.0d);
        tableRectangle = new Rectangle((int) (cornerX - linePadding), (int) (cornerY - linePadding), (int) (cellSize * widthCount + linePadding * 2), (int) (cellSize * heightCount + linePadding * 2));
        cellArray = new ArrayList<List<Cell>>();
        for (int i = 0; i < widthCount; i++) {
            cellArray.add(new ArrayList<Cell>(heightCount));
            for (int j = 0; j < heightCount; j++) {
                cellArray.get(i).add(new Cell(cornerX + cellSize * i, cornerY + cellSize * j, cellSize));
            }
        }
    }
    private int checkedCellX = -1;
    private int checkedCellY = -1;

    public void checkCell(int x, int y) {
        if (x == checkedCellX && y == checkedCellY) return;
        resetCheckedCell();
        if (checkedCellY != -1) {
            cellArray.get(checkedCellX).get(checkedCellY).startDownAnimation();
        }
        checkedCellX = x;
        checkedCellY = y;
        cellArray.get(x).get(y).startUpAnimation();
    }

    public void resetCheckedCell() {
        if (checkedCellY != -1) {
            cellArray.get(checkedCellX).get(checkedCellY).startDownAnimation();
        }
        checkedCellY = -1;
        checkedCellX = -1;
    }

    private float speedLineMove = 1f / 3f * 15;
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
                if (i == checkedCellX && j == checkedCellY) continue;
                Cell crCell = cellArray.get(i).get(j);
                crCell.update(dt, batch);
            }
        }
        if (checkedCellY != -1) {
            int i = checkedCellX;
            int j = checkedCellY;
            Cell crCell = cellArray.get(i).get(j);
            crCell.update(dt, batch);
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
