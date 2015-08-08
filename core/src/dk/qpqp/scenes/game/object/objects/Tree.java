package dk.qpqp.scenes.game.object.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import dk.qpqp.scenes.game.DestroyableGameObject;
import dk.qpqp.scenes.game.GameScene;
import dk.qpqp.utills.Textures;

/**
 * Created by viktorstrate on 05/08/2015.
 * A tree object in the scene
 */
public class Tree extends DestroyableGameObject {

    private Texture texture;
    private GameScene gameScene;

    public Tree(int x, int y, GameScene gameScene) {
        super(x, y, 64, 64, gameScene);
        texture = Textures.OBJECT_TREE.getTexture();

        this.gameScene = gameScene;
    }

    @Override
    public Texture getTexture() {
        return texture;
    }

    @Override
    public void update(float dt) {
        super.update(dt);

        if (mouseOver()) {
            if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
                System.out.println("You clicked a tree");
            }
        }
    }
}
