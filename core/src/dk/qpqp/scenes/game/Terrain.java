package dk.qpqp.scenes.game;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.utils.Array;

import java.util.Iterator;

/**
 * Created by viktorstrate on 04/08/2015.
 * Renders the terrain from a tiled map
 */
public class Terrain {
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private GameScene scene;

    public Terrain(GameScene scene) {
        this.scene = scene;
        map = new TmxMapLoader().load("maps/map.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, scene.getSpriteBatch());

        // Animation
        Array<StaticTiledMapTile> frameTiles = new Array<>();

        for (TiledMapTile tile : map.getTileSets().getTileSet("water")) {
            if (tile instanceof StaticTiledMapTile) {
                frameTiles.add((StaticTiledMapTile) tile);
            }
        }

        AnimatedTiledMapTile animatedTile = new AnimatedTiledMapTile(0.75f, frameTiles);

        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get("mid");
        for(int x = 0; x < layer.getWidth(); x++){
            for(int y = 0; y < layer.getHeight(); y++){
                TiledMapTileLayer.Cell cell = layer.getCell(x,y);
                if(cell==null) continue;
                if(cell.getTile().getProperties().containsKey("animation") && cell.getTile().getProperties().get("animation").equals("water")){
                    cell.setTile(animatedTile);
                }
            }
        }
    }

    public void render(){
        renderer.setView(scene.getGameCamera());
        renderer.render();
    }

    public void dispose(){
        map.dispose();
        renderer.dispose();
    }
}
