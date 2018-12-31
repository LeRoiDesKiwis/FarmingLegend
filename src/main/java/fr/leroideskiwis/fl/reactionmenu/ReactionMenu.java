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

    public void addReaction(String reac){

        reactions.add(reac);

    }

    public void close(){

        target.delete().queue();
        core.deleteMenu(this);

    }

    public void build(Message target, Message msg){
        core.addMenu(this);
        this.msg = msg;
        this.target = target;
        this.member = msg.getMember();
        this.channel = msg.getTextChannel();

        for(String s : reactions){

            target.addReaction(s).queue();

        }

    }

}
