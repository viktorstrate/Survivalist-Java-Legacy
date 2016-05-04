package dk.qpqp.scenes.game.terrain.tiles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import dk.qpqp.scenes.game.Graphic;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by viktorstrate on 04/05/16.
 */
public abstract class Tile {

    public enum TileType {
        GRASS
    }

    protected TileType type;
    public static final int SIZE = 32;
    protected TextureRegion texture;

    public Tile(TileType type, TextureRegion texture) {
        this.texture = texture;
        this.type = type;
    }

    public Tile(TileType type) {
        this(type, null);
    }

    public void render(SpriteBatch spriteBatch, int x, int y) {
        if(texture!=null){
            spriteBatch.draw(texture, x*SIZE, y*SIZE, SIZE, SIZE);
        }
    }

    public TileType getType() {
        return type;
    }
}
