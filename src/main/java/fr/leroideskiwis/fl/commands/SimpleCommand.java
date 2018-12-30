package fr.leroideskiwis.fl.commands;

import java.lang.reflect.Method;

public class SimpleCommand {

    private Method m;
    private String name;
    private String description;
    private Object object;

    public SimpleCommand(Object o, Method m, String name, String description) {
        this.m = m;
        this.object = o;
        this.name = name;
        this.description = description;
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
}
