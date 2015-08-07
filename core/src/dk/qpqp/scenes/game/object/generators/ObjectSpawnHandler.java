package dk.qpqp.scenes.game.object.generators;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.qpqp.scenes.game.GameScene;

import java.util.ArrayList;

/**
 * Created by viktorstrate on 05/08/2015.
 * Handles spawning of {@link dk.qpqp.scenes.game.GameObject} like trees and rocks
 */
public class ObjectSpawnHandler {

    private ArrayList<ObjectGenerator> generators;

    public ObjectSpawnHandler(GameScene gameScene) {
        generators = new ArrayList<>();

        generators.add(new StoneGenerator(gameScene));
        generators.add(new TreeGenerator(gameScene, this));
    }

    public void render(SpriteBatch spriteBatch) {
        for (ObjectGenerator g : generators) {
            g.render(spriteBatch);
        }
    }

    public void update(float dt) {
        for (ObjectGenerator g : generators) {
            g.update(dt);
        }
    }

    public ArrayList<ObjectGenerator> getGenerators() {
        return generators;
    }
}
