package lioshik.corporation.gameAI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.utils.Null;
import lioshik.corporation.gameScreen.Cell;
import lioshik.corporation.gameScreen.GameRulesController;
import lioshik.corporation.gameScreen.GameScreen;
import lioshik.corporation.gameScreen.PlayingField;

import java.awt.*;
import java.util.*;
import java.util.List;

public class gameAI {
    PlayingField field;
    GameScreen gameScreen;
    GameRulesController rulesController;
    static InputProcessor processor = null;
    Thread t;
    Cell.ColorState crColorLocked;
    Cell.ColorState crColor;
    int whichTurn;

    public gameAI(PlayingField field, final GameRulesController rulesController, GameScreen gameScreen) {
        this.field = field;
        this.gameScreen = gameScreen;
        this.rulesController = rulesController;
        processor = gameScreen.inputAdapter;
        t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.currentThread().sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                List<List<Integer>> weights = new ArrayList<>();
                weights = new ArrayList<List<Integer>>();
                for (int i = 0; i < 10; i++) {
                    weights.add(new ArrayList<Integer>());
                    for (int j = 0; j < 10; j++) {
                        weights.get(i).add(j);
                    }
                }
                initWeights(weights, crColorLocked, crColor);
                int turns = 0;
                while (turns < 3) {
                    turns++;
                    if (turns == 3 || rulesController.whichTurn != whichTurn) {
                        Gdx.input.setInputProcessor(processor);
                    }
                    int[] turnCord = chooseMaxPos(weights);
                    if (turns == 3 || rulesController.whichTurn != whichTurn) {
                        Gdx.input.setInputProcessor(processor);
                    }
                    initWeights(weights, crColorLocked, crColor);
                    increaseNearbyCells(turnCord[0], turnCord[1], weights);
                    try {
                        Thread.currentThread().sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void stop() {
        try {
            t.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Gdx.input.setInputProcessor(processor);
    }

    public void makeTurn(final Cell.ColorState crColor, final Cell.ColorState crColorLocked, final int whichTurn) {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                gameScreen.uiStage.touchUp(screenX, screenY, pointer, button);
                return true;
            }
            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                gameScreen.uiStage.touchDragged(screenX, screenY, pointer);
                return true;
            }
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                gameScreen.uiStage.touchDown(screenX, screenY, pointer, button);
                return true;
            }
        });
        this.crColor = crColor;
        this.crColorLocked = crColorLocked;
        this.whichTurn = whichTurn;
        t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.currentThread().sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                List<List<Integer>> weights = new ArrayList<>();
                weights = new ArrayList<List<Integer>>();
                for (int i = 0; i < 10; i++) {
                    weights.add(new ArrayList<Integer>());
                    for (int j = 0; j < 10; j++) {
                        weights.get(i).add(j);
                    }
                }
                initWeights(weights, crColorLocked, crColor);
                int turns = 0;
                while (turns < 3) {
                    turns++;
                    if (turns == 3 || rulesController.whichTurn != whichTurn) {
                        Gdx.input.setInputProcessor(processor);
                    }
                    int[] turnCord = chooseMaxPos(weights);
                    if (turns == 3 || rulesController.whichTurn != whichTurn) {
                        Gdx.input.setInputProcessor(processor);
                    }
                    initWeights(weights, crColorLocked, crColor);
                    //increaseNearbyCells(turnCord[0], turnCord[1], weights);
                    try {
                        Thread.currentThread().sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Thread.currentThread().interrupt();
            }
        });
        t.start();
    }

    public void initWeights(List<List<Integer>> weights, Cell.ColorState crColorLocked, Cell.ColorState crColor){
        Random rnd = new Random();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (rulesController.cellAvailableForColor(i, j, crColorLocked, crColor)) {
                    if (field.cellArray.get(i).get(j).state == Cell.ColorState.COLOR1) {
                        weights.get(i).set(j, 20 + 100000);
                    } else if (field.cellArray.get(i).get(j).state == Cell.ColorState.EMPTY) {
                        weights.get(i).set(j, 0 + 100000);
                    } else {
                        weights.get(i).set(j, 10 + 100000);
                    }
                    weights.get(i).set(j, weights.get(i).get(j) - 2 * distToGreenCell(i, j));
                    weights.get(i).set(j, weights.get(i).get(j) - distToAliveCell(i, j, whichTurn));
                } else {
                    weights.get(i).set(j, -100000);
                }
            }
        }
    }

    public void increaseNearbyCells(int x, int y, List<List<Integer>> weights){
        for (int addX = -1; addX < 2; addX++) {
            for (int addY = -1; addY < 2; addY++) {
                if (addX == 0 && addY == 0) continue;
                int newX = x + addX;
                int newY = y + addY;
                if (newX < 0 || newY < 0 || newX >= 10 || newY >= 10) continue;
                weights.get(newX).set(newY, weights.get(newX).get(newY) + 1);
            }
        }
    }

    public int[] chooseMaxPos(List<List<Integer>> weights){
        List<int[]> res = new ArrayList<>();
        int mxv = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++){
                if (weights.get(i).get(j) > mxv) {
                    mxv = weights.get(i).get(j);
                    res.clear();
                    res.add(new int[] {i, j});
                } else if (weights.get(i).get(j) == mxv) {
                    res.add(new int[] {i, j});
                }
            }
        }
        if (res.size() != 0) {
            int pos = new Random().nextInt(res.size());
            rulesController.cellTouched(res.get(pos)[0], res.get(pos)[1]);
            return res.get(pos);
        } else {
            return new int[] {-100, -100};
        }
    }

    public int distToGreenCell(int x, int y) {
        Queue<int[]> qu = new ArrayDeque<>();
        Map<int[], Integer> dist = new TreeMap<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[0] == o2[0] && o1[1] == o2[1]) return 0;
                if (o1[0] == o2[0]) return o1[1] - o2[1];
                return o1[0] - o2[0];
            }});
        qu.offer(new int[] {x, y});
        boolean used[][] = new boolean[10][10];
        used[x][y] = true;
        dist.put(new int[]{x, y}, 0);
        while (!qu.isEmpty()) {
            int crX = qu.peek()[0];
            int crY = qu.peek()[1];
            if (field.cellArray.get(crX).get(crY).state == Cell.ColorState.COLOR1) return dist.get(new int[]{crX, crY});
            qu.remove();
            for (int addX = -1; addX < 2; addX++) {
                for (int addY = -1; addY < 2; addY++) {
                    if (addX == 0 && addY == 0) continue;
                    int newX = crX + addX;
                    int newY = crY + addY;
                    if (newX < 0 || newY < 0 || newX >= 10 || newY >= 10) continue;
                    if (!used[newX][newY]) {
                        used[newX][newY] = true;
                        qu.offer(new int[] {newX, newY});
                        dist.put(new int[]{newX, newY}, dist.get(new int[]{crX, crY}) + 1);
                    }
                }
            }
        }
        return 0;
    }

    public int distToAliveCell(int x, int y, int whichTurn) {
        Queue<int[]> qu = new ArrayDeque<>();
        Map<int[], Integer> dist = new TreeMap<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[0] == o2[0] && o1[1] == o2[1]) return 0;
                if (o1[0] == o2[0]) return o1[1] - o2[1];
                return o1[0] - o2[0];
            }});
        qu.offer(new int[] {x, y});
        boolean used[][] = new boolean[10][10];
        used[x][y] = true;
        dist.put(new int[]{x, y}, 0);
        while (!qu.isEmpty()) {
            int crX = qu.peek()[0];
            int crY = qu.peek()[1];
            Cell.ColorState crState = field.cellArray.get(crX).get(crY).state;
            if (whichTurn == 1 && crState == Cell.ColorState.COLOR2) return dist.get(new int[]{crX, crY});
            if (whichTurn == 2 && crState == Cell.ColorState.COLOR3) return dist.get(new int[]{crX, crY});
            if (whichTurn == 3 && crState == Cell.ColorState.COLOR4) return dist.get(new int[]{crX, crY});
            qu.remove();
            for (int addX = -1; addX < 2; addX++) {
                for (int addY = -1; addY < 2; addY++) {
                    if (addX == 0 && addY == 0) continue;
                    int newX = crX + addX;
                    int newY = crY + addY;
                    if (newX < 0 || newY < 0 || newX >= 10 || newY >= 10) continue;
                    if (!used[newX][newY]) {
                        used[newX][newY] = true;
                        qu.offer(new int[] {newX, newY});
                        dist.put(new int[]{newX, newY}, dist.get(new int[]{crX, crY}) + 1);
                    }
                }
            }
        }
        return 0;
    }
}
