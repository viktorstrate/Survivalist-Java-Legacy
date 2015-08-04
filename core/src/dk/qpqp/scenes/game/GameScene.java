package dk.qpqp.scenes.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import dk.qpqp.Game;
import dk.qpqp.scenes.Scene;
import dk.qpqp.scenes.game.entity.Player;
import dk.qpqp.scenes.game.objects.Stones;
import dk.qpqp.utills.Constants;

import java.util.ArrayList;

/**
 * Created by viktorstrate on 03/08/2015.
 * The game scene
 */
public class GameScene extends Scene {

    private OrthographicCamera gameCamera;
    private OrthographicCamera hudCamera;
    private OrthographicCamera b2dCamera;
    private SpriteBatch spriteBatch;

    private ArrayList<GameObject> gameObjects;

    private Player player;
    private Terrain terrain;
    private Stones stones;

    // Box 2D
    private World world;
    private Box2DDebugRenderer b2dr;

    public GameScene() {

        gameCamera = new OrthographicCamera(Game.WIDTH, Game.HEIGHT);
        gameCamera.zoom = 1/Game.SCALE;
        gameCamera.update();
        spriteBatch = new SpriteBatch();

        hudCamera = new OrthographicCamera(Game.WIDTH, Game.HEIGHT);
        b2dCamera = new OrthographicCamera(Game.WIDTH/Constants.PPM, Game.HEIGHT/Constants.PPM);

        // Box 2D
        world = new World(new Vector2(0, 0), true);
        b2dr = new Box2DDebugRenderer();
        gameObjects = new ArrayList<>();

        player = new Player(0, 0, 32, 32, world);
        gameObjects.add(player);

        terrain = new Terrain(this);

        stones = new Stones(this);
    }

    @Override
    public void render() {
        spriteBatch.setProjectionMatrix(gameCamera.combined);

        terrain.render();

        for(GameObject g: gameObjects){
            g.render(spriteBatch);
        }

        stones.render(spriteBatch);

        b2dr.render(world, b2dCamera.combined);
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

        float aspectRatio = (float) width / (float) height;
        float aspectRatio2 = (float)height / (float)width;

        // This is to maintain the same aspect ratio, using virtual screen-size
        if(aspectRatio >= (float) Game.WIDTH / (float) Game.HEIGHT){
            gameCamera.setToOrtho(false, Game.HEIGHT * aspectRatio, Game.HEIGHT);

        } else {
            gameCamera.setToOrtho(false, Game.WIDTH, Game.WIDTH * aspectRatio2);
        }

        gameCamera.position.set(player.getPosition().x, player.getPosition().y, 1);
        gameCamera.update();
    }

    @Override
    public void dispose() {
        world.dispose();
        terrain.dispose();
    }

    public OrthographicCamera getGameCamera() {
        return gameCamera;
    }

    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public World getWorld() {
        return world;
    }
}
