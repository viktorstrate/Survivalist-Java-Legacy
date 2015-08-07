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

    @Override
    protected void setupBody() {
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
        body.setLinearDamping(20);
    }

    public void update(float dt){
        position.x = body.getPosition().x * Constants.PPM - width / 2;
        position.y = body.getPosition().y * Constants.PPM - height / 2;
    }

    public void render(SpriteBatch sb){
    }
}
