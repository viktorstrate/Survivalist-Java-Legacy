package dk.qpqp.scenes.game;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.qpqp.utills.Constants;

import java.util.ArrayList;

/**
 * Created by viktorstrate on 09/08/2015.
 * Handles light in the scene
 */
public class LightHandler implements Graphic {

    private RayHandler rayHandler;
    private ArrayList<PointLight> pointLights;
    private GameScene gameScene;

    public LightHandler(GameScene gameScene) {
        rayHandler = new RayHandler(gameScene.getWorld());
        rayHandler.setAmbientLight(0.1f);
        this.gameScene = gameScene;
        pointLights = new ArrayList<>();
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
        rayHandler.update();
    }

    public PointLight addPointLight(Color color, float distance, float x, float y) {
        PointLight pointLight = new PointLight(rayHandler, 500, color, distance, x / Constants.PPM, y / Constants.PPM);
        pointLight.setSoftnessLength(1f);
        pointLights.add(pointLight);
        return pointLight;
    }
}
