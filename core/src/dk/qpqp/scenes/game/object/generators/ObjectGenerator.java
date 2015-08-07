package dk.qpqp.scenes.game.object.generators;

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

    private ArrayList<GameObject> objectsToRemove;

    public ObjectGenerator(GameScene gameScene) {
        gameObjects = new ArrayList<>();
        this.gameScene = gameScene;
        this.objectsToRemove = new ArrayList<>();
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

    public ArrayList<GameObject> getGameObjects() {
        return gameObjects;
    }

    public void update(float dt) {
        for (GameObject g : gameObjects) {
            g.update(dt);
        }

        for (GameObject g : objectsToRemove) {
            if (gameObjects.contains(g))
                gameObjects.remove(g);
        }

    }

    public void removeGameObject(GameObject gameObject) {
        objectsToRemove.add(gameObject);
    }

    void dispose() {
        gameObjects = null;
    }
}
