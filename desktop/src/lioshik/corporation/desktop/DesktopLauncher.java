package lioshik.corporation.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import lioshik.corporation.MyGdxGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = 400;
		config.width = 600;
		config.x = 300;
		config.y = 100;
		new LwjglApplication(new MyGdxGame(), config);
	}
}
