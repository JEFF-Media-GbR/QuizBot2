package com.jeff_media.quizbot;

import com.jeff_media.quizbot.data.Game;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Arrays;
import java.util.Locale;

public class MessageListener extends ListenerAdapter {

    private final QuizBot bot;
    private final String prefix;

    public MessageListener(QuizBot bot) {
        this.bot = bot;
        this.prefix = bot.getConfig().prefix();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot() || event.getMessage().isWebhookMessage()) {
            return;
        }
        Message message = event.getMessage();
        if(isCommand(message)) {
            handleCommand(message);
        } else {
            handleMessage(message);
        }
    }

    private void handleMessage(Message message) {
        if(message.getChannelType() != ChannelType.TEXT) return;
        TextChannel channel = message.getTextChannel();
        Game game = bot.getRunningGames().get(channel);
        if(game == null) {
            return;
        }
        System.out.println("Handling message in running game \"" + game.getName() + "\": " + message.getContentRaw());
        game.handleMessage(message);
    }

    private void handleCommand(Message message) {
        String[] args = message.getContentRaw().split(" ");
        String command;// = args[0].substring(prefix.length()).toLowerCase(Locale.ROOT);
        if(args.length == 1) {
            command = "help";
            args = new String[0];
        } else {
            command = args[1].toLowerCase(Locale.ROOT);
            args = Arrays.copyOfRange(args, 2, args.length);
        }


        System.out.println("Command: " + command + "\nArgs: " + Arrays.toString(args));
        bot.executeCommand(message, command, args);
    }

    private boolean isCommand(Message message) {
        return message.getContentRaw().toLowerCase(Locale.ROOT).startsWith(prefix.toLowerCase(Locale.ROOT) + " ")
                || message.getContentRaw().toLowerCase(Locale.ROOT).equals(prefix.toLowerCase(Locale.ROOT));
    }
}
