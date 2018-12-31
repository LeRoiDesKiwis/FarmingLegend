package fr.leroideskiwis.fl.command;

import fr.leroideskiwis.fl.commands.Command;
import fr.leroideskiwis.fl.game.Item;
import fr.leroideskiwis.fl.game.Job;
import fr.leroideskiwis.fl.game.Material;
import fr.leroideskiwis.fl.game.Player;

public class CommandsFarm {

    @Command(name="mine",job = Job.MINEUR)
    public void onMine(Player player){

        player.getInventory().addItem(new Item(Material.COAL, 10));

    }

    @Command(name="peche",job = Job.PECHEUR)
    public void onPeche(){

    }
    @Command(name="recolte",job = Job.AGRICULTEUR)
    public void onRecolte(){

    }
    @Command(name="buche",job = Job.BUCHERON)
    public void onBuche(){

    }
    @Command(name="chasse",job = Job.CHASSEUR)
    public void onChasse(){

    }
    @Command(name="forge",job = Job.FORGERON)
    public void onForge(){

    }

}
