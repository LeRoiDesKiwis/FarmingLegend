package fr.leroideskiwis.fl.reactionmenu;

import fr.leroideskiwis.fl.Main;

import java.util.ArrayList;
import java.util.List;

public class ReactionCore {

    private List<ReactionMenu> menus = new ArrayList<>();
    private Main main;

    public List<ReactionMenu> getMenus() {
        return menus;
    }

    public void addMenu(ReactionMenu menu){
        menus.add(menu);

    }

    public Main getMain() {
        return main;
    }

    public ReactionCore(Main main) {
        this.main = main;
        main.getJda().addEventListener(new ReactionEvent(this));

    }


    public void deleteMenu(ReactionMenu reactionMenu) {

        menus.remove(reactionMenu);

    }
}
