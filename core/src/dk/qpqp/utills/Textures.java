package dk.qpqp.utills;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by viktorstrate on 07/08/2015.
 * An enum of all the textures, so they only have to get loaded once.
 */
public enum Textures {
    OBJECT_STONE(new Texture("images/objects/stone.png")), OBJECT_TREE(new Texture("images/objects/tree.png")),

    ENTITY_PLAYER(new Texture("images/entities/player/naked.png"));

    private Texture texture;

    Textures(Texture texture) {
        this.texture = texture;
    }

    public Texture getTexture() {
        return texture;
    }
}
