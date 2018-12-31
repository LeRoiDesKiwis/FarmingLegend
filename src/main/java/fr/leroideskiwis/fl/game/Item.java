package fr.leroideskiwis.fl.game;

public class Item {

    private Material material;
    private int count;

    public Item(Material material, int count) {
        this.material = material;
        this.count = count;
    }

    public Material getMaterial() {
        return material;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int i) {

        this.count = i;

    }
}
