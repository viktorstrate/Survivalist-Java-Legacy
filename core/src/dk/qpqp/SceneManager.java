package dk.qpqp;

import dk.qpqp.scenes.game.GameScene;
import dk.qpqp.scenes.Scene;

/**
 * Created by viktorstrate on 03/08/2015.
 * Handles the different scenes in the game
 */
public class SceneManager extends Scene {


    private Scene activeScene;

    public SceneManager() {
        activeScene = new GameScene();
    }

    @Override
    public void render(){
        activeScene.render();
    }

    @Override
    public void update(float dt){
        activeScene.update(dt);
    }

    @Override
    public void resize(int width, int height) {
        activeScene.resize(width, height);
    }

    @Override
    public void dispose() {
        activeScene.dispose();
    }
}
