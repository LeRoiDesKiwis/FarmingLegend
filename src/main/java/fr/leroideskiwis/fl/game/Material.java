package fr.leroideskiwis.fl.game;

public enum Material {

    //MINEUR
    MOON_STONE(Job.MINEUR, 1),
    STONE(Job.MINEUR, 500),
    COAL(Job.MINEUR, 200),
    IRON(Job.MINEUR, 100),
    DIAMOND(Job.MINEUR, 50),
    EMERALD(Job.MINEUR, 20),

    //PECHEUR
    CLOWNFISH(Job.PECHEUR),
    FISH(Job.PECHEUR),
    SALMON(Job.PECHEUR),

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
    SHARK(Job.CHASSEUR, 1),
    WOLF(Job.CHASSEUR, 1000),
    PIG(Job.CHASSEUR, 1000),

    //FORGERON
    EXCALIBUR(Job.FORGERON),
    DIAMOND_SWORD(Job.FORGERON),
    IRON_SWORD(Job.FORGERON);


    private final Job job;
    private int chance;

    Material(Job job) {

        this.job = job;
        this.chance = 1;

    }

    Material(Job job, int i){

        this.job = job;
        this.chance = i;

    }

    public Job getJob() {
        return job;
    }

    public int getChance() {
        return chance;
    }
}
