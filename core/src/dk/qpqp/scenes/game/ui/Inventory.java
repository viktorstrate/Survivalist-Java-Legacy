package dk.qpqp.scenes.game.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.qpqp.scenes.game.Graphic;
import dk.qpqp.scenes.game.item.InventoryItem;

/**
 * Created by viktorstrate on 06/08/2015.
 * Renders and handles the inventory
 */
public class Inventory implements Graphic {

    InventoryItem[] items;

    public Inventory() {
        items = new InventoryItem[54];
    }

    @Override
    public void render(SpriteBatch spriteBatch) {

    }

    @Override
    public void update(float dt) {

    }
}
