package fr.leroideskiwis.fl.commands;

import fr.leroideskiwis.fl.RoleCommand;
import fr.leroideskiwis.fl.game.Job;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)

public @interface Command {

    String name();
    String description() default "Aucune description.";
    Job[] job() default Job.ALL;
    String syntaxe() default "Une erreur s'est produite !";

    RoleCommand role() default RoleCommand.ALL;
}
