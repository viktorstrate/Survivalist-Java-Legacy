package dk.qpqp.scenes.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;
import dk.qpqp.scenes.game.GameObject;

/**
 * Created by viktorstrate on 05/08/2015.
 * A tree object in the scene
 */
public class Tree extends GameObject {

    private Texture texture;

    public Tree(int x, int y, World world) {
        super(x, y, 64, 64, world);
        texture = new Texture("images/objects/tree.png");
    }

    @Override
    public Texture getTexture() {
        return texture;
    }
}
