package lioshik.corporation;

public class GameRulesController {
    MyGdxGame game;

    public GameRulesController(MyGdxGame game) {
        this.game = game;
    }

    public void cellTouched(int x, int y){
        game.field.cellArray.get(x).get(y).changeColor(Cell.ColorState.COLOR1);
    }
}
