package dk.qpqp.scenes.game.object.objects;

import com.badlogic.gdx.graphics.Texture;
import dk.qpqp.scenes.game.GameObject;
import dk.qpqp.scenes.game.GameScene;

/**
 * Created by viktorstrate on 04/08/2015.
 * A stone object in the scene
 */
public class Stone extends GameObject {

    private Texture texture;
    private GameScene gameScene;
    public static final float DESTROY_TIME = 0.2f; // time it takes to destroy in sec
    private float hitTime = 0; // time it has been hit in sec

    public Stone(int x, int y, GameScene gameScene) {
        super(x, y, 32, 32, gameScene);

        this.gameScene = gameScene;

        texture = new Texture("images/objects/stone.png");
    }

    @Override
    public void update(float dt) {
        super.update(dt);
    }

    @Override
    public Texture getTexture() {
        return texture;
    }

    public float getHitTime() {
        return hitTime;
    }

    public void setHitTime(float hitTime) {
        this.hitTime = hitTime;
    }
}
