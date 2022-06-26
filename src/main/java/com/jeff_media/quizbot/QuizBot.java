package com.jeff_media.quizbot;

import com.jeff_media.quizbot.command.CommandExecutor;
import com.jeff_media.quizbot.command.CommandResult;
import com.jeff_media.quizbot.command.commands.QuizCommand;
import com.jeff_media.quizbot.config.MainConfig;
import com.jeff_media.quizbot.data.Game;
import lombok.Getter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;
import java.io.FileNotFoundException;
import java.util.*;

public class QuizBot {

    @Getter private final MainConfig config;
    private final JDA jda;
    @Getter private final Map<TextChannel,Game> runningGames = new HashMap<>();
    private final Map<String, CommandExecutor> commandMap = new HashMap<>();

    public QuizBot() {
        try {
            config = new MainConfig();
        } catch (FileNotFoundException exception) {
            throw new RuntimeException(exception);
        }

        try {
            jda = JDABuilder.createDefault(config.getBotToken()).enableIntents(Arrays.asList(GatewayIntent.values())).build().awaitReady();
        } catch (LoginException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        jda.addEventListener(new MessageListener(this));
        registerCommand("quiz",new QuizCommand(this));
        System.out.println("QuizBot is running.");
    }

    private void registerCommand(String command, CommandExecutor executor) {
        commandMap.put(command, executor);
    }

    public void executeCommand(Message message, String command, String[] args) {
        CommandExecutor executor = commandMap.get(command.toLowerCase(Locale.ROOT));
        if(executor == null) {
            System.out.println("No executor found for command " + command);
            return;
        }
        System.out.println("Executing command " + command);
        CommandResult result = executor.execute(message, command, args);
        switch (result) {
            case WRONG_USAGE -> {
                message.reply("Wrong usage of command \"" + command + "\".\n\n**Usage:**\n" + executor.usage().replace("<command>",config.getPrefix() + command)).queue();
                break;
            }
        }
    }

    public String getCommandName(String command) {
        return getConfig().getPrefix() + command;
    }

}
