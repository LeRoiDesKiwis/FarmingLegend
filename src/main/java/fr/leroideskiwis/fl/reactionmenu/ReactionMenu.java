package fr.leroideskiwis.fl.reactionmenu;

import fr.leroideskiwis.fl.Main;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.*;

import java.util.ArrayList;
import java.util.List;

public abstract class ReactionMenu {

    public Message msg;
    public Member member;
    public TextChannel channel;
    private List<String> reactions = new ArrayList<>();

    public abstract void onReaction(ReactionMenu menu, MessageReaction.ReactionEmote clicked, Message msg, Member m, TextChannel channel);

    public void addReaction(String reac){

        reactions.add(reac);

    }

    public void build(Message msg, Main main){
        this.msg = msg;
        this.member = msg.getMember();
        this.channel = msg.getTextChannel();

        for(String s : reactions){

            msg.addReaction(s).queue();

        }

    }

}
