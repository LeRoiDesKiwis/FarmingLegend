package fr.leroideskiwis.fl.command;

import fr.leroideskiwis.fl.Main;
import fr.leroideskiwis.fl.commands.Command;
import fr.leroideskiwis.fl.game.Item;
import fr.leroideskiwis.fl.game.Job;
import fr.leroideskiwis.fl.game.Material;
import fr.leroideskiwis.fl.game.Player;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.TextChannel;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CommandsFarm {

    private Main main;

    public CommandsFarm(Main main) {
        this.main = main;
    }

    @Command(name="mine",job = Job.MINEUR)
    public void onMine(Player player, TextChannel channel){
        farm(player, channel);

    }

    @Command(name="peche",job = Job.PECHEUR)
    public void onPeche(Player player, TextChannel channel){
        farm(player, channel);

    }
    @Command(name="recolte",job = Job.AGRICULTEUR)
    public void onRecolte(Player player, TextChannel channel){
        farm(player, channel);

    }
    @Command(name="buche",job = Job.BUCHERON)
    public void onBuche(Player player, TextChannel channel){
        farm(player, channel);

    }
    @Command(name="chasse",job = Job.CHASSEUR)
    public void onChasse(Player player, TextChannel channel){
        farm(player, channel);

    }
    @Command(name="forge",job = Job.FORGERON)
    public void onForge(Player player, TextChannel channel){
        farm(player, channel);

    }

    public void farm(Player p, TextChannel channel){

        List<Material> matChance = new ArrayList<>();

        for(Material mat : main.getUtils().getMaterialJobs(p.getJob())){

            for(int i = 0; i < mat.getChance(); i++){

                matChance.add(mat);

            }

        }

        Item item = new Item(matChance.get(new Random().nextInt(matChance.size())), new Random().nextInt(5));

        channel.sendMessage(new EmbedBuilder().setColor(Color.ORANGE).setTitle("Vous avez gagner").addField(item.getMaterial().toString(), item.getCount()+"", false).build()).queue();

    }
}
