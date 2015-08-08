package dk.qpqp.scenes.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import dk.qpqp.Game;
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
    // Scale for the item
    private static final float ITEM_SCALE = 0.75f;

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

    public void render(SpriteBatch spriteBatch) {


        float x = position.x * (SIZE * uiHandler.getScale() + MARGIN * uiHandler.getScale()) + getOffsetX() + MARGIN;
        float y = position.y * (SIZE * uiHandler.getScale() + MARGIN * uiHandler.getScale()) + getOffsetY() + MARGIN;
        float scl = SIZE * uiHandler.getScale();

        spriteBatch.begin();

        spriteBatch.draw((selected ? textureSelected : texture), x, y, scl, scl);

        if (item != null) {
            spriteBatch.draw(item.getMaterial().getTexture(), x + (scl * ITEM_SCALE) / 4, y + (scl * ITEM_SCALE) / 4, scl * ITEM_SCALE, scl * ITEM_SCALE);



        }

        spriteBatch.end();

    }

    public void renderText(SpriteBatch spriteBatch) {
        if (item != null) {
            if (mouseOver()) {
                spriteBatch.begin();
                Constants.FONT_SMALL.draw(spriteBatch, item.getMaterial().getName(),
                        getOffsetX() + Gdx.input.getX() / Game.SCALE + 5,
                        getOffsetY() + uiHandler.getViewport().getScreenHeight() / Game.SCALE - Gdx.input.getY() / Game.SCALE);
                spriteBatch.end();
            }
        }
    }

    public void update(float dt) {

    }

    public boolean mouseOver() {

        float x = position.x * (SIZE * uiHandler.getScale() + MARGIN * uiHandler.getScale()) + getOffsetX() + MARGIN;
        float y = position.y * (SIZE * uiHandler.getScale() + MARGIN * uiHandler.getScale()) + getOffsetY() + MARGIN;
        float scl = SIZE * uiHandler.getScale();

        Vector2 mouseWorldPos = uiHandler.getViewport().unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));

        return mouseWorldPos.x > x && mouseWorldPos.x < x + scl &&
                mouseWorldPos.y > y && mouseWorldPos.y < y + scl;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public InventoryItem getItem() {
        return item;
    }

    public void setItem(InventoryItem item) {
        this.item = item;
    }

    private int getOffsetX() {
        return Math.round(0 - uiHandler.getHudCam().viewportWidth / 2 / Game.SCALE);
    }

    private int getOffsetY() {
        return Math.round(0 - uiHandler.getHudCam().viewportHeight / 2 / Game.SCALE);
    }
}
