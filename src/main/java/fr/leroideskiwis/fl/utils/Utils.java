package fr.leroideskiwis.fl.utils;

import fr.leroideskiwis.fl.Main;
import fr.leroideskiwis.fl.game.Job;
import fr.leroideskiwis.fl.game.Material;
import fr.leroideskiwis.fl.game.Player;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.requests.RestAction;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Utils {

    private Main main;

    public Utils(Main main){

        this.main = main;

    }

    public List<Material> getMaterialJobs(Job job){

        List<Material> mats = new ArrayList<>();

        for(Material mat : Material.values()){

           if(mat.getJob() == job) mats.add(mat);

        }

        return mats;

    }

    public String firstMaj(String s){

        String finalS = "";

        for(char c : s.toCharArray()){

            if(c == s.toCharArray()[0]) finalS+=Character.toUpperCase(c);
            else finalS+=c;


        }
        return finalS;

    }

    public Utils(){}

    public Object floatInt(Float f){

        if(f % 1 == 0) return f.intValue();
        else return f;

    }

    public String format(String s, Object... args){

        for(Object o : args){

            s = s.replaceFirst("%s", o+"");

        }

        return s;

    }

    public Job getJobByEmote(String emote){

        for(Job job : Job.values()){
            if(job.getEmote() == null) continue;

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
