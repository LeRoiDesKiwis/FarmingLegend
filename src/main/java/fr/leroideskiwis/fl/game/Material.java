package fr.leroideskiwis.fl.game;

public enum Material {

    //MINEUR
    MOON_STONE(Job.CHASSEUR),
    COAL(Job.CHASSEUR),
    IRON(Job.CHASSEUR),
    DIAMOND(Job.CHASSEUR),
    EMERALD(Job.CHASSEUR),

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
    SHARK(Job.CHASSEUR),
    WOLF(Job.CHASSEUR),
    PIG(Job.CHASSEUR),

    //FORGERON
    EXCALIBUR(Job.FORGERON),
    DIAMOND_SWORD(Job.FORGERON),
    IRON_SWORD(Job.FORGERON);


    private final Job job;

    Material(Job job) {

        this.job = job;

    }

    public Job getJob() {
        return job;
    }
}
