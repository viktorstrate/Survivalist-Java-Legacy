package dk.qpqp.scenes.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by viktorstrate on 03/08/2015.
 * The player entity
 */
public class Player extends Entity {

    private Texture texture;
    private static final float SPEED = 100;

    public Player(int x, int y, int w, int h, World world) {
        super(x, y, w, h, world);
        texture = new Texture("images/entities/player/naked.png");
    }

    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);
        sb.begin();
        sb.draw(texture, position.x, position.y);
        sb.end();
    }

    @Override
    public void update(float dt) {
        super.update(dt);

        Vector2 velocity = new Vector2(0,0);
        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            velocity.x -= 1;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            velocity.x += 1;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            velocity.y += 1;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            velocity.y -= 1;
        }
        body.applyForce(velocity.scl(SPEED), new Vector2(0, 0), true);
    }

    @Override
    public Texture getTexture() {
        return texture;
    }
}
