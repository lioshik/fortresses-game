package lioshik.corporation.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.esotericsoftware.kryonet.Server;
import lioshik.corporation.MyGdxGame;

import java.io.IOException;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.y = 0;
		config.resizable = false;
		config.width = 500;
		config.height = 700;
		new LwjglApplication(new MyGdxGame(), config);
	}
}
