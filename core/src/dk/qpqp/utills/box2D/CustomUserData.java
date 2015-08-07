package dk.qpqp.utills.box2D;

/**
 * Created by viktorstrate on 07/08/2015.
 * Custom user data for box 2d bodies
 */
public class CustomUserData {
    private Box2DTag tag;
    private Object bodyClass;

    public CustomUserData(Box2DTag tag, Object bodyClass) {
        this.tag = tag;
        this.bodyClass = bodyClass;
    }

    public Box2DTag getTag() {
        return tag;
    }

    public Object getBodyClass() {
        return bodyClass;
    }
}
