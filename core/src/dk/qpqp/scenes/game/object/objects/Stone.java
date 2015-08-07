package dk.qpqp.scenes.game.object.objects;

import com.badlogic.gdx.graphics.Texture;
import dk.qpqp.scenes.game.DestroyableGameObject;
import dk.qpqp.scenes.game.GameScene;
import dk.qpqp.scenes.game.item.Material;
import dk.qpqp.scenes.game.item.entities.EntityStone;
import dk.qpqp.scenes.game.object.generators.StoneGenerator;
import dk.qpqp.utills.Textures;

/**
 * Created by viktorstrate on 04/08/2015.
 * A stone object in the scene
 */
public class Stone extends DestroyableGameObject {

    private Texture texture;
    private GameScene gameScene;

    public Stone(int x, int y, GameScene gameScene) {
        super(x, y, 32, 32, gameScene);

        this.gameScene = gameScene;

        texture = Textures.OBJECT_STONE.getTexture();

        for (int i = 0; i < 5; i++) {
            drops.add(new EntityStone(gameScene, Material.STONE));
        }
    }

    @Override
    public void update(float dt) {
        super.update(dt);
    }

    @Override
    public Texture getTexture() {
        return texture;
    }

    @Override
    public void destroy() {
        super.destroy();
        if (gameScene.getObjectSpawnHandler().getStoneGenerator().getGameObjects() != null) {
            StoneGenerator stoneGenerator = gameScene.getObjectSpawnHandler().getStoneGenerator();
            stoneGenerator.removeGameObject(this);
        }

    }
}
