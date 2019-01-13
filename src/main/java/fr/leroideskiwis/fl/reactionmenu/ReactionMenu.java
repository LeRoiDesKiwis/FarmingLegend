package fr.leroideskiwis.fl.reactionmenu;

import net.dv8tion.jda.core.entities.*;

import java.util.ArrayList;
import java.util.List;

public abstract class ReactionMenu {

    private ReactionCore core;
    private int timeout;
    public Message userMessage, botMessage;
    public Member member;
    public TextChannel channel;
    private List<String> reactions = new ArrayList<>();
    private boolean deleteReaction;

    public boolean isDeleteReaction(){
        return deleteReaction;
    }

    public abstract void onReaction(MessageReaction.ReactionEmote clicked);

    public void setDeleteReaction(boolean bool){

        this.deleteReaction = bool;

    }

    public ReactionMenu(ReactionCore core, int timeout) {
        this.timeout = timeout;
        this.deleteReaction = true;
        this.core = core;

    }

    public ReactionMenu addReaction(String reac){

        reactions.add(reac);
        return this;

    }

    public void close(){

        botMessage.delete().queue();
        core.deleteMenu(this);

    }

    public ReactionMenu build(Message botMessage, Message userMessage){
        core.addMenu(this);
        this.userMessage = userMessage;
        this.botMessage = botMessage;
        this.member = userMessage.getMember();
        this.channel = userMessage.getTextChannel();

        for(String s : reactions){

            botMessage.addReaction(s).queue();

        }

        new Thread(() -> {

            try {
                Thread.sleep(timeout);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(core.getMenus().contains(this)) {

                close();

                channel.sendMessage(core.getMain().getUtils().embedError("Vous avez attendu trop longtemps !")).queue();
            }

        }, "reaction-"+userMessage.getId()).start();

        return this;

    }

}
