package fr.leroideskiwis.fl.commands;

import fr.leroideskiwis.fl.Main;
import fr.leroideskiwis.fl.command.CommandsAdmin;
import fr.leroideskiwis.fl.command.CommandsBasics;
import fr.leroideskiwis.fl.command.CommandsFarm;
import fr.leroideskiwis.fl.game.Job;
import fr.leroideskiwis.fl.game.Player;
import fr.leroideskiwis.fl.reactionmenu.ReactionMenu;
import fr.leroideskiwis.fl.utils.MessageHandler;
import fr.leroideskiwis.fl.utils.Utils;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

public class CommandCore {

    private List<SimpleCommand> simpleCommands = new ArrayList<>();
    private Main main;

    public CommandCore(Main main) {
        this.main = main;
        registerCommand(new CommandsBasics());
        registerCommand(new CommandsFarm(main));
        registerCommand(new CommandsAdmin());
    }

    public List<SimpleCommand> getSimpleCommands() {
        return simpleCommands;
    }

    public boolean checkJob(Player p, Job[] need){

        return need[0].getEmote() == null || (p != null && main.getUtils().contains(need, p.getJob()));

    }

    public void createAccount(TextChannel channel, User u, Message message){
        channel.sendMessage("Avant toute chose, créons un compte !");

        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("Création d'un compte");

        for(Job job : Job.values()){

            if(job.getEmote() == null) continue;

            builder.addField(job.getEmote()+" "+main.getUtils().firstMaj(job.toString().toLowerCase()), "Votre but principal est de "+job.getFinalQuest(), false);

        }

        channel.sendMessage(builder.build()).queue(msg -> {

            ReactionMenu reactionMenu = new ReactionMenu(main.getReactionCore()) {
                @Override
                public void onReaction(MessageReaction.ReactionEmote clicked) {

                    if(clicked.getName().equals("❌")){
                        channel.sendMessage(new MessageHandler().embedInfo("Vous avez annuler votre inscription !")).queue();
                        close();

                        return;
                    }

                    Job job = main.getUtils().getJobByEmote(clicked.getName());

                    if(job == null){

                        channel.sendMessage(main.getUtils().embedError("Ce job n'existe pas !")).queue();
                        return;
                    }

                    main.getPlayers().add(new Player(job, u));
                    close();

                    channel.sendMessage(member.getAsMention()+", Vous avez maintenant le métier "+job.toString().toLowerCase()+".\nVotre but principal est désormais : **"+job.getFinalQuest()+"**").queue();

                }
            };

            for(Job job : Job.values()){
                if(job.getEmote() == null) continue;

                reactionMenu.addReaction(job.getEmote());

            }

            reactionMenu.addReaction("❌").build(msg, message);

        });

    }

    public void commandUser(String cmd, MessageReceivedEvent e){

        Player p = main.getUtils().getPlayer(e.getAuthor());

        List<SimpleCommand> available = new ArrayList<>();

        for(SimpleCommand simpleCommand : simpleCommands){

            if(checkJob(p, simpleCommand.getJob()) && (!simpleCommand.needOp() || main.checkDev(e.getAuthor())) && simpleCommand.getName().startsWith(cmd.split(" ")[0])) available.add(simpleCommand);

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

            e.getTextChannel().sendMessage(builder.toString()).queue();

        } else {

            if(p == null){

                e.getTextChannel().sendMessage("Rappel : vous n'avez pas de compte. Créez un compte en faisant "+main.getPrefixeAsString()+"inscription").queue();

            }

            execute(cmd, available.get(0), e, p);

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

                simpleCommands.add(new SimpleCommand(object, m, cmd.name(), cmd.description(), cmd.job(), cmd.op()));

            }

        }

    }

    private void execute(String cmd, SimpleCommand simpleCommand, MessageReceivedEvent e, Player player) {

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
            else if(params[i].getType() == Player.class) {

                if(player == null){

                    e.getTextChannel().sendMessage(main.getUtils().embedError("Cette commande n'hésite un compte... Nous allons vous en créer un :D")).queue();

                    createAccount(e.getTextChannel(), e.getAuthor(), e.getMessage());
                    return;
                }
                objects[i] = player;
            }
            else if(params[i].getType() == Utils.class) objects[i] = main.getUtils();

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

        new Thread(() -> {

            while (thread.isAlive()) {
            }

            if(player != null) player.levelUp(e.getTextChannel());

        }).start();

    }

}
