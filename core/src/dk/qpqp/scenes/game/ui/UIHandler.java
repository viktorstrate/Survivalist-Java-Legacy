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
    private Toolbar toolbar;
    private Viewport viewport;
    private SpriteBatch spriteBatch;

    public UIHandler(GameScene gameScene) {

        hudCam = new OrthographicCamera(Game.WIDTH, Game.HEIGHT);
        hudCam.zoom = 1 / Game.SCALE;
        viewport = new ScreenViewport(hudCam);

        spriteBatch = new SpriteBatch();

        this.gameScene = gameScene;
        toolbar = new Toolbar(gameScene, this);


    }

    @Override
    public void render() {
        viewport.apply();
        spriteBatch.setProjectionMatrix(hudCam.combined);
        toolbar.render(spriteBatch);

    }

    public void update(float dt) {
        toolbar.update(dt);
    }

    @Override
    public void resize(int width, int height) {
//        float aspectRatio = (float) width / (float) height;
//        float aspectRatio2 = (float)height / (float)width;
//
//        // This is to maintain the same aspect ratio, using virtual screen-size
//        if(aspectRatio >= (float) Game.WIDTH / (float) Game.HEIGHT){
//            hudCam.setToOrtho(false, Game.HEIGHT * aspectRatio, Game.HEIGHT);
//
//        } else {
//            hudCam.setToOrtho(false, Game.WIDTH, Game.WIDTH * aspectRatio2);
//        }
//
//
//        hudCam.update();
        viewport.update(width, height);
    }

    public OrthographicCamera getHudCam() {
        return hudCam;
    }
}
