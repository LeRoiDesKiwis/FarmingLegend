package fr.leroideskiwis.fl.reactionmenu;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
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

        for(int i =0; i < core.getMenus().size(); i++){
            ReactionMenu menu = core.getMenus().get(i);

            if(menu.isDeleteReaction()) event.getReaction().removeReaction(event.getUser()).queue();

            if(menu.member.getUser().getId().equals(event.getMember().getUser().getId()) && menu.target.getId().equals(event.getMessageId())){

                menu.onReaction(event.getReactionEmote());

            }

        }

    }
}
