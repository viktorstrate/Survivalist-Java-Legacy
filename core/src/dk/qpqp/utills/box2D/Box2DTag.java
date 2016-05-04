package dk.qpqp.utills.box2D;

import com.badlogic.gdx.physics.box2d.Filter;

/**
 * Created by viktorstrate on 07/08/2015.
 * Box 2D Tags for custom user data
 */
public enum Box2DTag {
    ITEM((short)0x4, (short)0,(short)0xFF),
    GAME_OBJECT((short)0x8, (short)0,(short)0xFF),
    PLAYER((short)0x2, (short)0,(short)(0xFF^ITEM.getCategoryBits())),
    LIGHT((short)0x1, (short)0,(short)(GAME_OBJECT.getCategoryBits()|PLAYER.getCategoryBits()));

    short categoryBits, groupIndex, maskBits;

    Box2DTag(short categoryBits, short groupIndex, short maskBits) {
        this.categoryBits = categoryBits;
        this.groupIndex = groupIndex;
        this.maskBits = maskBits;
    }

    public short getCategoryBits() {
        return categoryBits;
    }

    public short getGroupIndex() {
        return groupIndex;
    }

    public short getMaskBits() {
        return maskBits;
    }

    public Filter getContactFilter(){
        Filter f = new Filter();
        f.categoryBits = getCategoryBits();
        f.groupIndex = getGroupIndex();
        f.maskBits = getMaskBits();
        return f;
    }
}
