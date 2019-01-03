package fr.leroideskiwis.fl.commands;

import fr.leroideskiwis.fl.RoleCommand;
import fr.leroideskiwis.fl.game.Job;

import java.lang.reflect.Method;

public class SimpleCommand {

    private RoleCommand role;
    private Method m;
    private String name;
    private String description;
    private Object object;
    private Job[] job;
    private String syntaxe;

    public SimpleCommand(Object o, Method m, String name, String description, Job[] job, RoleCommand op, String syntaxe) {
        this.m = m;
        this.object = o;
        this.name = name;
        this.description = description;
        this.job = job;
        this.role = op;
        this.syntaxe = syntaxe;
    }

    public Job[] getJob() {
        return job;
    }

    public String getSyntaxe() {
        return syntaxe;
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

    public RoleCommand getNeededRole() {
        return role;
    }
}
