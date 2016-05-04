package dk.qpqp.scenes.game.terrain.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.sun.istack.internal.Nullable;
import dk.qpqp.scenes.game.GameID;
import dk.qpqp.scenes.game.GameScene;
import dk.qpqp.scenes.game.Graphic;
import dk.qpqp.utills.Constants;
import dk.qpqp.utills.box2D.Box2DTag;
import dk.qpqp.utills.box2D.CustomUserData;

/**
 * Created by viktorstrate on 04/08/2015.
 * Abstract class for game object, like trees and stones
 */
public abstract class GameObject implements Graphic {

    protected int width, height;
    protected Body body;
    protected GameScene gameScene;
    protected Vector2 position;
    protected GameID id;
    protected Vector2 velocity;
    private Vector2 futurePosition = null;

    public GameObject(int x, int y, int width, int height, GameScene gameScene, GameID id, int collisionWidth, int collisionHeight, int collisionX, int collisionY) {
        this(width, height, gameScene, id);
        this.position = new Vector2(x, y);
        setupBody(collisionWidth, collisionHeight, collisionX, collisionY);
    }

    public GameObject(int x, int y, int width, int height, GameScene gameScene, GameID id, int collisionWidth, int collisionHeight) {
        this(width, height, gameScene, id);
        this.position = new Vector2(x, y);
        setupBody(collisionWidth, collisionHeight, 0, 0);
    }

    public GameObject(int x, int y, int width, int height, GameScene gameScene, GameID id) {
        this(width, height, gameScene, id);
        this.position = new Vector2(x, y);
        setupBody(width, height, 0, 0);
    }

    public GameObject(int width, int height, GameScene gameScene, GameID id) {
        this.width = width;
        this.height = height;
        this.gameScene = gameScene;
        this.id = id;
    }

    protected void setupBody(int width, int height, int x, int y, @Nullable Filter filter) {
        // Setup body
        BodyDef bdef = new BodyDef();
        bdef.position.set(
                ((position.x * Constants.TILE_SIZE + width / 2) + x) / Constants.PPM,
                ((position.y * Constants.TILE_SIZE + height / 2) + y) / Constants.PPM);
        bdef.type = BodyDef.BodyType.StaticBody;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox((width / 2 - width * 0.05f) / Constants.PPM, (height / 2 - width * 0.05f) / Constants.PPM);
        FixtureDef fdef = new FixtureDef();

        if(filter!=null){
            fdef.filter.maskBits = filter.maskBits;
            fdef.filter.categoryBits = filter.categoryBits;
            fdef.filter.groupIndex = filter.groupIndex;
        }

        fdef.shape = shape;

        body = gameScene.getWorld().createBody(bdef);
        body.setUserData(new CustomUserData(Box2DTag.GAME_OBJECT, this));
        body.createFixture(fdef);
    }

    protected void setupBody(int width, int height, int x, int y){
        setupBody(width, height, x, y, Box2DTag.GAME_OBJECT.getContactFilter());
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.begin();
        spriteBatch.draw(getTexture(), getPosition().x * Constants.TILE_SIZE, getPosition().y * Constants.TILE_SIZE);
        spriteBatch.end();
    }

    @Override
    public void update(float dt) {
        velocity = new Vector2(0,0);

        // Set Position from future position
        if(futurePosition!=null) {
            this.position = futurePosition;
            if (body == null) {
                setupBody(width, height, 0, 0);
            }
            this.body.setTransform(futurePosition.scl(1 / Constants.PPM), body.getAngle());
            futurePosition = null;
        }
    }

    public boolean mouseOver() {

        Vector2 mouseWorldPos = gameScene.getViewport().unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));

        return mouseWorldPos.x > getPosition().x * Constants.TILE_SIZE && mouseWorldPos.x < getPosition().x * Constants.TILE_SIZE + this.width &&
                mouseWorldPos.y > getPosition().y * Constants.TILE_SIZE && mouseWorldPos.y < getPosition().y * Constants.TILE_SIZE + this.height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {

        futurePosition = position;

    }

    public abstract Texture getTexture();

    public void dispose() {
        gameScene.removeBody(body);
    }

    public GameID getId() {
        return id;
    }

    public void setId(GameID id) {
        this.id = id;
    }
}
