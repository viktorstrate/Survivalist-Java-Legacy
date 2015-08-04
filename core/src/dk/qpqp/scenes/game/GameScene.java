package dk.qpqp.scenes.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.*;
import dk.qpqp.Game;
import dk.qpqp.scenes.Scene;
import dk.qpqp.scenes.game.entity.Player;

import java.util.ArrayList;

/**
 * Created by viktorstrate on 03/08/2015.
 * The game scene
 */
public class GameScene extends Scene {

    private OrthographicCamera gameCamera;
//    private OrthographicCamera hudCamera;
    private Viewport viewport;
    private SpriteBatch spriteBatch;

    private ArrayList<GameObject> gameObjects;

    private Player player;
    private Terrain terrain;

    // Box 2D
    private World world;

    public GameScene() {

        gameCamera = new OrthographicCamera(Game.WIDTH, Game.HEIGHT);
        viewport = new ExtendViewport(Game.WIDTH, Game.HEIGHT, gameCamera);
        gameCamera.zoom = 1/Game.SCALE;
        gameCamera.update();
        spriteBatch = new SpriteBatch();

        // Box 2D
        world = new World(new Vector2(0, 0), true);
        gameObjects = new ArrayList<>();

        player = new Player(0, 0, 32, 32, world);
        gameObjects.add(player);

        terrain = new Terrain(this);
    }

    @Override
    public void render() {
        spriteBatch.setProjectionMatrix(gameCamera.combined);

        terrain.render();

        for(GameObject g: gameObjects){
            g.render(spriteBatch);
        }
    }

    @Override
    public void update(float dt) {
        world.step(1 / 60f, 6, 2);

        for(GameObject g: gameObjects){
            g.update(dt);
        }

        // Camera
        gameCamera.position.lerp(new Vector3(player.getPosition().x, player.getPosition().y, 0), 4f*dt);
        gameCamera.update();
    }

    @Override
    public void resize(int width, int height){
        viewport.update(Math.round(width), Math.round(height));
    }

    @Override
    public void dispose() {
        world.dispose();
    }

    public OrthographicCamera getGameCamera() {
        return gameCamera;
    }

    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }
}
