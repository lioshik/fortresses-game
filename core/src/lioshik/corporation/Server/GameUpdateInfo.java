package lioshik.corporation.Server;

import lioshik.corporation.gameScreen.Cell;
import lioshik.corporation.gameScreen.GameRulesController;
import lioshik.corporation.gameScreen.PlayingField;

public class GameUpdateInfo {
    public Cell.ColorState[][] state;
    public int whichTurn;
    public int oneTurnCount;
    public GameUpdateInfo() {}

    public GameUpdateInfo(PlayingField field, GameRulesController rulesController) {
        state = new Cell.ColorState[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                state[i][j] = field.cellArray.get(i).get(j).state;
            }
        }
        whichTurn = rulesController.whichTurn;
        oneTurnCount = rulesController.oneTurnCount;
    }
}
