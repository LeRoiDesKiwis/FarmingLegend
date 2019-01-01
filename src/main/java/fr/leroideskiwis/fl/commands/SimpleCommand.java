package fr.leroideskiwis.fl.commands;

import fr.leroideskiwis.fl.game.Job;

import java.lang.reflect.Method;

public class SimpleCommand {

    private boolean op;
    private Method m;
    private String name;
    private String description;
    private Object object;
    private Job[] job;

    public SimpleCommand(Object o, Method m, String name, String description, Job[] job, boolean op) {
        this.m = m;
        this.object = o;
        this.name = name;
        this.description = description;
        this.job = job;
        this.op = op;
    }

    public Job[] getJob() {
        return job;
    }

    public Method getMethod() {
        return m;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Object getObject() {
        return object;
    }

    public boolean needOp() {
        return op;
    }
}
