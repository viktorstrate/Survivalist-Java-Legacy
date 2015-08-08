package dk.qpqp.scenes.game.object.generators;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import dk.qpqp.scenes.game.GameObject;
import dk.qpqp.scenes.game.GameScene;
import dk.qpqp.scenes.game.Terrain;
import dk.qpqp.scenes.game.object.objects.Stone;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Created by viktorstrate on 04/08/2015.
 * Randomly generates {@link Stone} object in the world
 */
public class StoneGenerator extends ObjectGenerator {

    private static final int MAX_STONE_COUNT = 500;
    private Terrain terrain;

    public StoneGenerator(GameScene gameScene) {
        super(gameScene);
        this.terrain = gameScene.getTerrain();

        for (int i = 0; i < MAX_STONE_COUNT; i++) {
            generate();
        }
    }

    @Override
    public boolean generate() {
        int width = terrain.getMap().getProperties().get("width", Integer.class);
        int height = terrain.getMap().getProperties().get("height", Integer.class);


        boolean foundProperPlace = false;
        Random random = new Random();

        int x= -1, y= -1;

        Iterator<MapLayer> iterator = terrain.getMap().getLayers().iterator();
        ArrayList<TiledMapTileLayer> layers = new ArrayList<>();

        while (iterator.hasNext()) {
            MapLayer mapLayer = iterator.next();
            if (mapLayer instanceof TiledMapTileLayer) {
                layers.add((TiledMapTileLayer) mapLayer);
            }
        }

        for(int i = 0; i < 100; i++) {

            x = random.nextInt(width);
            y = random.nextInt(height);

            for(int l = layers.size()-1; l >= 0; l--){

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
            gameObjects.add(new Stone(x, y, gameScene));
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void update(float dt) {
        super.update(dt);

        for (GameObject g : gameObjects) {
            Stone stone = (Stone) g;
            if (g.mouseOver()) {
                if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
                    stone.setHitTime(stone.getHitTime() + dt);
                }
            }
        }

    }


    private void destroyStone(Stone stone) {
        gameObjects.get(gameObjects.indexOf(stone)).dispose();
        gameObjects.remove(stone);
    }
}
