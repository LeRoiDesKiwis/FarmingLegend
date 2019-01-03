package fr.leroideskiwis.fl.listeners;

import fr.leroideskiwis.fl.Main;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.Random;

public class EventCommand extends ListenerAdapter {

    private Main main;

    public EventCommand(Main main) {
        this.main = main;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        if(event.getMessage().getContentDisplay().startsWith(main.getPrefixeAsString())){

            main.getUtils().startSecureThread(() ->  main.getCore().commandUser(event.getMessage().getContentDisplay().replaceFirst(main.getPrefixeAsString(), ""), event)
            , "command-"+new Random().nextInt(9999));
        }

    }
}
