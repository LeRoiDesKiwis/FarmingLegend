package fr.leroideskiwis.fl.command;

import fr.leroideskiwis.fl.Main;
import fr.leroideskiwis.fl.commands.Command;
import fr.leroideskiwis.fl.game.Item;
import fr.leroideskiwis.fl.game.Job;
import fr.leroideskiwis.fl.game.Material;
import fr.leroideskiwis.fl.game.Player;
import fr.leroideskiwis.fl.utils.MessageHandler;
import fr.leroideskiwis.fl.utils.Utils;
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

    @Command(name="cook", job={Job.PECHEUR, /*Job.CHASSEUR*/})
    public void onCook(TextChannel channel, Player player, String[] args){

        Material mat;
        Integer count;
        List<Item> items;

        try {

            mat = Material.valueOf(args[0].toUpperCase());
            count = Integer.parseInt(args[1]);

            if(mat.getJob() != player.getJob()) throw new Exception();

            items = new ArrayList<>();
            }catch(Throwable throwable) {

                channel.sendMessage(main.getUtils().embedError("Syntaxe : " + main.getPrefixeAsString() + "cook <nom du matériel> <nombre à cuir>")).queue();
                return;
        }

        if((player.getFood() == 0) || player.getFood()-count*0.05f <= 0){

            channel.sendMessage(new MessageHandler().embedError("Vous n'avez plus ou pas assez de nourriture ! (vous avez besoin de **"+count*0.05f+"** de nourriture et vous n'en avez que **"+ player.getFood()+"**)")).queue();

            return;
        }

            for(Item item : player.getInventory().getItems()){

                if(item.getMaterial() == mat) items.add(item);

            }

            player.getInventory().unstack(items);


            if(items.size() < count){
                channel.sendMessage(new Utils(main).embedError("Vous n'avez pas assez de "+mat.toString().toLowerCase()+" !")).queue();
                return;
            }
            player.getInventory().unstack(player.getInventory().getItems());

            for(int i = 0; i < count; i++){

                if(items.get(i).getMaterial() != mat) continue;

                switch(items.get(i).getMaterial()){
                    case CLOWNFISH:
                        player.getInventory().removeItem(items.get(i).getMaterial(), 1);
                        items.get(i).setType(Material.COOKED_CLOWNFISH);

                        break;
                    case FISH:

                        player.getInventory().removeItem(items.get(i).getMaterial(), 1);
                        items.get(i).setType(Material.COOKED_FISH);

                        break;
                    case SALMON:

                        player.getInventory().removeItem(items.get(i).getMaterial(), 1);
                        items.get(i).setType(Material.COOKED_SALMON);

                        break;

                }

            }

            player.getInventory().addItems(items);

            channel.sendMessage("Vous avez cuit "+count+" "+mat.toString().toLowerCase()+" !").queue();
            player.removeFood(0.05f*count);
            player.addXp(count/2);

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
        channel.sendMessage("Commande en développement...").queue();

    }

    public void farm(Player p, TextChannel channel){

        if(!p.checkEnoughEnergy(5)){
            channel.sendMessage(new MessageHandler().embedError(":zap: Vous n'avez plus assez d'énergie ! :confused:")).queue();

            return;
        }

        List<Item> items = new ArrayList<>();

        for(int i = 0; i < p.getFood(); i++) {

            if(Math.random() < 0.33) continue;

            Item item = null;

            while (item == null) {

                for (Material mat : main.getUtils().getMaterialJobs(p.getJob())) {

                    if(p.getLevel() < mat.getLevel()) continue;

                    if (Math.random() < (mat.getChance() > 1.0 ? 1.0/(double)main.getUtils().getMaterialJobs(p.getJob()).size() : mat.getChance())) {

                        int count = (int) (mat.getChance() * 10);

                        item = new Item(mat, new Random().nextInt(count < 1 ? 1 : count) + 1);
                        items.add(item);

                    }

                }
            }
        }

        if(items.isEmpty()) {

            channel.sendMessage(new EmbedBuilder().setColor(Color.RED)
                    .setTitle(p.getJob().getEmote()+"Impossible de farmer ! :sob:")
                    .setDescription("Oh tient ! Vous n'avez rien eu ! Vous avez surement faim :stuck_out_tongue:").build()).queue();

        }

        p.getInventory().stack(items);
        EmbedBuilder builder = new EmbedBuilder().setColor(Color.ORANGE).setTitle("Vous avez gagné");

        p.removeFood(0.1f);
        p.removeEnergy(5);
        p.addXp(new Utils().getRandomNumber(2, 7));

        items.forEach(i -> {
            p.getInventory().addItem(i);
            builder.addField(main.getUtils().firstMaj(i.getMaterial().toString().toLowerCase()), i.getCount()+"", true);

        });

        channel.sendMessage(builder.build()).queue();

    }



}
