package lioshik.corporation;

import com.badlogic.gdx.Game;
import lioshik.corporation.gameScreen.GameScreen;
import lioshik.corporation.mainMenuScreen.mainScreen;

public class MyGdxGame extends Game {
	
	@Override
	public void create () {
		setScreen(new mainScreen());
	}
	
	@Override
	public void dispose () {
	}
}
