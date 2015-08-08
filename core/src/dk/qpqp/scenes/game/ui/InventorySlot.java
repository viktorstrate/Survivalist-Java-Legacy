package dk.qpqp.scenes.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import dk.qpqp.scenes.game.item.InventoryItem;
import dk.qpqp.scenes.game.item.Material;
import dk.qpqp.utills.Constants;
import dk.qpqp.utills.Textures;

/**
 * Created by viktorstrate on 05/08/2015.
 * Renders a toolbar background
 */
public class InventorySlot {

    private Texture texture, textureSelected;
    private InventoryItem item;
    private Vector2 position;
    private boolean selected;
    private UIHandler uiHandler;
    public static final int SIZE = 20;
    public static final int MARGIN = 2;

    public InventorySlot(int x, int y, UIHandler uiHandler) {
        texture = Textures.INVENTORY_SLOT.getTexture();
        textureSelected = Textures.INVENTORY_SLOT_SELECTED.getTexture();
        position = new Vector2(x, y);
        this.selected = false;
        item = null;
        this.uiHandler = uiHandler;
    }

    public InventorySlot(int x, int y, UIHandler uiHandler, Material material, int amount) {
        this(x, y, uiHandler);
        item = new InventoryItem(material, amount);
    }

    public void render(SpriteBatch spriteBatch, int offsetX, int offsetY) {
        spriteBatch.begin();
        spriteBatch.draw((selected ? texture : textureSelected),
                position.x * (SIZE * uiHandler.getScale() + MARGIN * uiHandler.getScale()) + offsetX + MARGIN,
                position.y * (SIZE * uiHandler.getScale() + MARGIN * uiHandler.getScale()) + offsetY + MARGIN,
                SIZE * uiHandler.getScale(), SIZE * uiHandler.getScale());
        spriteBatch.end();
    }

    public void update(float dt) {

    }

    public boolean mouseOver() {

        Vector2 mouseWorldPos = uiHandler.getViewport().unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));

        return mouseWorldPos.x > position.x * Constants.TILE_SIZE && mouseWorldPos.x < position.x * Constants.TILE_SIZE + SIZE &&
                mouseWorldPos.y > position.y * Constants.TILE_SIZE && mouseWorldPos.y < position.y * Constants.TILE_SIZE + SIZE;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
