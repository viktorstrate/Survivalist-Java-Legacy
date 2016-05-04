package dk.qpqp.scenes.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import dk.qpqp.Game;
import dk.qpqp.files.GameFile;
import dk.qpqp.net.GameClient;
import dk.qpqp.net.GameClientConnection;
import dk.qpqp.scenes.Scene;
import dk.qpqp.scenes.game.entity.Player;
import dk.qpqp.scenes.game.item.Material;
import dk.qpqp.scenes.game.listeners.CollisionLister;
import dk.qpqp.scenes.game.listeners.FilterListener;
import dk.qpqp.scenes.game.object.generators.ObjectSpawnHandler;
import dk.qpqp.scenes.game.ui.UIHandler;
import dk.qpqp.utills.Constants;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by viktorstrate on 03/08/2015.
 * The game scene
 */
public class GameScene extends Scene {

    private OrthographicCamera gameCamera;
    private Viewport viewport;
    private SpriteBatch spriteBatch;

    private ArrayList<GameObject> gameObjects;
    private ArrayList<GameObject> gameObjectsToRemove;
    private ArrayList<GameObject> gameObjectsToAdd;

    private Player player;
    private Terrain terrain;
    private ObjectSpawnHandler objectSpawnHandler;
    private UIHandler uiHandler;

    private boolean loadFile = false;


    // Box 2D
    private World world;
    private Box2DDebugRenderer b2dr;
    private ArrayList<Body> bodiesToRemove;
    private CollisionLister collisionLister;

    // Network
    GameClient networkClient;

    private LightHandler lightHandler;

    public GameScene() {

        gameCamera = new OrthographicCamera(Game.WIDTH, Game.HEIGHT);
        viewport = new FillViewport(Game.WIDTH, Game.HEIGHT, gameCamera);
        gameCamera.zoom = 1/Game.SCALE;
        gameCamera.update();
        spriteBatch = new SpriteBatch();

        // Box 2D
        world = new World(new Vector2(0, 0), true);
        collisionLister = new CollisionLister();
        world.setContactFilter(new FilterListener());
        world.setContactListener(collisionLister);
        b2dr = new Box2DDebugRenderer();
        bodiesToRemove = new ArrayList<>();

        lightHandler = new LightHandler(this);

        gameObjects = new ArrayList<>();
        gameObjectsToRemove = new ArrayList<>();
        gameObjectsToAdd = new ArrayList<>();

        //setPlayer(new Player(64 * 32, 64 * 32, this, true, id));

        terrain = new Terrain(this);
        objectSpawnHandler = new ObjectSpawnHandler(this);

        uiHandler = new UIHandler(this);

        uiHandler.getInventory().addItem(Material.TORCH);

        // Connect to the server
        try {
            networkClient = new GameClient(InetAddress.getByName("localhost"), 5765, this);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void render() {
        spriteBatch.setProjectionMatrix(gameCamera.combined);

        terrain.render(spriteBatch);

        for(GameObject g: gameObjects){
            g.render(spriteBatch);
        }


        lightHandler.render(spriteBatch);

        uiHandler.render();
        viewport.apply();


        Gdx.graphics.setTitle("FPS "+Gdx.graphics.getFramesPerSecond()+" Game Objects "+gameObjects.size());

        b2dr.render(world, gameCamera.combined.cpy().scale(Constants.PPM, Constants.PPM, 1));
    }

    @Override
    public void update(float dt) {
        world.step(dt, 6, 2);
        Iterator<Body> it = bodiesToRemove.iterator();
        while (it.hasNext()) {
            Body bod = it.next();
            bod.setActive(false);
            bod.setAwake(false);
            world.destroyBody(bod);
            it.remove();
        }

        for(GameObject g: gameObjectsToRemove){
            g.dispose();
            gameObjects.remove(g);
        }
        gameObjectsToRemove.clear();


        gameObjects.addAll(gameObjectsToAdd);
        gameObjectsToAdd.clear();


        lightHandler.update(dt);

        for(GameObject g: gameObjects){
            g.update(dt);
        }

        // Temporary save key
        if(Gdx.input.isKeyJustPressed(Input.Keys.P)){
            GameFile file = new GameFile(getGameObjects());
            file.save("survivalist.save");
        }

        if(loadFile){
            GameFile file = new GameFile(gameObjects);
            file.load("survivalist.save", this);
            loadFile = false;
        }

        // Temporary load key
        if(Gdx.input.isKeyJustPressed(Input.Keys.L)){
            clearGameObjects();
            loadFile = true;
        }



        if(Gdx.input.isKeyJustPressed(Input.Keys.C)){
            clearGameObjects();
        }

        terrain.update(dt);


        // Camera
        if(player!=null) {
            gameCamera.position.lerp(new Vector3(player.getPosition().x + player.width / 2, player.getPosition().y + player.height / 2, 0), 4f * dt);
            gameCamera.update();
        }
    }

    @Override
    public void resize(int width, int height){

        viewport.update(width, height);

        uiHandler.resize(width, height);
    }

    @Override
    public void dispose() {
        networkClient.disconnect();
        world.dispose();
        terrain.dispose();
    }

    public void removeBody(Body body) {
        bodiesToRemove.add(body);
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

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        if(gameObjects.contains(this.getPlayer())){
            removeGameObject(this.getPlayer());
        }
        this.player = player;
        addGameObject(player);
    }

    public Viewport getViewport() {
        return viewport;
    }

    public CollisionLister getCollisionLister() {
        return collisionLister;
    }


    public ObjectSpawnHandler getObjectSpawnHandler() {
        return objectSpawnHandler;
    }

    public UIHandler getUiHandler() {
        return uiHandler;
    }

    public LightHandler getLightHandler() {
        return lightHandler;
    }

    public ArrayList<GameObject> getGameObjects() {
        return gameObjects;
    }

    public void addGameObject(GameObject gameObjects) {
        this.gameObjectsToAdd.add(gameObjects);
    }

    public void removeGameObject(GameObject gameObject){
        gameObjectsToRemove.add(gameObject);
    }

    public void clearGameObjects(){
        gameObjectsToRemove.addAll(gameObjects);
    }

    public Player addNetworkPlayer(GameClientConnection connection){
        System.out.println("Adding network player object");
        Player player = new Player((int)connection.getX(), (int)connection.getY(), this, false);
        addGameObject(player);
        return player;
    }

    public void moveNetworkPlayer(Player player, Vector2 position){
        for(GameObject g: getGameObjects()){
            if(g.equals(player)){
                System.out.println("Updating network player position");
                Player gamePlayer = (Player) g;
                gamePlayer.setPosition(position);
                break;
            }
        }
    }

    public GameClient getNetworkClient() {
        return networkClient;
    }
}
