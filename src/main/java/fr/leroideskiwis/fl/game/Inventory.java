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

    public void stack(){

        for(int a = 0; a < items.size(); a++){

            for(int b = a+1; b < items.size(); b++){

                if(items.get(a).getMaterial() == items.get(b).getMaterial()){

                    items.get(a).setCount(items.get(b).getCount()+items.get(a).getCount());

                    items.remove(items.get(b));

                }

            }

        }

    }

    public void addItem(Item item){

        items.add(item);
        stack();

    }

    public List<Item> getItems() {
        return items;
    }
}
