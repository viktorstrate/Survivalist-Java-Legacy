package dk.qpqp.scenes.game.item;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.qpqp.scenes.game.GameScene;
import dk.qpqp.scenes.game.Graphic;

import java.util.ArrayList;

/**
 * Created by viktorstrate on 07/08/2015.
 * Handles the entities in the world
 */
public class ItemEntityHandler implements Graphic {
    private ArrayList<EntityItem> entities;
    private GameScene gameScene;

    public ItemEntityHandler(GameScene gameScene) {
        this.gameScene = gameScene;
        entities = new ArrayList<>();
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        for (EntityItem e : entities)
            e.render(spriteBatch);
    }

    @Override
    public void update(float dt) {
        for (EntityItem e : entities)
            e.update(dt);
    }

    public ArrayList<EntityItem> getEntities() {
        return entities;
    }

}
