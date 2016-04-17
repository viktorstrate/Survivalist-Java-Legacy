package dk.qpqp.scenes.game.item.entities;

import dk.qpqp.scenes.game.GameID;
import dk.qpqp.scenes.game.GameScene;
import dk.qpqp.scenes.game.item.EntityItem;
import dk.qpqp.scenes.game.item.Material;
import dk.qpqp.utills.box2D.Box2DTag;
import dk.qpqp.utills.box2D.CustomUserData;

/**
 * Created by viktorstrate on 06/08/2015.
 * A stone entity
 */
public class EntityStone extends EntityItem {

    public EntityStone(int x, int y, GameScene gameScene) {
        super(x, y, gameScene, GameID.ENTITY_STONE, Material.STONE);
    }

    public EntityStone(GameScene gameScene, Material material) {
        super(gameScene, GameID.ENTITY_STONE, material);
    }

    @Override
    protected void setupBody(int width, int height, int x, int y) {
        super.setupBody(width, height, x, y);
        body.setUserData(new CustomUserData(Box2DTag.ITEM, this));
    }
}
