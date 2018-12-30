package fr.leroideskiwis.fl.game;

import net.dv8tion.jda.core.entities.User;

public class Player {

    private Job job;
    private User user;

    public Player(Job job, User user) {
        this.job = job;
        this.user = user;
    }

    public Job getJob() {
        return job;
    }

    public User getUser() {
        return user;
    }
}
