package dk.qpqp.utills;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import dk.qpqp.Game;

/**
 * Created by viktorstrate on 03/08/2015.
 * A list of constant variables
 */
public final class Constants {
    public static final float PPM = 20;
    public static final int TILE_SIZE = 32;
    public static final BitmapFont FONT_HUGE = new BitmapFont(Gdx.files.internal("fonts/font.fnt"));
    public static final BitmapFont FONT_BIG = new BitmapFont(Gdx.files.internal("fonts/font.fnt"));
    public static final BitmapFont FONT_MEDIUM = new BitmapFont(Gdx.files.internal("fonts/font.fnt"));
    public static final BitmapFont FONT_SMALL = new BitmapFont(Gdx.files.internal("fonts/font.fnt"));

    public static void updateFontScale(int screenWidth, int screenHeight) {
        float scale = ((screenHeight + screenWidth) / 2f) / ((Game.WIDTH + Game.HEIGHT) / 2f);

        FONT_HUGE.getData().setScale(0.6f * scale, 0.6f * scale);
        FONT_BIG.getData().setScale(0.2f * scale, 0.2f * scale);
        FONT_MEDIUM.getData().setScale(0.1f * scale, 0.1f * scale);
        FONT_SMALL.getData().setScale(0.075f * scale, 0.075f * scale);
    }
}
