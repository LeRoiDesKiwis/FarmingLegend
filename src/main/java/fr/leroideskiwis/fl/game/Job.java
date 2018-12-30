package fr.leroideskiwis.fl.game;

public enum Job {

    MINEUR("⛏"), PECHEUR("\uD83C\uDFA3"), AGRICULTEUR("\uD83C\uDF4E");

    private String emote;

    Job(String s) {

        this.emote = s;

    }

    public String getEmote() {
        return emote;
    }
}
