package fr.leroideskiwis.fl.command;

import fr.leroideskiwis.fl.Main;
import fr.leroideskiwis.fl.commands.Command;
import fr.leroideskiwis.fl.commands.CommandCore;
import fr.leroideskiwis.fl.commands.SimpleCommand;
import fr.leroideskiwis.fl.game.Item;
import fr.leroideskiwis.fl.game.Player;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;

import java.awt.*;

public class CommandsBasics {

    @Command(name="help", description="affiche la liste des commandes")
    public void help(Player p, Main main, CommandCore core, Member m, User user, TextChannel channel){

        EmbedBuilder builder = new EmbedBuilder()
                .setColor(Color.GRAY);


        int count = 0;

        for(SimpleCommand cmd : core.getSimpleCommands()){

            if((!cmd.needOp() || main.checkDev(user)) && core.checkJob(p, cmd.getJob())) {
                builder.addField(cmd.getName(), cmd.getDescription(), false);
                count++;
            }

        }

        builder.setTitle(count+" commandes avec le préfixe "+main.getPrefixeAsString());

        channel.sendMessage(builder.build()).queue();

    }

    @Command(name="inventory")
    public void inventory(Player p, TextChannel channel){

        EmbedBuilder builder = new EmbedBuilder().setColor(Color.ORANGE).setAuthor("Inventaire de "+p.getUser().getName(), null, p.getUser().getAvatarUrl());

        if(p.getInventory().getItems().isEmpty()) builder.setDescription("Vous n'avez rien dans votre inventaire !");
        else {
            for (Item item : p.getInventory().getItems()) {

                builder.addField(item.getMaterial().toString().toLowerCase(), item.getCount() + "", false);

            }
        }

        channel.sendMessage(builder.build()).queue();

    }

    @Command(name="profile")
    public void profile(Player p, TextChannel channel){

        EmbedBuilder builder = new EmbedBuilder().setColor(Color.ORANGE).setAuthor("Profile de "+p.getUser().getName(), null, p.getUser().getAvatarUrl());

        builder.addField("Job :", p.getJob().toString().toLowerCase()+" "+p.getJob().getEmote(), false);
        builder.addField(":heart: Vie :", p.getHealth()+"/"+p.getMaxHealth(), true);
        builder.addField(":moneybag: Argent :", p.getMoney()+"€", false);
        builder.addField(":trophy: Niveau : ", p.getLevel()+"", true);

        channel.sendMessage(builder.build()).queue();

    }

    @Command(name="github",description="Obtenir le lien du github")
    public void github(TextChannel channel){

        channel.sendMessage("Vous voulez aider au développement du bot ou vous êtes juste curieux de savoir comment il est codé ? Alors ce lien est pour vous : https://github.com/LeRoiDesKiwis/FarmingLegend/").queue();

    }

}
