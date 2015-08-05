package dk.qpqp.scenes.game.objects;

import dk.qpqp.scenes.game.GameScene;

/**
 * Created by viktorstrate on 05/08/2015.
 * Randomly generates {@link Tree} objects in the world
 */
public class TreeGenerator extends ObjectGenerator {

    public TreeGenerator(GameScene gameScene) {
        super(gameScene);
        generate();
    }

    @Override
    public boolean generate() {
        gameObjects.add(new Tree(2, 2, gameScene.getWorld()));
        return true;
    }
}
