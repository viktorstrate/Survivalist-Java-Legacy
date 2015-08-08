package dk.qpqp.scenes.game.ui;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import dk.qpqp.Game;
import dk.qpqp.scenes.game.GameScene;

/**
 * Created by viktorstrate on 06/08/2015.
 * Renders and handles the UI
 */
public class UIHandler extends ApplicationAdapter {

    private GameScene gameScene;
    private OrthographicCamera hudCam;
    private Viewport viewport;
    private SpriteBatch spriteBatch;
    private Inventory inventory;
    private float scale = 2f;

    public UIHandler(GameScene gameScene) {

        hudCam = new OrthographicCamera(Game.WIDTH, Game.HEIGHT);
        hudCam.zoom = 1 / scale;
        viewport = new ScreenViewport(hudCam);

        spriteBatch = new SpriteBatch();

        inventory = new Inventory(gameScene, this);

        this.gameScene = gameScene;

    }

    @Override
    public void render() {
        viewport.apply();
        spriteBatch.setProjectionMatrix(hudCam.combined);
        inventory.render(spriteBatch);
    }

    public void update(float dt) {
        inventory.update(dt);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        float size = (width + height) / 2;
        float gameSize = ((float) Game.WIDTH + (float) Game.HEIGHT) / 2;
        scale = size / gameSize;
        System.out.println("scale = " + scale);
    }

    public OrthographicCamera getHudCam() {
        return hudCam;
    }

    public Viewport getViewport() {
        return viewport;
    }

    public float getScale() {
        return scale;
    }
}
