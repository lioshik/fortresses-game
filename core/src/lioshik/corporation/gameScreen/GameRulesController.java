package lioshik.corporation.gameScreen;

import com.badlogic.gdx.Gdx;
import lioshik.corporation.gameAI.gameAI;

import java.util.ArrayDeque;
import java.util.Queue;

public class GameRulesController {
    GameScreen game;

    public int whichTurn = 0;
    public int lastTurn = 0;
    private boolean[] firstTurn;
    private boolean[] isPlayer;
    public int oneTurnCount= 0;
    public int playersCount;
    public gameAI gameAI;
    public boolean enableAI = true;
    public final Cell.ColorState[] colors = {Cell.ColorState.COLOR1, Cell.ColorState.COLOR2, Cell.ColorState.COLOR3, Cell.ColorState.COLOR4};

    public GameRulesController(GameScreen game, int playersCount, int bots) {
        this.game = game;
        this.playersCount = playersCount;
        firstTurn = new boolean[playersCount];
        isPlayer = new boolean[playersCount];
        for (int i = 0; i < playersCount; i++) firstTurn[i] = true;
        for (int i = 0; i < playersCount - bots; i++) isPlayer[i] = true;
        updateAvailableCells();
        gameAI = new gameAI(game.field, this, game);
    }

    public void cellTouched(int x, int y) {
        // will be initialized anyway
        Cell.ColorState crLockedColor = null, crTargetColor = null;

        switch (whichTurn) {
            case 0:
                crLockedColor = Cell.ColorState.COLOR1Locked;
                crTargetColor = Cell.ColorState.COLOR1;
                break;
            case 1:
                crLockedColor = Cell.ColorState.COLOR2Locked;
                crTargetColor = Cell.ColorState.COLOR2;
                break;
            case 2:
                crLockedColor = Cell.ColorState.COLOR3Locked;
                crTargetColor = Cell.ColorState.COLOR3;
                break;
            case 3:
                crLockedColor = Cell.ColorState.COLOR4Locked;
                crTargetColor = Cell.ColorState.COLOR4;
                break;
        }
        if (cellAvailableForColor(x, y, crLockedColor, crTargetColor)) {
            lastTurn = whichTurn;
            firstTurn[whichTurn] = false;
            if (game.field.cellArray.get(x).get(y).state == Cell.ColorState.EMPTY) {
                game.field.cellArray.get(x).get(y).changeColor(crTargetColor);
            } else {
                game.field.cellArray.get(x).get(y).changeColor(crLockedColor);
            }
            oneTurnCount++;
            game.field.targetLineScale = 1 / 3.0f * oneTurnCount;
            if (oneTurnCount == 3) {
                whichTurn = (whichTurn + 1) % playersCount;
                game.field.targetLineScale = 0.0f;
                oneTurnCount = 0;
                game.field.nextLineColorState = colors[whichTurn];
            }
            int skipped = 0;
            while (!updateAvailableCells() && skipped < playersCount - 1) {
                oneTurnCount = 0;
                whichTurn = (whichTurn + 1) % playersCount;
                if (game.field.crLineScale != 0) {
                    game.field.nextLineColorState = colors[whichTurn];
                } else {
                    game.field.crLineColorState = colors[whichTurn];
                }
                game.field.targetLineScale = 0;
                skipped++;
            }
            if (skipped == playersCount - 1) {
                game.gameEndDialog(whichTurn);
                enableAI = false;
            }
        } else {
            game.field.cellArray.get(x).get(y).startShakeAnim();
        }
        switch (whichTurn) {
            case 0:
                crLockedColor = Cell.ColorState.COLOR1Locked;
                crTargetColor = Cell.ColorState.COLOR1;
                break;
            case 1:
                crLockedColor = Cell.ColorState.COLOR2Locked;
                crTargetColor = Cell.ColorState.COLOR2;
                break;
            case 2:
                crLockedColor = Cell.ColorState.COLOR3Locked;
                crTargetColor = Cell.ColorState.COLOR3;
                break;
            case 3:
                crLockedColor = Cell.ColorState.COLOR4Locked;
                crTargetColor = Cell.ColorState.COLOR4;
                break;
        }
        if (!isPlayer[whichTurn] && oneTurnCount == 0 && enableAI) {
            gameAI.makeTurn(crTargetColor, crLockedColor, whichTurn);
        }
    }

    public void checkGameEnd(){
        int skipped = 0;
        while (!updateAvailableCells() && skipped < playersCount - 1) {
            oneTurnCount = 0;
            whichTurn = (whichTurn + 1) % playersCount;
            if (game.field.crLineScale != 0) {
                game.field.nextLineColorState = colors[whichTurn];
            } else {
                game.field.crLineColorState = colors[whichTurn];
            }
            game.field.targetLineScale = 0;
            skipped++;
        }
        if (skipped == playersCount - 1) {
            game.gameEndDialog(lastTurn);
        }
    }

    boolean updateAvailableCells() {
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                game.field.resetCheckedCell();
            }
        });
        Cell.ColorState crLockedColor = null, crTargetColor = null;
        boolean returnValue = false;
        switch (whichTurn) {
            case 0:
                crLockedColor = Cell.ColorState.COLOR1Locked;
                crTargetColor = Cell.ColorState.COLOR1;
                break;
            case 1:
                crLockedColor = Cell.ColorState.COLOR2Locked;
                crTargetColor = Cell.ColorState.COLOR2;
                break;
            case 2:
                crLockedColor = Cell.ColorState.COLOR3Locked;
                crTargetColor = Cell.ColorState.COLOR3;
                break;
            case 3:
                crLockedColor = Cell.ColorState.COLOR4Locked;
                crTargetColor = Cell.ColorState.COLOR4;
                break;
        }
        for (int x = 0; x < game.field.widthCount; x++) {
            for (int y = 0; y < game.field.heightCount; y++) {
                if (cellAvailableForColor(x, y, crLockedColor, crTargetColor)) {
                    if (isPlayer[whichTurn]) {
                        final int finalX = x;
                        final int finalY = y;
                        Gdx.app.postRunnable(new Runnable() {
                            @Override
                            public void run() {
                                game.field.checkCell(finalX, finalY);
                            }
                        });
                    }
                    returnValue = true;
                }
            }
        }
        return returnValue;
    }

    public boolean cellAvailableForColor(int x, int y, Cell.ColorState lockedColor, Cell.ColorState targetColor) {
        if ((firstTurn[whichTurn] && (x == 0 || x == game.field.widthCount - 1) && (y == 0 || y == game.field.heightCount - 1) && game.field.cellArray.get(x).get(y).state == Cell.ColorState.EMPTY)) {
            return true;
        }
        Cell crCell = game.field.cellArray.get(x).get(y);
        if (crCell.state == lockedColor || crCell.state == targetColor || crCell.isLocked()) return false;
        Queue<int[]> qu = new ArrayDeque<>();
        qu.offer(new int[] {x, y});
        boolean used[][] = new boolean[game.field.widthCount][game.field.heightCount];
        used[x][y] = true;
        while (!qu.isEmpty()) {
            int crX = qu.peek()[0];
            int crY = qu.peek()[1];
            qu.remove();
            crCell = game.field.cellArray.get(crX).get(crY);

            if (crCell.state == targetColor) return true;
            if (!(crX == x && crY == y) && crCell.state != lockedColor) continue;

            for (int addX = -1; addX < 2; addX++) {
                for (int addY = -1; addY < 2; addY++) {
                    if (addX == 0 && addY == 0) continue;
                    int newX = crX + addX;
                    int newY = crY + addY;
                    if (newX < 0 || newX >= game.field.widthCount || newY < 0 || newY >= game.field.heightCount) continue;
                    if (!used[newX][newY]) {
                        used[newX][newY] = true;
                        qu.offer(new int[] {newX, newY});
                    }
                }
            }
        }
        return false;
    }
}
