package dk.qpqp.scenes.game.item;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Filter;
import com.sun.istack.internal.Nullable;
import dk.qpqp.scenes.game.GameID;
import dk.qpqp.scenes.game.GameScene;
import dk.qpqp.scenes.game.entity.Entity;
import dk.qpqp.scenes.game.entity.Player;
import dk.qpqp.utills.box2D.Box2DTag;

import javax.swing.*;
import java.util.Random;

/**
 * Created by viktorstrate on 06/08/2015.
 * An item on the ground
 */
public class EntityItem extends Entity {

    private Material material;
    private boolean pickupable = true;
    private static final int TILE_SIZE = 8;

    private boolean hasVelocity = true;

    public EntityItem(int x, int y, GameScene gameScene, GameID id, Material material, boolean hasVelocity) {
        this(x, y, gameScene, id, material);
        this.hasVelocity = hasVelocity;
    }

    public EntityItem(int x, int y, GameScene gameScene, GameID id, Material material) {
        super(x, y, TILE_SIZE, TILE_SIZE, gameScene, id);
        this.material = material;
    }

    public EntityItem(GameScene gameScene, GameID id, Material material) {
        super(TILE_SIZE, TILE_SIZE, gameScene, id);
        this.material = material;
    }

    @Override
    protected void setupBody(int width, int height, int x, int y, @Nullable Filter filter) {
        super.setupBody(width, height, x, y, filter);
        if(hasVelocity) {
            Random random = new Random();
            Vector2 force = new Vector2(random.nextFloat() * 2 - 1, random.nextFloat() * 2 - 1);
            body.applyForceToCenter(force.scl(600), true);
        }
    }

    protected void setupBody(int width, int height, int x, int y){
        setupBody(width, height, x, y, Box2DTag.ITEM.getContactFilter());
    }

    @Override
    public void update(float dt) {
        super.update(dt);

        if (pickupable) {
            Player player = gameScene.getPlayer();

            if (player.getPosition().dst(position) < 64) {

                Vector2 centerPos = new Vector2(getPosition().x + getWidth() / 2, getPosition().y + getHeight() / 2);
                Vector2 playerCenterPos = new Vector2(player.getPosition().x + player.getWidth() / 2,
                        player.getPosition().y + player.getHeight() / 2);

                Vector2 angle = new Vector2(playerCenterPos.x - centerPos.x,
                        playerCenterPos.y - centerPos.y);

                body.setLinearVelocity(angle.scl(0.2f));

                if (playerCenterPos.dst(centerPos) < 18) {
                    player.pickupItem(this);
                    gameScene.removeGameObject(this);
                }

//                System.out.println(angle.angle());
            }
        }
    }

    @Override
    public Texture getTexture() {
        return material.getTexture();
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;

    }

    public boolean isPickupable() {
        return pickupable;
    }

    public void setPickupable(boolean pickupable) {
        this.pickupable = pickupable;
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
