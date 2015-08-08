package dk.qpqp.scenes.game.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import dk.qpqp.scenes.game.GameObject;
import dk.qpqp.scenes.game.GameScene;
import dk.qpqp.utills.Constants;

/**
 * Created by viktorstrate on 03/08/2015.
 * A template for an entity
 */
public abstract class Entity extends GameObject {

    public Entity(int x, int y, int w, int h, GameScene gameScene) {
        super(x, y, w, h, gameScene);
    }

    public Entity(int w, int h, GameScene gameScene) {
        super(w, h, gameScene);
    }

    public Entity(int x, int y, int width, int height, GameScene gameScene, int collisionWidth, int collisionHeight) {
        super(x, y, width, height, gameScene, collisionWidth, collisionHeight);
    }

    @Override
    protected void setupBody(int width, int height) {
        // Setup body
        BodyDef bdef = new BodyDef();
        bdef.position.set(position.x / Constants.PPM, position.y / Constants.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = gameScene.getWorld().createBody(bdef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2 / Constants.PPM, height / 2 / Constants.PPM);
        FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;
        body.createFixture(fdef);
        body.setLinearDamping(8);
    }

    public void update(float dt){
        position.x = body.getPosition().x * Constants.PPM - width / 2;
        position.y = body.getPosition().y * Constants.PPM - height / 2;
    }

    public void render(SpriteBatch sb){
        sb.begin();
        sb.draw(getTexture(), getPosition().x, getPosition().y);
        sb.end();
    }
}
