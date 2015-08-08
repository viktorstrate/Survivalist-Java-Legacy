package dk.qpqp.scenes.game.item;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by viktorstrate on 06/08/2015.
 * All the item materials
 */
public enum Material {
    STONE(new Texture("images/items/rock.png"), 6, "Stone");

    private Texture texture;
    private int maxStackSize;
    private String name;

    Material(Texture texture, int maxStackSize, String name) {
        this.texture = texture;
        this.maxStackSize = maxStackSize;
        this.name = name;
    }

    public Texture getTexture() {
        return texture;
    }

    public int getMaxStackSize() {
        return maxStackSize;
    }

    public String getName() {
        return name;
    }
}
