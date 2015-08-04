package dk.qpqp.scenes.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import dk.qpqp.scenes.game.GameObject;
import dk.qpqp.utills.Constants;

/**
 * Created by viktorstrate on 04/08/2015.
 * A stone object in the scene
 */
public class Stone implements GameObject {

    private Body body;
    private World world;
    private Vector2 position;
    private Texture texture;
    private static final int SIZE = 32;

    public Stone(int x, int y, World world) {
        position = new Vector2(x,y);
        texture = new Texture("images/objects/stone.png");

        this.world = world;

        // Setup body
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x * SIZE / Constants.PPM, y * SIZE / Constants.PPM);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(SIZE/2/Constants.PPM, SIZE/2/Constants.PPM);

        body = world.createBody(bodyDef);
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.begin();
        spriteBatch.draw(texture, body.getPosition().x * Constants.PPM, body.getPosition().y * Constants.PPM);
        spriteBatch.end();
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public Vector2 getPosition() {
        return position;
    }

    @Override
    public void dispose() {
        position = null;
        texture.dispose();
        world.destroyBody(body);
    }
}
