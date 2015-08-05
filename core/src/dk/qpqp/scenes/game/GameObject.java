package dk.qpqp.scenes.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import dk.qpqp.utills.Constants;

/**
 * Created by viktorstrate on 04/08/2015.
 * Abstract class for game objects, like trees and stones
 */
public abstract class GameObject {

    protected int width, height;
    protected Body body;
    protected World world;
    protected Vector2 position;

    public GameObject(int x, int y, int width, int height, World world) {

        this.width = width;
        this.height = height;
        this.world = world;
        this.position = new Vector2(x, y);

        setupBody();

    }

    protected void setupBody() {
        // Setup body
        BodyDef bdef = new BodyDef();
        bdef.position.set((position.x * Constants.TILE_SIZE + width / 2) / Constants.PPM, (position.y * Constants.TILE_SIZE + height / 2) / Constants.PPM);
        bdef.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(bdef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox((width / 2 - width * 0.05f) / Constants.PPM, (height / 2 - width * 0.05f) / Constants.PPM);
        FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;
        body.createFixture(fdef);

        body = world.createBody(bdef);
    }

    public void render(SpriteBatch spriteBatch) {
        spriteBatch.begin();
        spriteBatch.draw(getTexture(), getPosition().x * Constants.TILE_SIZE, getPosition().y * Constants.TILE_SIZE);
        spriteBatch.end();
    }

    public void update(float dt) {

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

    public abstract Texture getTexture();

    public void dispose() {
        position = null;
        world.destroyBody(body);
    }
}
