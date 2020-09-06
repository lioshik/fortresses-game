package lioshik.corporation;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PlayingField {
    public List<List<Cell>> cellArray;
    public int widthCount, heightCount;

    public PlayingField(int widthCount, int heightCount, Rectangle rect) {
        this.widthCount = widthCount;
        this.heightCount = heightCount;
        float cellSize = (float) Math.min(rect.getWidth() / widthCount, rect.getHeight() / heightCount);
        float cornerX = (float) (rect.x + (rect.getWidth() - (cellSize * widthCount)) / 2.0d);
        float cornerY = (float) (rect.y + (rect.getHeight() - (cellSize * heightCount)) / 2.0d);
        cellArray = new ArrayList<List<Cell>>();
        for (int i = 0; i < widthCount; i++) {
            cellArray.add(new ArrayList<Cell>(heightCount));
            for (int j = 0; j < heightCount; j++) {
                cellArray.get(i).add(new Cell(cornerX + cellSize * i, cornerY + cellSize * j, cellSize));
            }
        }
    }

    public void drawOnBatch(float dt, SpriteBatch batch) {
        for (int i = 0; i < widthCount; i++) {
            for (int j = 0; j < heightCount; j++) {
                cellArray.get(i).get(j).update(dt, batch);
            }
        }
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
