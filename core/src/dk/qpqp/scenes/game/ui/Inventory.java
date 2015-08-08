package dk.qpqp.scenes.game.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.qpqp.scenes.game.GameScene;
import dk.qpqp.scenes.game.Graphic;
import dk.qpqp.scenes.game.item.InventoryItem;
import dk.qpqp.scenes.game.item.Material;

/**
 * Created by viktorstrate on 06/08/2015.
 * Renders and handles the inventory
 */
public class Inventory implements Graphic {

    private InventorySlot[] items;
    private Toolbar toolbar;

    public Inventory(GameScene gameScene, UIHandler uiHandler) {
        items = new InventorySlot[54];

        for (int i = 0; i < 54; i++) {
            int y = Math.floorDiv(i, 9);
            int x = i % 9;
            items[i] = new InventorySlot(x, y, uiHandler);
        }

        toolbar = new Toolbar(gameScene, uiHandler, this);
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        toolbar.render(spriteBatch);
    }

    @Override
    public void update(float dt) {
        toolbar.update(dt);
    }

    public InventorySlot[] getItems() {
        return items;
    }

    public void addItem(Material item) {
        for (int i = 0; i < 54; i++) {
            if (items[i].getItem() == null) continue;
            if (items[i].getItem().getMaterial().equals(item) && items[i].getItem().getMaxStackSize() > items[i].getItem().getAmount()) {
                items[i].getItem().setAmount(items[i].getItem().getAmount() + 1);
                return;
            }
        }

        for (int i = 0; i < 54; i++) {
            if (items[i].getItem() == null) {
                items[i].setItem(new InventoryItem(item, 1));
                break;
            }
        }

    }
}
