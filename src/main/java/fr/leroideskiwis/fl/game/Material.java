package fr.leroideskiwis.fl.game;

public enum Material {

    //MINEUR
    MOON_STONE(Job.MINEUR, 0.0001, 30, 10000),
    STONE(Job.MINEUR, 0.5, 1),
    COAL(Job.MINEUR, 0.25, 5),
    IRON(Job.MINEUR, 0.1, 5, 10),
    DIAMOND(Job.MINEUR, 0.05, 10, 100),
    EMERALD(Job.MINEUR, 0.01, 15, 500),

    //PECHEUR
    CLOWNFISH(Job.PECHEUR, 2, 1, 5),
    FISH(Job.PECHEUR, 2, 1, 5),
    SALMON(Job.PECHEUR, 2, 1, 5),
    COOKED_CLOWNFISH(Job.ALL, 2, 1, 5),
    COOKED_FISH(Job.ALL, 2, 1, 5),
    COOKED_SALMON(Job.ALL, 2, 1, 5),

    //AGRICULTEUR
    APPLE(Job.AGRICULTEUR, 2, 1, 5),
    WHEAT(Job.AGRICULTEUR, 2, 1, 5),
    POTATO(Job.AGRICULTEUR, 2, 1, 5),
    PUMPKIN(Job.AGRICULTEUR, 2, 1, 5),
    CARROT(Job.AGRICULTEUR, 2, 1, 5),

    //BUCHERON
    ACACIA(Job.BUCHERON, 2, 1, 5),
    FIR(Job.BUCHERON, 2, 1, 5),
    OAK(Job.BUCHERON, 2, 1, 5),

    //CHASSEUR
    SHARK(Job.CHASSEUR, 0.0001, 1, 5),
    WOLF(Job.CHASSEUR, 0.99, 1, 5),
    PIG(Job.CHASSEUR, 0.99, 1, 5),

    //FORGERON
    EXCALIBUR(Job.FORGERON, 2, 1, 5),
    DIAMOND_SWORD(Job.FORGERON, 2, 1, 5),
    IRON_SWORD(Job.FORGERON, 2, 1, 5);


    private final Job job;
    private double chance;
    private int level;
    private int price;

    Material(Job job) {

        this(job, 0.3333);

    }

    Material(Job job, double i){

        this(job, i, 1);

    }

    Material(Job job, double v, int i) {

        this(job, v, i, 5);

    }

    Material(Job job, double v, int lvl, int price) {
        this.job = job;
        this.chance = v;
        this.level = lvl;
        this.price = price;
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

    public int getPrice() {
        return price;
    }
}
