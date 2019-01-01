package fr.leroideskiwis.fl.game;

public enum Material {

    //MINEUR
    MOON_STONE(Job.MINEUR, 0.0001, 30),
    STONE(Job.MINEUR, 0.5),
    COAL(Job.MINEUR, 0.25),
    IRON(Job.MINEUR, 0.1, 5),
    DIAMOND(Job.MINEUR, 0.05, 10),
    EMERALD(Job.MINEUR, 0.01, 15),

    //PECHEUR
    CLOWNFISH(Job.PECHEUR),
    FISH(Job.PECHEUR),
    SALMON(Job.PECHEUR),
    COOKED_CLOWNFISH(Job.ALL),
    COOKED_FISH(Job.ALL),
    COOKED_SALMON(Job.ALL),

    //AGRICULTEUR
    APPLE(Job.AGRICULTEUR),
    WHEAT(Job.AGRICULTEUR),
    POTATO(Job.AGRICULTEUR),
    PUMPKIN(Job.AGRICULTEUR),
    CARROT(Job.AGRICULTEUR),

    //BUCHERON
    ACACIA(Job.BUCHERON),
    FIR(Job.BUCHERON),
    OAK(Job.BUCHERON),

    //CHASSEUR
    SHARK(Job.CHASSEUR, 0.0001),
    WOLF(Job.CHASSEUR, 0.99),
    PIG(Job.CHASSEUR, 0.99),

    //FORGERON
    EXCALIBUR(Job.FORGERON),
    DIAMOND_SWORD(Job.FORGERON),
    IRON_SWORD(Job.FORGERON);


    private final Job job;
    private double chance;
    private int level;

    Material(Job job) {

        this(job, 0.3333);

    }

    Material(Job job, double i){

        this(job, i, 1);

    }

    Material(Job job, double v, int i) {

        this.job = job;
        this.chance = v;
        this.level = i;

    }

    public int getLevel() {
        return level;
    }

    public Job getJob() {
        return job;
    }

    public double getChance() {
        return chance;
    }
}
