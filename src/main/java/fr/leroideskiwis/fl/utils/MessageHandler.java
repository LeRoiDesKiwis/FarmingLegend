package fr.leroideskiwis.fl.utils;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;

import java.awt.*;

public class MessageHandler {

    public MessageEmbed embedError(String s){
        return new EmbedBuilder().setColor(Color.RED).setDescription(s).setTitle("Erreur !").build();
    }

    public MessageEmbed embedInfo(String s){

        return new EmbedBuilder().setColor(Color.BLUE).setTitle("Information").setDescription(s).build();

    }

}
