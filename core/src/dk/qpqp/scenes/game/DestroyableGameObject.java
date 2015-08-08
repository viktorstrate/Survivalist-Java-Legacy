package dk.qpqp.scenes.game;

import com.badlogic.gdx.math.Vector2;
import dk.qpqp.scenes.game.item.EntityItem;
import dk.qpqp.utills.Constants;

import java.util.ArrayList;

/**
 * Created by viktorstrate on 07/08/2015.
 * A GameObject that is destroyable, like stones or trees
 */
public abstract class DestroyableGameObject extends GameObject {

    protected float destroyTime = 0.2f; // time it takes to destroy in sec
    protected float hitTime = 0; // time it has been hit in sec
    protected ArrayList<EntityItem> drops;

    public DestroyableGameObject(int x, int y, int width, int height, GameScene gameScene) {
        super(x, y, width, height, gameScene);
        setup();
    }

    public DestroyableGameObject(int x, int y, int width, int height, GameScene gameScene, int collisionWidth, int collisionHeight) {
        super(x, y, width, height, gameScene, collisionWidth, collisionHeight);
        setup();
    }

    private void setup() {
        drops = new ArrayList<>();
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        if (hitTime >= destroyTime) {
            destroy();
        }
    }

    public ArrayList<EntityItem> getDrops() {
        return drops;
    }

    public float getDestroyTime() {
        return destroyTime;
    }

    public void setDestroyTime(float destroyTime) {
        this.destroyTime = destroyTime;
    }

    public float getHitTime() {
        return hitTime;
    }

    public void setHitTime(float hitTime) {
        this.hitTime = hitTime;
    }

    public void destroy() {
        gameScene.removeBody(body);

        for (EntityItem e : drops) {
            e.setPosition(new Vector2(position.x * Constants.TILE_SIZE, position.y * Constants.TILE_SIZE));
            gameScene.getItemEntityHandler().getEntities().add(e);
        }
    }
}
