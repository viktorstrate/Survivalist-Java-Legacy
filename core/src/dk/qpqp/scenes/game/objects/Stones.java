package dk.qpqp.scenes.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import dk.qpqp.scenes.game.GameScene;
import dk.qpqp.scenes.game.Terrain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

/**
 * Created by viktorstrate on 04/08/2015.
 * Generates and renders stones
 */
public class Stones {

    private ArrayList<Stone> stones;
    private static final int MAX_STONE_COUNT = 200;
    private Terrain terrain;
    private GameScene gameScene;

    public Stones(GameScene gameScene) {
        stones = new ArrayList<>();
        this.terrain = gameScene.getTerrain();
        this.gameScene = gameScene;

        for (int i = 0; i < MAX_STONE_COUNT; i++) {
            Stone stone = generateStone();
            if(stone==null)
                break;
            else stones.add(stone);
        }
    }

    public void render(SpriteBatch spriteBatch) {
        for(Stone s: stones){
            s.render(spriteBatch);
        }
    }

    private Stone generateStone(){
        int width = terrain.getMap().getProperties().get("width", Integer.class);
        int height = terrain.getMap().getProperties().get("height", Integer.class);


        boolean foundProperPlace = false;
        Random random = new Random();

        int x= -1, y= -1;

        Iterator<MapLayer> iterator = terrain.getMap().getLayers().iterator();
        ArrayList<TiledMapTileLayer> layers = new ArrayList<>();

        while (iterator.hasNext()) {
            layers.add((TiledMapTileLayer) iterator.next());
        }

        for(int i = 0; i < 100; i++) {

            x = random.nextInt(width);
            y = random.nextInt(height);

            for(int l = layers.size()-1; l >= 0; l--){
//            for(int l = 0; l < layers.size(); l++){
                TiledMapTileLayer layer = layers.get(l);

                if(layer==null) continue;
                if(layer.getCell(x,y)==null) continue;



                if (layer.getCell(x, y).getTile().getProperties().containsKey("ground") &&
                        layer.getCell(x, y).getTile().getProperties().get("ground").equals("true")) {
                    foundProperPlace = true;
                } else {
                    foundProperPlace = false;
                    break;
                }
            }

            if(foundProperPlace) break;
        }

        if(foundProperPlace){
            return new Stone(x, y, gameScene.getWorld());
        } else return null;
    }

    public void update(float dt) {

    }

    public void dispose() {
        stones = null;
    }
}
