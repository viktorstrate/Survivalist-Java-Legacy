package dk.qpqp.scenes.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;
import dk.qpqp.scenes.game.GameObject;

/**
 * Created by viktorstrate on 04/08/2015.
 * A stone object in the scene
 */
public class Stone extends GameObject {

    private Texture texture;

    public Stone(int x, int y, World world) {
        super(x, y, 32, 32, world);

        texture = new Texture("images/objects/stone.png");
    }

    @Override
    public Texture getTexture() {
        return texture;
    }
}
