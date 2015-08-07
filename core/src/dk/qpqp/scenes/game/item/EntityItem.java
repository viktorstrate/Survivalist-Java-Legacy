package dk.qpqp.scenes.game.item;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import dk.qpqp.scenes.game.GameScene;
import dk.qpqp.scenes.game.entity.Entity;
import dk.qpqp.scenes.game.entity.Player;

import java.util.ArrayList;

/**
 * Created by viktorstrate on 06/08/2015.
 * An item on the ground
 */
public class EntityItem extends Entity {

    private Material material;
    private boolean pickupable = true;
    private static final int TILE_SIZE = 8;

    public EntityItem(int x, int y, GameScene gameScene, Material material) {
        super(x, y, TILE_SIZE, TILE_SIZE, gameScene);
        this.material = material;
    }

    public EntityItem(GameScene gameScene, Material material) {
        super(TILE_SIZE, TILE_SIZE, gameScene);
        this.material = material;
    }

    @Override
    public void update(float dt) {
        super.update(dt);

        if (pickupable) {
            Player player = gameScene.getPlayer();

            if (player.getPosition().dst(position) < 64) {

                Vector2 angle = new Vector2((player.getPosition().x + player.getWidth() / 2) - (getPosition().x + getWidth() / 2),
                        (player.getPosition().y + player.getHeight() / 2) - (getPosition().y + getHeight() / 2));

                body.setLinearVelocity(angle.scl(0.1f));
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
        ArrayList<EntityItem> entities = gameScene.getItemEntityHandler().getEntities();
        if (entities.contains(this))
            entities.remove(this);
    }
}
