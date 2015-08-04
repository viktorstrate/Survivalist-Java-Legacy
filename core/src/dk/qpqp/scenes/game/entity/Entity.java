package dk.qpqp.scenes.game.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import dk.qpqp.scenes.game.GameObject;
import dk.qpqp.utills.Constants;

/**
 * Created by viktorstrate on 03/08/2015.
 * A template for an entity
 */
public abstract class Entity implements GameObject {

    protected Body body;
    protected World world;
    protected float width, height;
    protected Vector2 position;

    public Entity(float x, float y, float w, float h, World world) {

        this.world = world;
        position = new Vector2(x, y);
        width = w;
        height = h;

        // Setup body
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x / Constants.PPM, y / Constants.PPM);
        bodyDef.fixedRotation = true;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(w/2/Constants.PPM, h/2/Constants.PPM);

        body = world.createBody(bodyDef);
        body.setLinearDamping(20);

    }

    public void update(float dt){
        position.x = body.getPosition().x*Constants.PPM;
        position.y = body.getPosition().y*Constants.PPM;
    }

    public void render(SpriteBatch sb){
    }

    public void dispose(){
        world.destroyBody(body);
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }
}
