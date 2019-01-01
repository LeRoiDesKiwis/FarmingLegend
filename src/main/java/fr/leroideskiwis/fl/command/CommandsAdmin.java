package fr.leroideskiwis.fl.command;

import fr.leroideskiwis.fl.commands.Command;
import fr.leroideskiwis.fl.game.Player;
import fr.leroideskiwis.fl.utils.Utils;
import net.dv8tion.jda.core.entities.TextChannel;

import java.lang.reflect.Method;

public class CommandsAdmin {

    @Command(name="method",op=true)
    public void getGetters(Utils u, String[] args, TextChannel channel) throws Throwable {

        for(Method m : Class.forName(args[0]).getDeclaredMethods()){

            channel.sendMessage(u.format("Méthode %s retourne %s", m.getName(), m.getReturnType())).queue();

        }

    }

    @Command(name="levelup",op=true)
    public void level(Player p, TextChannel channel){

        p.levelUp(channel, true);
        channel.sendMessage("GG ! Tu es maintenant level **"+p.getLevel()+"** !").queue();

    }

}
