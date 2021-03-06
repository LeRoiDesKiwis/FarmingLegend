package fr.leroideskiwis.fl;

import fr.leroideskiwis.fl.commands.CommandCore;
import fr.leroideskiwis.fl.game.Player;
import fr.leroideskiwis.fl.game.ItemSell;
import fr.leroideskiwis.fl.listeners.EventCommand;
import fr.leroideskiwis.fl.reactionmenu.ReactionCore;
import fr.leroideskiwis.fl.reactionmenu.ReactionMenu;
import fr.leroideskiwis.fl.utils.Utils;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main implements Runnable{

    private final JDA jda;
    private final CommandCore core;
    private final char prefixe = '!';
    private final List<Player> players = new ArrayList<>();
    private final ReactionCore reactionCore;
    private final Map<Guild, TextChannel> shops = new HashMap<>();
    private final List<ItemSell> sells = new ArrayList<>();

    public List<ItemSell> getSells() {
        return sells;
    }

    public Map<Guild, TextChannel> getShops() {
        return shops;
    }

    public ReactionCore getReactionCore() {
        return reactionCore;
    }

    public Utils getUtils(){

        return new Utils(this);

    }

    public List<User> getDevs() throws IOException {

            File file = new File("./admins.txt");

            if (!file.exists()) file.createNewFile();


                BufferedReader reader = new BufferedReader(new FileReader(file));
                List<User> toReturn = new ArrayList<>();
                String line = reader.readLine();

                while (line != null) {

                    if(jda.getUserById(line) != null)toReturn.add(jda.getUserById(line));

                    line = reader.readLine();

                }
                reader.close();

                return toReturn;

    }

    public boolean checkDev(User user) throws IOException {

        return getDevs().contains(user);

    }

    public List<Player> getPlayers() {

        return players;
    }

    public char getPrefixe(){
        return prefixe;
    }


    public String getPrefixeAsString(){
        return String.valueOf(prefixe);
    }

    public JDA getJda() {
        return jda;
    }

    public CommandCore getCore() {
        return core;
    }

    public void registerMenu(ReactionMenu menu){

        reactionCore.addMenu(menu);

    }

    public Main() throws Throwable{

        jda = new JDABuilder(AccountType.BOT).setToken(readToken()).build();
        jda.awaitReady();
        reactionCore = new ReactionCore(this);
        core = new CommandCore(this);

        jda.addEventListener(new EventCommand(this));

    }

    public static void main(String... args){

        try{

            new Thread(new Main(), "main").start();

        }catch(Throwable e){
            e.printStackTrace();
        }

    }

    private String readToken() throws IOException{

            File file = new File("./token.txt");

            if(!file.exists()) file.createNewFile();

            FileReader stream = new FileReader(file);



            int c = stream.read();

            StringBuilder builder = new StringBuilder();

            while (c != -1) {

                builder.append((char)c);
                c = stream.read();

            }

            return builder.toString();

    }

    @Override
    public void run() {

    }
}
