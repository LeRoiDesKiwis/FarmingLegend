package fr.leroideskiwis.fl.game;

import fr.leroideskiwis.fl.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class Inventory {

    private List<Item> items = new ArrayList<>();
    private Player owner;

    public Inventory(Player p){
        this.owner = p;
    }

    /**
     *
     * @param mat
     * @return null if there a no item with this material and the item with the material if there are an item with this material
     */

    public Item getItem(Material mat){

        for(Item i : items){
            if(i.getMaterial() == mat) return i;
        }
        return null;

    }

    public void removeItem(Item item){

        if(items.contains(item)) items.remove(item);

    }

    public void replaceItem(Item current, Item next){

        if(items.contains(current)){

            removeItem(current);
            addItem(next);

        }

    }

    public void unstack(List<Item> items){

        for(int i = 0; i < items.size(); i++){

            Item item = items.get(i);

            if(item.getCount() > 1){

                for(int y = 0; y < item.getCount(); y++){
                    items.add(new Item(item.getMaterial(), 1));
                }

                items.remove(item);

            }

        }

    }

    public void setItems(List<Item> items){
        this.items = items;
    }

    public void stack(List<Item> items){

        for(int a = 0; a < items.size(); a++) {

            if (items.get(a).getCount() == 0) items.remove(items.get(a));

            for (int b = a + 1; b < items.size(); b++) {

                if (items.get(a).getMaterial() == items.get(b).getMaterial()) {

                    items.get(a).setCount(items.get(b).getCount() + items.get(a).getCount());

                    items.remove(items.get(b));
                    b--;

                }

            }
        }

    }

    public void stack(){

        stack(this.items);

    }

    public void addItems(List<Item> items){

        items.forEach(item -> addItem(item));

    }

    public void addItem(Item item){

        items.add(item);
        stack();

    }

    public List<Item> getItems() {
        stack();
        return items;
    }

    public List<Item> copyunstacked(){

        List<Item> copy = new ArrayList<>(items);
        unstack(copy);

        return copy;

    }

    public void removeItem(Material material, int count) {

        List<Item> copy = copyunstacked();

        for(int i = 0; i < count; i++){

            for(Item item : copy){

                if(item.getMaterial() == material){
                    copy.remove(item);
                    break;
                }

            }

        }

        setItems(copy);

    }
}
