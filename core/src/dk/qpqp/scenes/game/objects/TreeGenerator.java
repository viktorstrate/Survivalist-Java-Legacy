package dk.qpqp.scenes.game.objects;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import dk.qpqp.scenes.game.GameObject;
import dk.qpqp.scenes.game.GameScene;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by viktorstrate on 05/08/2015.
 * Randomly generates {@link Tree} objects in the world
 */
public class TreeGenerator extends ObjectGenerator {

    ArrayList<Vector2> cellPositions;
    ObjectSpawnHandler spawnHandler;

    public TreeGenerator(GameScene gameScene, ObjectSpawnHandler spawnHandler) {
        super(gameScene);

        this.spawnHandler = spawnHandler;

        TiledMapTileLayer layer = (TiledMapTileLayer) gameScene.getTerrain().getMap().getLayers().get("treeSpawnArea");

        cellPositions = new ArrayList<>();

        for (int x = 0; x < layer.getWidth(); x++) {
            for (int y = 0; y < layer.getHeight(); y++) {
                if (layer.getCell(x, y) != null) cellPositions.add(new Vector2(x, y));
            }
        }

        for (int i = 0; i < 200; i++) {
            if (!generate()) break;
        }

    }

    @Override
    public boolean generate() {

        Random random = new Random();

        for (int i = 0; i < 20; i++) {

            boolean foundProperLocation = true;
            Vector2 pos = cellPositions.get(random.nextInt(cellPositions.size()));

            for (GameObject g : gameObjects) {

                for (int x = -2; x < 2; x++) {
                    for (int y = -2; y < 2; y++) {
                        if (g.getPosition().x == pos.x + x && g.getPosition().y == pos.y + y) {
                            foundProperLocation = false;
                            break;
                        }
                    }
                }

                for (ObjectGenerator generator : spawnHandler.getGenerators()) {

                    if (generator instanceof StoneGenerator) {
                        for (GameObject stones : generator.getGameObjects()) {

                            for (int x = -2; x < 2; x++) {
                                for (int y = -2; y < 2; y++) {

                                    if (stones.getPosition().x == pos.x + x && stones.getPosition().y == pos.y + y) {
                                        foundProperLocation = false;
                                        break;
                                    }

                                }
                            }

                        }
                    }

                }
            }

            if (!foundProperLocation) continue;

            gameObjects.add(new Tree((int) pos.x, (int) pos.y, gameScene.getWorld()));
            return true;

        }

        return false;
    }
}
