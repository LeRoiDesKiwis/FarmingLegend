package fr.leroideskiwis.fl.game;

import fr.leroideskiwis.fl.utils.Utils;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;

import java.awt.*;

public class Player {

    private Job job;
    private User user;
    private int level, money;
    private float health;
    private int maxHealth;
    private Inventory inventory;
    private int xp;

    public Player(Job job, User user) {
        this.job = job;
        this.user = user;
        this.maxHealth = job.getDefaultHealth();
        this.health = this.maxHealth;
        this.level = 1;
        this.money = 50;
        this.inventory = new Inventory(this);
    }

    public Job getJob() {
        return job;
    }

    public User getUser() {
        return user;
    }

    public int getLevel() {
        return level;
    }

    public Object getHealth() {

        if(health > maxHealth) health = maxHealth;

        return new Utils().floatInt(health);
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getMoney() {
        return money;
    }

    public int getNeededXp(){

        return (int)(level*100*0.75);

    }

    public Inventory getInventory() {
        return inventory;

    }

    public void levelUp(TextChannel channel){
        levelUp(channel, false);
    }

    public void levelUp(TextChannel channel, boolean force) {
        if(getXp() >= getNeededXp() || force) {
            level++;
            maxHealth+=1;
            if (channel != null) channel.sendMessage(new EmbedBuilder()
                    .setColor(Color.ORANGE)
                    .setAuthor(getUser().getName() + " à level up !", null, getUser().getAvatarUrl())
                    .setDescription("Il est maintenant niveau " + level + " !")
                    .build()).queue();
        }
    }

    public int getXp() {

        return xp;
    }
}
