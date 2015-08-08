package dk.qpqp.scenes.game.item;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by viktorstrate on 06/08/2015.
 * All the item materials
 */
public enum Material {
    STONE(new Texture("images/items/rock.png"), 99);

    private Texture texture;
    private int maxStackSize;

    Material(Texture texture, int maxStackSize) {
        this.texture = texture;
        this.maxStackSize = maxStackSize;
    }

    public Texture getTexture() {
        return texture;
    }

    public int getMaxStackSize() {
        return maxStackSize;
    }
}
