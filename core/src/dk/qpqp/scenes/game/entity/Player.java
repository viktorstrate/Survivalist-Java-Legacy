package dk.qpqp.scenes.game.entity;

import box2dLight.PointLight;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import dk.qpqp.net.GameClient;
import dk.qpqp.net.packets.Packet04PlayerMove;
import dk.qpqp.scenes.game.GameID;
import dk.qpqp.scenes.game.GameScene;
import dk.qpqp.scenes.game.item.EntityItem;
import dk.qpqp.scenes.game.ui.Inventory;
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
    private PointLight light;
    private boolean local;
    private long lastMovePacketTime = 0;
    private Vector2 lastPosition;

    public Player(int x, int y, GameScene gameScene, boolean local) {
        super(x, y, 32, 32, gameScene, GameID.PLAYER, 20, 32);
        texture = Textures.ENTITY_PLAYER.getTexture();

        this.local = local;

        light = gameScene.getLightHandler().addPointLight(new Color(0.451f, 0.17f, 0f, 1f), 20, getPosition().x, getPosition().y);
        light.attachToBody(body);

        lastPosition = new Vector2(position);
    }

    @Override
    protected void setupBody(int width, int height, int x, int y) {
        super.setupBody(width, height, x, y);

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

        if(local) {
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                velocity.x -= 1;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                velocity.x += 1;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                velocity.y += 1;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                velocity.y -= 1;
            }
            body.applyForce(velocity.scl(SPEED), new Vector2(0, 0), true);
            if(!lastPosition.epsilonEquals(position, 0.1f) && lastMovePacketTime+1000/60 < System.currentTimeMillis()){

                Packet04PlayerMove movePacket = new Packet04PlayerMove(gameScene.getNetworkClient().getSecret(),
                        getPosition().x, getPosition().y);

                gameScene.getNetworkClient().sendData(movePacket.getDataToServer());

                lastMovePacketTime = System.currentTimeMillis();
            }
        }

        lastPosition = new Vector2(position);
    }

    @Override
    public Texture getTexture() {
        return texture;
    }

    public void pickupItem(EntityItem item) {
        getInventory().addItem(item.getMaterial());
    }

    public Inventory getInventory() {
        return gameScene.getUiHandler().getInventory();
    }

    @Override
    public void dispose() {
        gameScene.getLightHandler().removeLight(light);
        super.dispose();
    }
}
