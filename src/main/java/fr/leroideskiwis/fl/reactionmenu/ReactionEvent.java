package fr.leroideskiwis.fl.reactionmenu;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class ReactionEvent extends ListenerAdapter {

    private ReactionCore core;

    public ReactionEvent(ReactionCore core) {
        this.core = core;

    }


    @Override
    public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event) {

        for(ReactionMenu menu : core.getMenus()){

            if(menu.member.equals(event.getMember())){

                menu.onReaction(/*to be completed*/null, menu.msg, menu.member, menu.channel);

            }

        }

    }
}
