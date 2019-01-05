package fr.leroideskiwis.fl.listeners;

import fr.leroideskiwis.fl.Main;
import fr.leroideskiwis.fl.game.Player;
import fr.leroideskiwis.fl.utils.ThreadFactory;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class EventCommand extends ListenerAdapter {

    private Main main;
    private Map<User, Long> cooldowns = new HashMap<>();

    public EventCommand(Main main) {
        this.main = main;
    }

    //TODO délai pour le ratelimit (genre 2sec/commande) et ne pas envoyer de message pour dire qu'il lui reste tant de seconde sinon yaura toujours le probleme du ratelimit

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        if(event.getAuthor() == main.getJda().getSelfUser()) return;

        if(event.getMessage().getContentDisplay().startsWith(main.getPrefixeAsString())){

            if(cooldowns.containsKey(event.getAuthor())){

                int delay = 3;

                int remain = (int)(System.currentTimeMillis()/1000-cooldowns.get(event.getAuthor())/1000);

                if(remain < delay) return;
                cooldowns.remove(event.getAuthor());

            }
            cooldowns.put(event.getAuthor(), System.currentTimeMillis());


            new ThreadFactory(() -> {
                try {
                    main.getCore().commandUser(event.getMessage().getContentDisplay().replaceFirst(main.getPrefixeAsString(), ""), event);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).startSecureThread( "command-"+new Random().nextInt(9999));
        }

    }
}
