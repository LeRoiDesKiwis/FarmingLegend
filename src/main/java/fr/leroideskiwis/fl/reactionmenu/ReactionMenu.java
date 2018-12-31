package fr.leroideskiwis.fl.reactionmenu;

import fr.leroideskiwis.fl.Main;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.*;

import java.util.ArrayList;
import java.util.List;

public abstract class ReactionMenu {

    private final ReactionCore core;
    public Message msg, target;
    public Member member;
    public TextChannel channel;
    private List<String> reactions = new ArrayList<>();

    public abstract void onReaction(MessageReaction.ReactionEmote clicked);

    public ReactionMenu(ReactionCore core) {

        this.core = core;

    }

    public ReactionMenu addReaction(String reac){

        reactions.add(reac);
        return this;

    }


    public void close(){

        target.delete().queue();
        core.deleteMenu(this);

    }

    public ReactionMenu build(Message target, Message msg){
        core.addMenu(this);
        this.msg = msg;
        this.target = target;
        this.member = msg.getMember();
        this.channel = msg.getTextChannel();

        for(String s : reactions){

            target.addReaction(s).queue();

        }

        new Thread(() -> {

            try {
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(core.getMenus().contains(this)) {

                close();

                channel.sendMessage(core.getMain().getUtils().embedError("Vous avez attendu trop longtemps !")).queue();
            }

        }, "reaction-"+msg.getId()).start();

        return this;

    }

}
