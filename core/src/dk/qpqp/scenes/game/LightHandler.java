package dk.qpqp.scenes.game;

import box2dLight.Light;
import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Filter;
import dk.qpqp.utills.Constants;
import dk.qpqp.utills.box2D.Box2DTag;

import java.util.ArrayList;

/**
 * Created by viktorstrate on 09/08/2015.
 * Handles light in the scene
 */
public class LightHandler implements Graphic {

    private RayHandler rayHandler;
    private ArrayList<Light> lights;
    private ArrayList<Light> lightsToAdd;
    private ArrayList<Light> lightsToRemove;
    private GameScene gameScene;

    public LightHandler(GameScene gameScene) {
        rayHandler = new RayHandler(gameScene.getWorld());
        rayHandler.setAmbientLight(0.1f);
        this.gameScene = gameScene;
        lights = new ArrayList<>();
        lightsToAdd = new ArrayList<>();
        lightsToRemove = new ArrayList<>();
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        rayHandler.useCustomViewport(gameScene.getViewport().getScreenX(), gameScene.getViewport().getScreenY(),
                gameScene.getViewport().getScreenWidth(), gameScene.getViewport().getScreenHeight());
        rayHandler.setCombinedMatrix(gameScene.getGameCamera().combined.cpy().scl(Constants.PPM));
        rayHandler.render();
    }

    @Override
    public void update(float dt) {

        for(Light l: lightsToRemove){
            lights.remove(l);
        }
        lightsToRemove.clear();

        lights.addAll(lightsToAdd);
        lightsToAdd.clear();

        rayHandler.update();
    }

    public PointLight addPointLight(Color color, float distance, float x, float y) {
        PointLight pointLight = new PointLight(rayHandler, 500, color, distance, x / Constants.PPM, y / Constants.PPM);
        pointLight.setSoftnessLength(1f);
        pointLight.setContactFilter(Box2DTag.LIGHT.getContactFilter());
        lightsToAdd.add(pointLight);
        return pointLight;
    }

    public void removeLight(Light light){
        light.remove(true);
        lightsToRemove.add(light);
    }
}
