package fr.leroideskiwis.fl.utils;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.TextChannel;

import java.awt.*;

public class MessageHandler {

    EmbedBuilder builder = new EmbedBuilder();

    public MessageEmbed embedError(String s){
        return builder.setColor(Color.RED).setDescription(s).setTitle("Erreur !").build();
    }

    public MessageEmbed embedInfo(String s){

        return builder.setColor(Color.BLUE).setTitle("Information").setDescription(s).build();

    }

    public void send(TextChannel channel, String s){
        channel.sendMessage(builder.setDescription(s).build()).queue();
    }

    public MessageHandler(Color color){
        this(color, null);
    }

    public MessageHandler(Color color, String title) {

        this();
        this.builder.setColor(color).setTitle(title);

    }

    public MessageHandler(){

        this.builder = new EmbedBuilder();

    }
}
