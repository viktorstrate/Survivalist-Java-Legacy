package dk.qpqp.scenes.game.terrain.tiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import dk.qpqp.utills.Textures;

/**
 * Created by viktorstrate on 04/05/16.
 */
public class TileGrass extends Tile {

    public TileGrass() {
        super(TileType.GRASS);
        Texture grassTexture = Textures.TILE_GRASS.getTexture();
        int textures = grassTexture.getWidth()/SIZE;
        int x = (int)Math.ceil(Math.random()*textures);
        texture = new TextureRegion(grassTexture, x*SIZE, 0, SIZE, SIZE);
    }

}
