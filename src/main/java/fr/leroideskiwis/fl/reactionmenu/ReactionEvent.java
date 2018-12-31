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

        if(event.getUser().equals(core.getMain().getJda().getSelfUser())) return;

        for(ReactionMenu menu : core.getMenus()){

            if(menu.member.getUser().getId().equals(event.getMember().getUser().getId()) && menu.target.getId().equals(event.getMessageId())){

                menu.onReaction(event.getReactionEmote());

            }

        }

    }
}
