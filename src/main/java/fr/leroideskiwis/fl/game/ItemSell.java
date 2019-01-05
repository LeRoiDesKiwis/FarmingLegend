package fr.leroideskiwis.fl.game;

import fr.leroideskiwis.fl.Main;
import fr.leroideskiwis.fl.utils.Utils;
import net.dv8tion.jda.core.EmbedBuilder;

import java.awt.*;

public class ItemSell {

    private Item item;
    private Player seller;
    private Main main;
    private String id;
    private int price;

    public ItemSell(Main main, Item item, Player seller, int price) {
        this.item = item;
        this.seller = seller;
        this.main = main;
        this.price = price;

    }

    public ItemSell build(){

        do {
            this.id = new Utils(main).generateStringWithRandomChars();
        }while(main.getSells().stream().map(i -> i.getId()).noneMatch(id -> this.id.equals(id)));

        main.getJda().getGuilds().stream()
                .filter(g -> main.getShops().containsKey(g))
                .map(g -> g.getTextChannelById(main.getShops().get(g).getId()))
                .forEach(tx -> tx.sendMessage(new EmbedBuilder()
                        .setColor(Color.ORANGE)
                        .setAuthor("Vente de "+item.getCount()+" "+item.getMaterial().toString().toLowerCase()+" par"+seller.getUser().getName()+" à "+price+"€", null, seller.getUser().getAvatarUrl())
                        .setDescription("Pour l'acheter, faites "+main.getPrefixeAsString()+"buy "+id)
                        .setFooter("id : "+id, null)
                        .build()).queue());

        return this;
    }

    public String getId() {
        return id;
    }

    public Item getItem() {
        return item;
    }

    public Player getSeller() {
        return seller;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {

        this.price = price;
    }
}
