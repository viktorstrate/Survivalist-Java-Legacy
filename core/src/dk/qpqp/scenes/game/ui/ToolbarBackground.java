package dk.qpqp.scenes.game.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by viktorstrate on 05/08/2015.
 * Renders a toolbar background
 */
public class ToolbarBackground {

    Texture texture, textureSelected;
    Vector2 position;
    boolean selected;

    public ToolbarBackground(int x, int y) {
        texture = new Texture("images/ui/toolbarBackground.png");
        textureSelected = new Texture("images/ui/toolbarBackgroundSelected.png");
        position = new Vector2(x, y);
        this.selected = false;
    }

    public void render(SpriteBatch spriteBatch, int offsetX, int offsetY) {
        spriteBatch.begin();
        if (!selected)
            spriteBatch.draw(texture, position.x + offsetX, position.y + offsetY);
        else
            spriteBatch.draw(textureSelected, position.x + offsetX, position.y + offsetY);
        spriteBatch.end();
    }

    public void update(float dt) {

    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
