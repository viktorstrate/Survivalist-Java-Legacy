package dk.qpqp.scenes.game.listeners;

import com.badlogic.gdx.physics.box2d.*;
import dk.qpqp.scenes.game.entity.Player;
import dk.qpqp.scenes.game.item.EntityItem;
import dk.qpqp.utills.box2D.Box2DTag;
import dk.qpqp.utills.box2D.CustomUserData;

/**
 * Created by viktorstrate on 07/08/2015.
 * Listens for collisions
 */
public class CollisionLister implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Body[] bodies = new Body[2];
        bodies[0] = contact.getFixtureA().getBody();
        bodies[1] = contact.getFixtureB().getBody();

        for (int i = 0; i < 2; i++) {
            Body b1 = bodies[i];
            Body b2 = bodies[i == 0 ? 1 : 0];


            if (b1.getUserData() != null && b2.getUserData() != null) {

                CustomUserData b1UserData = (CustomUserData) b1.getUserData();
                CustomUserData b2UserData = (CustomUserData) b2.getUserData();

                if (b1UserData.getTag() == Box2DTag.PLAYER) {
                    Player player = (Player) b1UserData.getBodyClass();

                    if (b2UserData.getTag() == Box2DTag.ITEM) {
                        EntityItem entityItem = (EntityItem) b2UserData.getBodyClass();
                        player.pickupItem(entityItem);
                    }
                }

            } else break;
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
