package dk.qpqp.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import dk.qpqp.Game;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = Game.WIDTH;
        config.height = Game.HEIGHT;
		config.title = "Badass Survival game in 2D";
		new LwjglApplication(new Game(), config);
	}
}
