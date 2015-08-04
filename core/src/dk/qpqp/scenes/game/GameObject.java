package dk.qpqp.scenes.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by viktorstrate on 04/08/2015.
 */
public interface GameObject {
    void render(SpriteBatch spriteBatch);
    void update(float dt);
    Vector2 getPosition();
    void dispose();
}
