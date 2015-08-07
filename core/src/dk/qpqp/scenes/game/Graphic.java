package dk.qpqp.scenes.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by viktorstrate on 05/08/2015.
 * An interface for something that can be rendered
 */
public interface Graphic {
    void render(SpriteBatch spriteBatch);

    void update(float dt);
}
