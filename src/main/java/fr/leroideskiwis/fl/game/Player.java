package fr.leroideskiwis.fl.game;

import fr.leroideskiwis.fl.utils.Utils;
import net.dv8tion.jda.core.entities.User;

public class Player {

    private Job job;
    private User user;
    private int level, money;
    private float health;
    private int maxHealth;
    private Inventory inventory;

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

    public Inventory getInventory() {
        return inventory;

    }
}
