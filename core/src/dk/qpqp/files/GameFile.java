package dk.qpqp.files;

import com.badlogic.gdx.math.Vector2;
import dk.qpqp.scenes.game.GameID;
import dk.qpqp.scenes.game.GameObject;
import dk.qpqp.scenes.game.GameScene;
import dk.qpqp.scenes.game.entity.Player;
import dk.qpqp.scenes.game.item.entities.EntityStone;
import dk.qpqp.scenes.game.object.objects.Stone;
import dk.qpqp.scenes.game.object.objects.Tree;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by viktorstrate on 17/04/16.
 */
public class GameFile {

    private ArrayList<GameObject> gameObjects;

    public GameFile(ArrayList<GameObject> gameObjects) {
        this.gameObjects = gameObjects;
    }

    public void save(String fileName){
        ByteBuffer buffer = ByteBuffer.allocate(100000);

        for(GameObject g: gameObjects){
            buffer.put((byte)2);
            buffer.putShort((short)g.getId().ordinal());
            buffer.putFloat(g.getPosition().x);
            buffer.putFloat(g.getPosition().y);
            buffer.put((byte)0);
        }

        byte[] data = Arrays.copyOfRange(buffer.array(), 0, buffer.position());

        try {
            Path file = Paths.get(fileName);
            Files.write(file, data);
            System.out.println("Saved game state to file: "+fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load(String fileName, GameScene gameScene){

        byte[] data;

        try {
            Path file = Paths.get(fileName);
            data = Files.readAllBytes(file);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        ByteBuffer buffer = ByteBuffer.wrap(data);
        int count = 0;

        while(buffer.remaining()>0){
            short key = buffer.get();
            if(key==(byte)2){
                GameID id = GameID.values()[buffer.getShort()];
                Vector2 position = new Vector2(buffer.getFloat(), buffer.getFloat());

                byte extra = buffer.get();

                if(extra==(byte)0){

                    switch (id){
                        case PLAYER:
                            gameScene.setPlayer(new Player((int)position.x, (int)position.y, gameScene, true));
                            break;
                        case TREE:
                            gameScene.addGameObject(new Tree((int)position.x, (int)position.y, gameScene));
                            break;
                        case STONE:
                            gameScene.addGameObject(new Stone((int)position.x, (int)position.y, gameScene));
                            break;
                        case ENTITY_STONE:
                            gameScene.addGameObject(new EntityStone((int)position.x, (int)position.y, gameScene, false));
                            break;
                    }

                    System.out.println("\nSuccessfully loaded GameObject "+count);
                    System.out.println("ID: "+id+", Position X: "+position.x+" Y: "+position.y);
                    count++;
                } else {
                    System.out.println("Something went wrong");
                    break;
                }
            }
        }
    }

}
