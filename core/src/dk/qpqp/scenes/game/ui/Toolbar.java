package dk.qpqp.scenes.game.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.qpqp.Game;
import dk.qpqp.scenes.game.GameScene;
import dk.qpqp.scenes.game.Graphic;

/**
 * Created by viktorstrate on 05/08/2015.
 * Renders the toolbar
 */
public class Toolbar implements Graphic {

    private GameScene gameScene;
    private UIHandler uiHandler;
    private ToolbarBackground[] toolbars;
    private int selected = 0;

    public Toolbar(GameScene gameScene, UIHandler uiHandler) {
        this.gameScene = gameScene;
        this.uiHandler = uiHandler;

        toolbars = new ToolbarBackground[9];

        for (int i = 0; i < 9; i++) {
            toolbars[i] = new ToolbarBackground(2 + 30 * i, 2);
            if (i == selected)
                toolbars[i].setSelected(true);
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        for (int i = 0; i < 9; i++) {
            toolbars[i].render(spriteBatch, Math.round(0 - uiHandler.getHudCam().viewportWidth / 2 / Game.SCALE), Math.round(0 - uiHandler.getHudCam().viewportHeight / 2 / Game.SCALE));
        }
    }

    @Override
    public void update(float dt) {
        for (int i = 0; i < 9; i++) {
            toolbars[i].update(dt);
        }
    }

    public void setSelected(int i) {
        this.selected = i;
    }
}
