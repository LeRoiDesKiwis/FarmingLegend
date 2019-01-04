package fr.leroideskiwis.fl.utils;

import fr.leroideskiwis.fl.Main;
import fr.leroideskiwis.fl.game.Item;
import fr.leroideskiwis.fl.game.Job;
import fr.leroideskiwis.fl.game.Material;
import fr.leroideskiwis.fl.game.Player;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.requests.RestAction;

import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;
import java.util.List;

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

    public void startSecureThread(Runnable runnable, String name){

        new Thread(() -> {

            Thread th = new Thread(runnable);
            if(name != null) th.setName(name);
            th.setDaemon(true);
            th.start();

            try {
                th.join(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(th.isAlive()){

                while(th.getState() != Thread.State.TIMED_WAITING & th.getState() !=Thread.State.WAITING) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                th.interrupt();
                if(th.isAlive()) th.stop();
                System.err.println("Le thread "+th.getName()+" a été interrompu par sécurité.");

            }

        }, "Sous-thread").start();

    }

    public Object[] stackAnItem(Material mat, int stack, List<Item> items){

        int count = 0;

        Item item = new Item(mat, 0);

        while(count != stack){

            boolean isBreak = false;
            int current = count;

            for(int a = 0; a < items.size(); a++){

                for(int b = a+1; b < items.size(); b++){

                    if(items.get(a).getMaterial() == items.get(b).getMaterial()) {
                        count++;
                        isBreak = true;

                        item.setCount(item.getCount()+1);
                        items.remove(items.get(b));
                        a--;

                        break;
                    }

                }

                if(isBreak) break;

            }

            if(count == current) return null;

        }

        if(item.getCount() < stack) return null;

        return new Object[]{items, item};

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
