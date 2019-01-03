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
    private float food;
    private int energy;

    public Player(Job job, User user) {
        this.job = job;
        this.user = user;
        this.maxHealth = job.getDefaultHealth();
        this.food = 10f;
        this.energy = 100;
        this.health = this.maxHealth;
        this.level = 1;
        this.money = 50;
        this.inventory = new Inventory(this);
        
        Thread thread = new Thread(() -> {

            try {

                while(true) {
                    updateEnergy();
                    Thread.sleep(5000);
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }

        }, "energy-"+user.getId());
        thread.setDaemon(true);
        thread.start();

    }

    public void addFood(float f){
        setFood(food +f);
    }

    public int getMaxEnergy(){ return 100; }

    public void checkEnergy(){
        if(energy > getMaxEnergy()) this.energy = getMaxEnergy();
        if(energy < 0) this.energy = 0;

    }

    public void updateEnergy(){

        this.energy = energy + 1;
        checkEnergy();

    }

    public boolean checkEnoughEnergy(int enough){

        return energy > enough;

    }

    public void removeEnergy(int next){

        this.energy -= next;
        checkEnergy();

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

    public float getFood() {
        return food;
    }

    public void setFood(float f){

        this.food = f;

        if(food > 10) food = 10;

        if(food < 0) food =0;

    }

    public void removeFood(float f){

        setFood(food -f);

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
                    .setAuthor(getUser().getName() + " à level up ! Vous avez re-gagner de la vie et de la nourriture ! (votre barre d'énergie à aussi été rechargée)", null, getUser().getAvatarUrl())
                    .setDescription("Il est maintenant niveau " + level + " !")
                    .build()).queue();

            addFood(4f);
            addHealth(getMaxHealth()/4);
        }
    }

    private void addHealth(int i) {

        this.health+=i;
    }

    public int getXp() {

        return xp;
    }

    public int getEnergy() {
        return energy;
    }

    public void addXp(int i) {
        this.xp+=i;

    }
}
