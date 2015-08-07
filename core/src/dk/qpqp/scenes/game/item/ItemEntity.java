package dk.qpqp.scenes.game.item;

import com.badlogic.gdx.graphics.Texture;
import dk.qpqp.scenes.game.GameScene;
import dk.qpqp.scenes.game.entity.Entity;
import dk.qpqp.scenes.game.entity.Player;

/**
 * Created by viktorstrate on 06/08/2015.
 * An item on the ground
 */
public class ItemEntity extends Entity {

    private Material material;
    private boolean pickupable = true;

    public ItemEntity(int x, int y, GameScene gameScene, Material material) {
        super(x, y, 8, 8, gameScene);
        this.material = material;
    }

    @Override
    public void update(float dt) {
        super.update(dt);

        if (pickupable) {
            Player player = gameScene.getPlayer();

            if (player.getPosition().dst(position) < 64) {
                System.out.println("Item in range of player");
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
}
