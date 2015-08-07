package dk.qpqp.scenes.game.object.generators;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.qpqp.scenes.game.GameScene;

/**
 * Created by viktorstrate on 05/08/2015.
 * Handles spawning of {@link dk.qpqp.scenes.game.GameObject} like trees and rocks
 */
public class ObjectSpawnHandler {

    private StoneGenerator stoneGenerator;
    private TreeGenerator treeGenerator;

    public ObjectSpawnHandler(GameScene gameScene) {

        stoneGenerator = new StoneGenerator(gameScene);
        treeGenerator = new TreeGenerator(gameScene, this);
    }

    public void render(SpriteBatch spriteBatch) {
        stoneGenerator.render(spriteBatch);
        treeGenerator.render(spriteBatch);
    }

    public void update(float dt) {
        stoneGenerator.update(dt);
        treeGenerator.update(dt);
    }

    public StoneGenerator getStoneGenerator() {
        return stoneGenerator;
    }

    public TreeGenerator getTreeGenerator() {
        return treeGenerator;
    }
}
