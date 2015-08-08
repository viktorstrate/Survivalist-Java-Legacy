package dk.qpqp.scenes.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import dk.qpqp.scenes.game.GameScene;
import dk.qpqp.scenes.game.item.EntityItem;
import dk.qpqp.utills.Textures;
import dk.qpqp.utills.box2D.Box2DTag;
import dk.qpqp.utills.box2D.CustomUserData;

/**
 * Created by viktorstrate on 03/08/2015.
 * The player entity
 */
public class Player extends Entity {

    private Texture texture;
    private static final float SPEED = 150;

    public Player(int x, int y, GameScene gameScene) {
        super(x, y, 32, 32, gameScene, 20, 32);
        texture = Textures.ENTITY_PLAYER.getTexture();
    }

    @Override
    protected void setupBody(int width, int height) {
        super.setupBody(width, height);
        body.setLinearDamping(20);
        body.setUserData(new CustomUserData(Box2DTag.PLAYER, this));
    }

    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);
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

    public void pickupItem(EntityItem item) {
        item.dispose();
        System.out.println("Picked up item");
    }
}
