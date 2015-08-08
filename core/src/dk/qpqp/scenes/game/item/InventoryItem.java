package dk.qpqp.scenes.game.item;

/**
 * Created by viktorstrate on 06/08/2015.
 * An item in the inventory
 */
public class InventoryItem {

    private Material material;
    private int amount = 0;

    public InventoryItem(Material material) {
        this(material, 1);
    }

    public InventoryItem(Material material, int amount) {
        this.material = material;
        this.amount = amount;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getMaxStackSize() {
        return material.getMaxStackSize();
    }
}
