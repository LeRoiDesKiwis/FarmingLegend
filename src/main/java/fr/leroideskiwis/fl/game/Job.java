package fr.leroideskiwis.fl.game;

public enum Job {

    ALL(),
    MINEUR("⛏", 20, "récolter de la roche lunaire"),
    PECHEUR("\uD83C\uDFA3", 18, "pêcher le monstre du lock ness"),
    AGRICULTEUR("\uD83C\uDF4E", 16, "récolter le haricot magique"),
    BUCHERON("\uD83D\uDD28", 20, "récolter au moins un bois de tout les types"),
    CHASSEUR("⚔", 22, "tuer un requin"),
    FORGERON("⚒", 20, "forger excalibur");

    private String emote;
    private int defaultHealth;
    private String finalQuest;

    Job(){

    }

    Job(String s, int defaultHealth, String finalQuest) {

        this.emote = s;
        this.defaultHealth = defaultHealth;
        this.finalQuest=finalQuest.toLowerCase();

    }

    public String getFinalQuest() {
        return finalQuest;
    }

    public String getEmote() {
        return emote;
    }
    public int getDefaultHealth(){return defaultHealth;}

}
