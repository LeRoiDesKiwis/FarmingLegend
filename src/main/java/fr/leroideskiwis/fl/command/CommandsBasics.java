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
import fr.leroideskiwis.fl.utils.MessageHandler;
import fr.leroideskiwis.fl.utils.Utils;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.*;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CommandsBasics {

    @Command(name="deleteaccount")
    public void delete(Player p, Main main, TextChannel channel, Message message){

        EmbedBuilder builder = new EmbedBuilder().setColor(Color.RED).setTitle("Confirmation");
        builder.setDescription("Êtes-vous **SÛR** de vouloir supprimer votre compte ? Votre argent, votre inventaire, vos niveaux, **TOUT** sera perdu et sera irrécupérable !");
        channel.sendMessage(builder.build()).queue(msg -> new ReactionMenu(main.getReactionCore(), 10000) {
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
        }.addReaction("❌").addReaction("✅").build(msg, message));

    }

    @Command(name="help", description="affiche la liste des commandes")
    public void help(Guild g, Main main, CommandCore core, Member m, User user, TextChannel channel) throws IOException {

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
    public void onSuggest(User u, Main main, TextChannel textChannel, String[] args) throws IOException {

        for (User dev : main.getDevs()) {

            dev.openPrivateChannel().queue(pv -> {

                StringBuilder str = new StringBuilder();

                for (String arg : args) {

                    str.append(arg).append(" ");

                }

                pv.sendMessage(new EmbedBuilder()
                        .setColor(Color.ORANGE)
                        .setAuthor("Nouvelle suggestion de "+u.getName(), null, u.getAvatarUrl())
                        .setDescription(str.toString())
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

    @Command(name="info",syntaxe = "!s!info <material>")
    public void info(TextChannel channel, String[] args){

        Material mat = Material.valueOf(args[0].toUpperCase());

        EmbedBuilder builder = new EmbedBuilder().setColor(Color.ORANGE)
                .setTitle("Infos sur le "+mat.toString().toLowerCase())
                .addField("Prix conseillé : ", mat.getPrice()+"", false)
                .addField("Prix minimum : ", (mat.getPrice()*0.5 <= 0 ? 0 : mat.getPrice()*0.5)+"", false)
                .addField("Prix maximum : ", mat.getPrice()*2+"", false)
                .addField("Description : ", "coming soon...", false);

        channel.sendMessage(builder.build()).queue();

    }

    @Command(name="buy")
    public void buy(Message msg, Main main, String[] args, Player player, TextChannel channel){

        String id = args[0];

        if(main.getSells().stream().noneMatch(item -> item.getId().equals(id))){
            channel.sendMessage(new MessageHandler().embedError("Aucune vente avec l'id "+id+".")).queue();

            return;
        }

        main.getSells().stream().filter(itemSell -> itemSell.getId().equals(id)).forEach(itemSell -> {

            if(player.getMoney() >= itemSell.getPrice()){

                EmbedBuilder builder = new EmbedBuilder()
                        .setColor(Color.ORANGE)
                        .setAuthor("Demande de confirmation", null, player.getUser().getAvatarUrl())
                        .addField("Vendeur ", itemSell.getSeller().getUser().getName(), false)
                        .addField("Type", itemSell.getItem().getMaterial().toString().toLowerCase(), false)
                        .addField("Prix", itemSell.getPrice()+"", false)
                        .addField("Id ", itemSell.getId(), false);

                channel.sendMessage(builder.build()).queue(m -> new ReactionMenu(main.getReactionCore(), 30000) {
                    @Override
                    public void onReaction(MessageReaction.ReactionEmote clicked) {

                        if(clicked.getName().equals("❌")) channel.sendMessage(builder.setColor(Color.RED).setAuthor("Vous avez annulé votre achat !", null, player.getUser().getAvatarUrl()).build()).queue();

                        else if(clicked.getName().equals("✅")){
                            channel.sendMessage(builder.setColor(Color.GREEN).setAuthor("Vous avez acheté un produit !", null, player.getUser().getAvatarUrl()).build()).queue();
                            player.removeMoney(itemSell.getPrice());
                            itemSell.getSeller().addMoney(itemSell.getPrice());

                            player.getInventory().addItem(itemSell.getItem());
                            main.getSells().remove(itemSell);

                            itemSell.getMsgShop().stream().filter(tx -> tx.getGuild().getSelfMember().hasPermission(tx.getTextChannel(), Permission.MESSAGE_WRITE)).forEach(msg -> {

                                EmbedBuilder b = new EmbedBuilder().setColor(Color.GREEN).setAuthor("Produit vendu à "+player.getUser().getName(), null, player.getUser().getAvatarUrl()).addField("Vendeur", itemSell.getSeller().getUser().getName(), false).addField("Type du produit :", itemSell.getItem().getMaterial().toString().toLowerCase(), false).addField("Prix", itemSell.getPrice()+"", false);
                                itemSell.getSeller().getUser().openPrivateChannel().queue(pv -> pv.sendMessage(b.build()).queue());

                                msg.editMessage(b.build()).queue();

                            });

                        }

                        close();

                    }
                }.addReaction("❌").addReaction("✅").build(m, msg));

            } else {
                channel.sendMessage(new MessageHandler().embedError("Vous n'avez pas assez d'argent !")).queue();
            }


        });



    }

    @Command(name="sell", syntaxe ="!s!sell <material> <count>")
    public void shop(Message msg, ReactionCore core, String[] args, Player p, Main main, Guild g, TextChannel channel) {

        Material material = Material.valueOf(args[0].toUpperCase());
        int count = Integer.parseInt(args[1]);

        Item item = main.getUtils().stackAnItem(material, count, p.getInventory());

        if(item == null) {

            channel.sendMessage(new MessageHandler().embedError("Vous n'avez pas assez de "+ material.toString().toLowerCase())).queue();
            return;

        }

        int priceReco = material.getPrice()*count;

        ItemSell sell = new ItemSell(main, item, p, priceReco);

        EmbedBuilder builder = new EmbedBuilder().setColor(Color.CYAN)
                .setTitle("Mettez un prix")
                .setDescription("Prix : "+ priceReco+"€")
                ;
        channel.sendMessage(builder.build()).queue(m -> new ReactionMenu(core, 30000) {
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

                if(newPrice < 0 || newPrice < priceReco*0.5 || newPrice > priceReco*2){

                    botMessage.editMessage(new EmbedBuilder(builder).setColor(Color.RED).setTitle("Vous n'avez pas renseigné un prix correct ! Faites "+main.getPrefixeAsString()+"info "+ material.toString().toLowerCase()+" pour en savoir plus !").build()).queue(msg -> {
                        try {
                            Thread.sleep(2000);
                            msg.editMessage(builder.build()).queue();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    });

                } else {
                    sell.setPrice(newPrice);

                    botMessage.editMessage(builder.setColor(Color.CYAN).setDescription("Prix : " + newPrice + "€").build()).queue();
                }
            }

        }.addReaction("➖").addReaction("➕").addReaction("\uD83C\uDD97").build(m, msg));
    }

    @Command(name="give",syntaxe = "!s!give <mention> <matériel> <nombre>")
    public void onGive(String[] args, TextChannel channel, Utils u, Main main, Player p, Message msg){

        Player target = u.getPlayer(msg.getMentionedUsers().get(0));

        if(target == null){

            channel.sendMessage(new MessageHandler().embedError("L'utilisateur "+msg.getMentionedUsers().get(0).getName()+" n'as pas de compte.")).queue();


        } else {

            Material mat = Material.valueOf(args[msg.getMentionedUsers().get(0).getName().split(" ").length].toUpperCase());

            int count = Integer.parseInt(args[msg.getMentionedUsers().get(0).getName().split(" ").length+1]);

            Item item = u.stackAnItem(mat, count, p.getInventory());

            if(item == null){
                channel.sendMessage(new MessageHandler().embedError("Vous n'avez pas assez de "+mat.toString().toLowerCase())).queue();
                return;
            }

            EmbedBuilder builder = new EmbedBuilder().setColor(Color.ORANGE)
                    .setAuthor("Demande de confirmation", null, p.getUser().getAvatarUrl())
                    .addField("Receveur : ",target.getUser().getName(), false)
                    .addField("Type : ", mat.toString().toLowerCase(),false )
                    .addField("Nombre : ", count+"", false)
                    .addField("Valeur de tout ces items :", count*mat.getPrice()+"", false);

            channel.sendMessage(builder.build()).queue(m -> {

                new ReactionMenu(main.getReactionCore(), 30000) {
                    @Override
                    public void onReaction(MessageReaction.ReactionEmote clicked) {

                        if(clicked.getName().equals("❌")){

                            channel.sendMessage(builder.setColor(Color.RED).setAuthor("Vous avez annulé votre don").build()).queue();

                        } else {

                            p.getInventory().removeItem(mat, count);
                            target.getInventory().addItem(item);
                            channel.sendMessage(builder.setColor(Color.GREEN).setAuthor("Don confirmée !").build()).queue();

                        }

                        close();

                    }
                }.addReaction("✅").addReaction("❌").build(m, msg);
            });

        }

    }

}
