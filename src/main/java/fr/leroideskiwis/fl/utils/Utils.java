package fr.leroideskiwis.fl.utils;

import fr.leroideskiwis.fl.Main;
import fr.leroideskiwis.fl.game.Job;
import fr.leroideskiwis.fl.game.Player;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.requests.RestAction;

import java.awt.*;

public class Utils {

    private Main main;

    public Utils(Main main){

        this.main = main;

    }

    public Job getJobByEmote(String emote){

        for(Job job : Job.values()){

            if(job.getEmote().equals(emote)) return job;

        }
        return null;

    }

    public MessageEmbed embedError(String s){
        return new EmbedBuilder().setColor(Color.RED).setDescription(s).setTitle("Erreur !").build();
    }

    public Player getPlayer(User user){

        for(Player p : main.getPlayers()){

            if(p.getUser().equals(user)) return p;

        }

        return null;
    }

}
