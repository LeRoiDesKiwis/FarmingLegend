package fr.leroideskiwis.fl.command;

import fr.leroideskiwis.fl.Main;
import fr.leroideskiwis.fl.commands.Command;
import fr.leroideskiwis.fl.commands.CommandCore;
import fr.leroideskiwis.fl.commands.SimpleCommand;
import fr.leroideskiwis.fl.game.Item;
import fr.leroideskiwis.fl.game.ItemSell;
import fr.leroideskiwis.fl.game.Material;
import fr.leroideskiwis.fl.game.Player;
import fr.leroideskiwis.fl.reactionmenu.ReactionCore;
import fr.leroideskiwis.fl.reactionmenu.ReactionMenu;
import fr.leroideskiwis.fl.utils.Utils;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CommandsBasics {

    @Command(name="deleteaccount")
    public void delete(Player p, Main main, TextChannel channel, Message message){

        EmbedBuilder builder = new EmbedBuilder().setColor(Color.RED).setTitle("Confirmation");
        builder.setDescription("Êtes-vous **SÛR** de vouloir supprimer votre compte ? Votre argent, votre inventaire, vos niveaux, **TOUT** sera perdu et sera irrécupérable !");
        channel.sendMessage(builder.build()).queue(msg -> {

            new ReactionMenu(main.getReactionCore(), 10000) {
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
    public void help(Guild g, Main main, CommandCore core, Member m, User user, TextChannel channel){

        EmbedBuilder builder = new EmbedBuilder()
                .setColor(Color.GRAY);


        int count = 0;

        for(SimpleCommand cmd : core.getSimpleCommands()){

            if(core.checkPerms(g, m.getUser(), cmd.getNeededRole()) && core.checkJob(main.getUtils().getPlayer(user), cmd.getJob())) {
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

        builder.addField("Job :", p.getJob().toString().toLowerCase()+" "+p.getJob().getEmote(), true);
        builder.addField(":heart: Vie :", p.getHealth()+"/"+p.getMaxHealth(), true);
        builder.addField(":meat_on_bone: faim :",new Utils().floatInt(p.getFood())+"/10",true);
        builder.addField(":zap: énergie :", p.getEnergy()+"/"+p.getMaxEnergy(), true);
        builder.addField(":moneybag: Argent :", p.getMoney()+"€", true);
        builder.addField(":trophy: Niveau : ", p.getLevel()+"", true);
        builder.addField("xp : ", p.getXp()+"/"+p.getNeededXp(), true);

        channel.sendMessage(builder.build()).queue();

    }

    @Command(name="github",description="Obtenir le lien du github")
    public void github(TextChannel channel){

        channel.sendMessage("Vous voulez contribuer au développement du bot ou vous êtes juste curieux de savoir comment il est codé ? Alors ce lien est pour vous : https://github.com/LeRoiDesKiwis/FarmingLegend/").queue();

    }

    @Command(name="sell", syntaxe ="!s!sell <material> <count>")
    public void shop(Message msg, ReactionCore core, String[] args, Player p, Main main, Guild g, TextChannel channel) throws Exception {

        Material material = Material.valueOf(args[0].toUpperCase());
        int count = Integer.parseInt(args[1]);

        List<Item> unstack = new ArrayList<>(p.getInventory().getItems());

        p.getInventory().unstack(unstack);

        Object[] objects = main.getUtils().stackAnItem(material, count, unstack);

        if(objects == null) throw new Exception();
        Item item = (Item)objects[1];

        p.getInventory().stack((List<Item>)objects[0]);

        p.getInventory().setItems((List<Item>)objects[0]);

        ItemSell sell = new ItemSell(main, item, p, 0);

        EmbedBuilder builder = new EmbedBuilder().setColor(Color.CYAN)
                .setTitle("Mettez un prix")
                .setDescription("Prix : 0€"); channel.sendMessage(builder.build()).queue(m -> {

            new ReactionMenu(core, 30000) {
                @Override
                public void onReaction(MessageReaction.ReactionEmote clicked) {
                    if(clicked.getName().equals("\uD83C\uDD97")){

                        channel.sendMessage("Vous avez vendu "+sell.getItem().getCount()+" "+sell.getItem().getMaterial().toString().toLowerCase()+" à "+sell.getPrice()+"€").queue();
                        main.getSells().add(sell.build());
                        close();
                        return;
                    }



                    int currentPrice = sell.getPrice();
                    int newPrice = clicked.getName().equals("➖") ? currentPrice-5 : currentPrice+5;
                    sell.setPrice(newPrice);

                    target.editMessage(builder.setDescription("Prix : "+newPrice+"€").build()).queue();

                }

            }.addReaction("➖").addReaction("➕").addReaction("\uD83C\uDD97").build(m, msg);

        });

        /*main.getSells().add(new ItemSell(main, item, p));

        channel.sendMessage("Vous avez vendu "+item.getCount()+" "+item.getMaterial().toString().toLowerCase()).queue();
        */
    }

}
