package dk.qpqp;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class Game extends ApplicationAdapter {
    public static final float SCALE = 2.5f;
    //    public static final float SCALE = 1f;
    public static final int WIDTH = 1080;
	public static final int HEIGHT = 720;

	private SceneManager sceneManager;

	@Override
	public void create () {
		sceneManager = new SceneManager();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.25f, 0.2f, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		sceneManager.render();

        sceneManager.update(Gdx.graphics.getDeltaTime());
	}

    @Override
    public void resize(int width, int height){
        sceneManager.resize(width, height);
    }

	@Override
	public void dispose () {
		sceneManager.dispose();
	}

	@Override
	public void pause () {
		sceneManager.pause();
	}

	@Override
	public void resume () {
		sceneManager.resume();
	}
}
