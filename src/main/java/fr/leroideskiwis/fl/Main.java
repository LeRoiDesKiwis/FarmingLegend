package fr.leroideskiwis.fl;

import fr.leroideskiwis.fl.commands.CommandCore;
import fr.leroideskiwis.fl.game.Player;
import fr.leroideskiwis.fl.listeners.EventCommand;
import fr.leroideskiwis.fl.reactionmenu.ReactionCore;
import fr.leroideskiwis.fl.reactionmenu.ReactionMenu;
import fr.leroideskiwis.fl.utils.Utils;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;

import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Main implements Runnable{

    private JDA jda;
    private CommandCore core;
    private char prefixe = '&';
    private List<Player> players = new ArrayList<>();
    private ReactionCore reactionCore;

    public ReactionCore getReactionCore() {
        return reactionCore;
    }

    public Utils getUtils(){

        return new Utils(this);

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
        new Thread(() -> {

            jda.getPresence().setGame(Game.playing("in "+jda.getGuilds().size()+" servers"));

        }, "thread-guilds").start();

        jda.addEventListener(new EventCommand(this));

    }

    public static void main(String... args){

        try{

            new Thread(new Main(), "main").start();

        }catch(Throwable e){
            e.printStackTrace();
        }

    }

    private String readToken(){
        try {

            FileReader stream = new FileReader(new File("./token.txt"));

            int c = stream.read();

            StringBuilder builder = new StringBuilder();

            while (c != -1) {

                builder.append((char)c);
                c = stream.read();

            }

            return builder.toString();
        }catch(Throwable t){
            t.printStackTrace();
        }

        return null;

    }

    @Override
    public void run() {

    }
}
