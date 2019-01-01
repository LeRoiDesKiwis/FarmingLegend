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
        return items;
    }

    public void removeItem(Material material, int count) {

        for(int i = 0; i < count; i++){

            for(Item item : items){

                if(item.getMaterial() == material){
                    items.remove(item);
                    break;
                }

            }

        }

    }
}
