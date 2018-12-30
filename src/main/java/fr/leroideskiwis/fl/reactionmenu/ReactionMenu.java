package fr.leroideskiwis.fl.reactionmenu;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.*;

import java.util.ArrayList;
import java.util.List;

public abstract class ReactionMenu {

    public Message msg;
    public Member member;
    public TextChannel channel;
    private List<String> reactions = new ArrayList<>();

    public abstract void onReaction(String clicked, Message msg, Member m, TextChannel channel);

    public void addReaction(String reac){

        reactions.add(reac);

    }


    public void build(Message msg){
        this.msg = msg;
        this.member = msg.getMember();
        this.channel = msg.getTextChannel();

        for(String s : reactions){

            msg.addReaction(s);

        }

    }

}
