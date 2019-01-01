package fr.leroideskiwis.fl.command;

import fr.leroideskiwis.fl.Main;
import fr.leroideskiwis.fl.commands.Command;
import fr.leroideskiwis.fl.commands.CommandCore;
import fr.leroideskiwis.fl.commands.SimpleCommand;
import fr.leroideskiwis.fl.game.Item;
import fr.leroideskiwis.fl.game.Player;
import fr.leroideskiwis.fl.reactionmenu.ReactionMenu;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.*;

import java.awt.*;

public class CommandsBasics {

    @Command(name="deleteaccount")
    public void delete(Player p, Main main, TextChannel channel, Message message){

        EmbedBuilder builder = new EmbedBuilder().setColor(Color.RED).setTitle("Confirmation");
        builder.setDescription("Êtes-vous **SÛR** de vouloir supprimer votre compte ? Votre argent, votre inventaire, vos niveaux, **TOUT** sera perdu et sera irrécupérable !");
        channel.sendMessage(builder.build()).queue(msg -> {

            new ReactionMenu(main.getReactionCore()) {
                @Override
                public void onReaction(MessageReaction.ReactionEmote clicked) {

                    if(clicked.getName().equals("❌")){
                        channel.sendMessage("Heureux que vous ayez changer d'avis :D !").queue();
                    } else {

                        main.getPlayers().remove(p);

                        channel.sendMessage(new EmbedBuilder()
                                .setColor(Color.RED)
                                .setAuthor("Suppression du compte de "+member.getUser().getName(), null, member.getUser().getAvatarUrl())
                                .setDescription("Aïe ! Je déteste les adieux :'(")
                                .build()).queue();

                    }

                    close();
                }
            }.addReaction("❌").addReaction("✅").build(msg, message);

        });

    }

    @Command(name="help", description="affiche la liste des commandes")
    public void help(Main main, CommandCore core, Member m, User user, TextChannel channel){

        EmbedBuilder builder = new EmbedBuilder()
                .setColor(Color.GRAY);


        int count = 0;

        for(SimpleCommand cmd : core.getSimpleCommands()){

            if((!cmd.needOp() || main.checkDev(user)) && core.checkJob(main.getUtils().getPlayer(user), cmd.getJob())) {
                builder.addField(cmd.getName(), cmd.getDescription(), false);
                count++;
            }

        }

        builder.setTitle(count+" commandes avec le préfixe "+main.getPrefixeAsString());

        channel.sendMessage(builder.build()).queue();

    }

    @Command(name="suggest")
    public void onSuggest(User u, Main main, TextChannel textChannel, String[] args){

        for (User dev : main.getDevs()) {

            dev.openPrivateChannel().queue(pv -> {

                String str = "";

                for(int i = 0; i < args.length; i++){

                    str+= args[i]+" ";

                }

                pv.sendMessage(new EmbedBuilder()
                        .setColor(Color.ORANGE)
                        .setAuthor("Nouvelle suggestion de "+u.getName(), null, u.getAvatarUrl())
                        .setDescription(str)
                        .build()
                        ).queue();

            });

        }

        textChannel.sendMessage(u.getAsMention()+", votre suggestion à bien été envoyée !").queue();

    }

    @Command(name="inscription")
    public void inscript(Player p, Main main, TextChannel channel){

        channel.sendMessage(main.getUtils().embedError("Vous êtes déjà inscrit !")).queue();

    }

    @Command(name="inventory")
    public void inventory(Player p, TextChannel channel){

        EmbedBuilder builder = new EmbedBuilder().setColor(Color.ORANGE).setAuthor("Inventaire de "+p.getUser().getName(), null, p.getUser().getAvatarUrl());

        if(p.getInventory().getItems().isEmpty()) builder.setDescription("Vous n'avez rien dans votre inventaire !");
        else {
            for (Item item : p.getInventory().getItems()) {

                builder.addField(item.getMaterial().toString().toLowerCase(), item.getCount() + "", false);

            }
        }

        channel.sendMessage(builder.build()).queue();

    }

    @Command(name="profile")
    public void profile(Player p, TextChannel channel){

        EmbedBuilder builder = new EmbedBuilder().setColor(Color.ORANGE).setAuthor("Profile de "+p.getUser().getName(), null, p.getUser().getAvatarUrl());

        builder.addField("Job :", p.getJob().toString().toLowerCase()+" "+p.getJob().getEmote(), false);
        builder.addField(":heart: Vie :", p.getHealth()+"/"+p.getMaxHealth(), true);
        builder.addField(":moneybag: Argent :", p.getMoney()+"€", false);
        builder.addField(":trophy: Niveau : ", p.getLevel()+"", true);
        builder.addField("xp : ", p.getXp()+"/"+p.getNeededXp(), false);

        channel.sendMessage(builder.build()).queue();

    }

    @Command(name="github",description="Obtenir le lien du github")
    public void github(TextChannel channel){

        channel.sendMessage("Vous voulez contribuer au développement du bot ou vous êtes juste curieux de savoir comment il est codé ? Alors ce lien est pour vous : https://github.com/LeRoiDesKiwis/FarmingLegend/").queue();

    }

}
