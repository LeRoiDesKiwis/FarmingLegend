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

        if(event.getMessage().getContentDisplay().startsWith(main.getPrefixeAsString())){

            new ThreadFactory(() -> {
                try {
                    main.getCore().commandUser(event.getMessage().getContentDisplay().replaceFirst(main.getPrefixeAsString(), ""), event);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).startSecureThread( "command-"+new Random().nextInt(9999));
        } else {

            main.getCore().getInscription().keySet().stream().filter(b -> b.getUser() == event.getAuthor()).forEach(u -> {

                if(event.getMessage().getContentDisplay().equalsIgnoreCase("cancel")){

                    event.getTextChannel().sendMessage("Vous avez annulé votre inscription !").queue();
                    main.getCore().getInscription().remove(u);
                    return;

                }

                int current = main.getCore().getInscription().get(u);

                switch (current){

                    case 0:

                        event.getTextChannel().sendMessage("Veuillez choisir un prénom.").queue();

                        u.firstName(event.getMessage().getContentDisplay());

                        break;

                    case 1:
                        event.getTextChannel().sendMessage("Veuillez choisir un nom").queue();
                        u.name(event.getMessage().getContentDisplay());
                        break;

                }

                main.getCore().getInscription().remove(u);
                main.getCore().getInscription().put(u, current+1);

            });


        }

    }
}
