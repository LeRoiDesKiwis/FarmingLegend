package fr.leroideskiwis.fl.utils;

import fr.leroideskiwis.fl.Main;
import fr.leroideskiwis.fl.game.*;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.User;

import java.awt.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Utils {

    private Main main;

    public Utils(Main main){

        this.main = main;

    }

    public List<Material> getMaterialJobs(Job job){

        return getMaterialJobs(Arrays.asList(job));

    }

    public boolean contains(Object[] objects, Object o){

        for(Object obj : objects){

            if(obj.equals(o)) return true;

        }

        return false;

    }

    public List<Material> getMaterialJobs(List<Job> jobs){

        List<Material> mats = new ArrayList<>();

        for(Material mat : Material.values()){

           if(jobs.contains(mat.getJob())) mats.add(mat);

        }

        return mats;

    }

    public String firstMaj(String s){

        char[] chars = s.toCharArray();
        chars[0] = Character.toUpperCase(chars[0]);

        return new String(chars);

    }

    public boolean checkArgs(String[] args, int count){

        return args.length >= count;

    }

    public Utils(){}

    public Object floatInt(Float f){

        if(f % 1 == 0) return f.intValue();
        else {

            BigDecimal bd = new BigDecimal(f);
            bd = bd.setScale(2, BigDecimal.ROUND_FLOOR);
            f = bd.floatValue();
            return f;

        }

    }

    public int getRandomNumber(int min, int max){

        return (int)(Math.random() * (max - min)+1) +min;

    }

    /**
     * @param mat
     * @param stack
     * @param inv
     * @return An arraylist for the first and a item for the deuxieme
     */

    public Item stackAnItem(Material mat, int stack, Inventory inv) {

        if(inv.getItem(mat).getCount() <= stack) return null;

        Item item = new Item(mat, 0);
        inv.unstack(inv.getItems());

        for (int i = 0; i < stack; i++) {

            for (Item it : inv.getItems()) {

                if (it.getMaterial() == mat && it.getCount() == 1) {
                    item.setCount(item.getCount() + 1);
                    inv.getItems().remove(it);
                    break;
                }

            }
        }

            inv.stack();

        return item;
    }

    public String generateStringWithRandomChars(){

        char[] chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();

        StringBuilder builder = new StringBuilder();

        for(int i = 0; i < 6; i++){

            builder.append(chars[new Random().nextInt(chars.length)]);

        }

        return builder.toString();

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
