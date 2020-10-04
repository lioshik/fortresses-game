package lioshik.corporation;

import com.badlogic.gdx.Game;
import lioshik.corporation.gameScreen.GameScreen;
import lioshik.corporation.mainMenuScreen.mainScreen;
import lioshik.corporation.splashScreen.splashScreen;

public class MyGdxGame extends Game {
	
	@Override
	public void create () {
		setScreen(new splashScreen(this));
	}
	
	@Override
	public void dispose () {
	}
}
