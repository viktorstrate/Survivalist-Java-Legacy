package dk.qpqp.scenes.game.item.items;

import dk.qpqp.scenes.game.GameScene;
import dk.qpqp.scenes.game.item.ItemEntity;
import dk.qpqp.scenes.game.item.Material;

/**
 * Created by viktorstrate on 06/08/2015.
 * A stone entity
 */
public class Stone extends ItemEntity {

    public Stone(int x, int y, GameScene gameScene) {
        super(x, y, gameScene, Material.STONE);
    }
}
