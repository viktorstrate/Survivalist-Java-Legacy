package dk.qpqp.scenes.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.qpqp.scenes.game.GameObject;
import dk.qpqp.scenes.game.GameScene;

import java.util.ArrayList;

/**
 * Created by viktorstrate on 05/08/2015.
 * Abstract class for {@link GameObject} generators
 */
public abstract class ObjectGenerator {

    protected ArrayList<GameObject> gameObjects;
    protected GameScene gameScene;

    public ObjectGenerator(GameScene gameScene) {
        gameObjects = new ArrayList<>();
        this.gameScene = gameScene;
    }

    public void render(SpriteBatch spriteBatch) {
        for (GameObject g : gameObjects) {
            g.render(spriteBatch);
        }
    }

    /**
     * Generates a {@link GameObject}
     *
     * @return generate successful
     */
    protected abstract boolean generate();

    ArrayList<GameObject> getGameObjects() {
        return gameObjects;
    }

    void update(float dt) {

    }

    void dispose() {
        gameObjects = null;
    }
}
