package dk.qpqp.scenes.game.terrain.generators;

import dk.qpqp.scenes.game.terrain.objects.GameObject;
import dk.qpqp.scenes.game.GameScene;

/**
 * Created by viktorstrate on 05/08/2015.
 * Abstract class for {@link GameObject} generators
 */
public abstract class ObjectGenerator {

    protected GameScene gameScene;


    public ObjectGenerator(GameScene gameScene) {
        this.gameScene = gameScene;
    }

    /**
     * Generates a {@link GameObject}
     *
     * @return the generated {@link GameObject}
     */
    protected abstract GameObject generate();
}
