package fr.leroideskiwis.fl.command;

import fr.leroideskiwis.fl.Main;
import fr.leroideskiwis.fl.commands.Command;
import fr.leroideskiwis.fl.commands.CommandCore;
import fr.leroideskiwis.fl.commands.SimpleCommand;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;

import java.awt.*;

public class CommandsBasics {

    @Command(name="help", description="affiche la liste des commandes")
    public void help(Main main, CommandCore core, Member m, User user, TextChannel channel){

        EmbedBuilder builder = new EmbedBuilder()
                .setColor(Color.GRAY)
                .setTitle(core.getSimpleCommands().size()+" commandes avec le préfixe "+main.getPrefixeAsString());

        for(SimpleCommand cmd : core.getSimpleCommands()){

            builder.addField(cmd.getName(), cmd.getDescription(), false);

        }

        channel.sendMessage(builder.build()).queue();

    }

}
