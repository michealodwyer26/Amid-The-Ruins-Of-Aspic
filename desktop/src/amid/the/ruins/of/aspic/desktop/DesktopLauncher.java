package amid.the.ruins.of.aspic.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import amid.the.ruins.of.aspic.Platformer;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Amid The Ruins Of Aspic";
		config.vSyncEnabled = true;
		config.foregroundFPS = 60;
		new LwjglApplication(new Platformer(), config);
	}
}
