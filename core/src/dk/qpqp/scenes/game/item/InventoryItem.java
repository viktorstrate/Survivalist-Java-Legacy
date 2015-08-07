package dk.qpqp.scenes.game.item;

/**
 * Created by viktorstrate on 06/08/2015.
 * An item in the inventory
 */
public class InventoryItem {

    private Material material;
    private int amount = 0;
    private int maxStackSize;

    public InventoryItem(Material material) {
        this(material, 1);
        maxStackSize = 99;
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
        return maxStackSize;
    }

    public void setMaxStackSize(int maxStackSize) {
        this.maxStackSize = maxStackSize;
    }
}
