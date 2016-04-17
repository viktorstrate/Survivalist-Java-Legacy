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
