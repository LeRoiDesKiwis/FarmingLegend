package fr.leroideskiwis.fl.commands;

import fr.leroideskiwis.fl.Main;
import fr.leroideskiwis.fl.command.CommandsBasics;
import fr.leroideskiwis.fl.game.Job;
import fr.leroideskiwis.fl.game.Player;
import fr.leroideskiwis.fl.reactionmenu.ReactionMenu;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandCore {

    private List<SimpleCommand> simpleCommands = new ArrayList<>();
    private Main main;

    public CommandCore(Main main) {
        this.main = main;
        registerCommand(new CommandsBasics());
    }

    public List<SimpleCommand> getSimpleCommands() {
        return simpleCommands;
    }

    public void commandUser(String cmd, MessageReceivedEvent e){

        if(main.getUtils().getPlayer(e.getAuthor()) == null){

            e.getTextChannel().sendMessage("Avant toute chose, créons un compte !");

            EmbedBuilder builder = new EmbedBuilder();
            builder.setTitle("Création d'un compte");

            for(Job job : Job.values()){

                builder.addField(job.toString().toLowerCase(), "cliquez sur la réaction "+job.getEmote(), false);

            }

            e.getTextChannel().sendMessage(builder.build()).queue(msg -> {

               ReactionMenu reactionMenu = new ReactionMenu() {
                   @Override
                   public void onReaction(ReactionMenu menu, MessageReaction.ReactionEmote clicked, Message msg, Member m, TextChannel channel) {

                       Job job = main.getUtils().getJobByEmote(clicked.getName());

                       if(job == null){

                           e.getTextChannel().sendMessage(main.getUtils().embedError("Ce job n'existe pas !")).queue();
                           return;
                       }

                       main.getPlayers().add(new Player(job, e.getAuthor()));

                       msg.delete().queue();

                       channel.sendMessage("Vous avez maintenant le job "+job.toString().toLowerCase()).queue();

                   }
               };

               for(Job job : Job.values()){

                   reactionMenu.addReaction(job.getEmote());

               }

               reactionMenu.build(msg, main);

               main.getReactionCore().addMenu(reactionMenu);

            });

            return;
        }

        List<SimpleCommand> available = new ArrayList<>();

        for(SimpleCommand simpleCommand : simpleCommands){

            if(simpleCommand.getName().startsWith(cmd.split(" ")[0])) available.add(simpleCommand);

        }

        if(available.size() == 0) return;
        if(available.size() > 1){

            StringBuilder builder = new StringBuilder();
            builder.append("Available commands : ");

            for(SimpleCommand command : available){

                builder.append(command.getName());
                if(!command.equals(available.get(available.size()-1))) builder.append(", ");

            }

            builder.append(".");

        } else {

            execute(cmd, available.get(0), e);

        }

    }



    public String[] getArgs(String cmd){

        String[] split = cmd.split(" ");
        String[] args = new String[split.length-1];

        for(int i = 0; i < args.length; i++){

            args[i] = split[i+1];

        }

        return args;
    }

    private void registerCommand(Object object){

        for(Method m : object.getClass().getDeclaredMethods()){

            if(m.isAnnotationPresent(Command.class)){

                Command cmd = m.getAnnotation(Command.class);

                simpleCommands.add(new SimpleCommand(object, m, cmd.name(), cmd.description()));

            }

        }

    }

    private void execute(String cmd, SimpleCommand simpleCommand, MessageReceivedEvent e) {

        Parameter[] params = simpleCommand.getMethod().getParameters();
        Object[] objects = new Object[params.length];

        for(int i = 0; i < params.length; i++){

            if(params[i].getType() == Guild.class) objects[i] = e.getGuild();
            else if(params[i].getType() == TextChannel.class) objects[i] = e.getTextChannel();
            else if(params[i].getType() == Member.class) objects[i] = e.getMember();
            else if(params[i].getType() == User.class) objects[i] = e.getAuthor();
            else if(params[i].getType() == String[].class) objects[i] = getArgs(cmd);
            else if(params[i].getType() == Message.class) objects[i] = e.getMessage();
            else if(params[i].getType() == JDA.class) objects[i] = main.getJda();
            else if(params[i].getType() == Main.class) objects[i] = main;
            else if(params[i].getType() == getClass()) objects[i] = this;
            else if(params[i].getType() == MessageReceivedEvent.class) objects[i] = e;

        }

        Thread thread = new Thread(() -> {

            try{

                simpleCommand.getMethod().invoke(simpleCommand.getObject(), objects);

            }catch(Exception ex){
                ex.printStackTrace();
            }

        });
        thread.setDaemon(true);
        thread.start();

    }

}
