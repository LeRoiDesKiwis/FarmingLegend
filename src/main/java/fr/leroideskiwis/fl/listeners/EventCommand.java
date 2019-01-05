package fr.leroideskiwis.fl.listeners;

import fr.leroideskiwis.fl.Main;
import fr.leroideskiwis.fl.game.Player;
import fr.leroideskiwis.fl.utils.ThreadFactory;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.io.IOException;
import java.util.Random;

public class EventCommand extends ListenerAdapter {

    private Main main;

    public EventCommand(Main main) {
        this.main = main;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        //TODO délai pour le ratelimit (genre 2sec/commande) et ne pas envoyer de message pour dire qu'il lui reste tant de seconde sinon yaura toujours le probleme du ratelimit
        //TODO autre idée : Mettre toutes les demandes de commande dans une liste, et les traiter une par une avec un délai entre chaque. (afficher la place dans la queue en message)
        //TODO en savoir plus sur les ratelimits (si c'est par guild, par bot, comment ça fonctionne etc)

        if(event.getMessage().getContentDisplay().startsWith(main.getPrefixeAsString())){

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
