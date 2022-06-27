package com.jeff_media.quizbot;

import com.jeff_media.quizbot.command.CommandExecutor;
import com.jeff_media.quizbot.command.CommandResult;
import com.jeff_media.quizbot.command.commands.HelpCommand;
import com.jeff_media.quizbot.command.commands.ListCommand;
import com.jeff_media.quizbot.command.commands.StartCommand;
import com.jeff_media.quizbot.command.commands.StopCommand;
import com.jeff_media.quizbot.config.MainConfig;
import com.jeff_media.quizbot.data.Game;
import lombok.Getter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class QuizBot {

    @Getter private final MainConfig config;
    private final JDA jda;
    @Getter private final Map<TextChannel,Game> runningGames = new HashMap<>();
    @Getter private final Map<String, CommandExecutor> commandMap = new LinkedHashMap<>();
    private final File categoriesFolder = new File("categories");

    public QuizBot() {
        try {
            config = new MainConfig();
        } catch (FileNotFoundException exception) {
            throw new RuntimeException(exception);
        }

        try {
            jda = JDABuilder
                    .createDefault(config.botToken())
                    .enableIntents(Arrays.asList(GatewayIntent.values()))
                    .setChunkingFilter(ChunkingFilter.ALL)
                    .setMemberCachePolicy(MemberCachePolicy.ALL)
                    .enableCache(Arrays.asList(CacheFlag.values()))
                    .build().awaitReady();
        } catch (LoginException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        jda.addEventListener(new MessageListener(this));
        registerCommand(new HelpCommand(this));
        registerCommand(new ListCommand(this));
        registerCommand(new StartCommand(this));
        registerCommand(new StopCommand(this));
        System.out.println("QuizBot is running.");
    }

    private void registerCommand(CommandExecutor executor) {
        commandMap.put(executor.name(), executor);
    }

    public void executeCommand(Message message, String command, String[] args) {
        CommandExecutor executor = commandMap.get(command.toLowerCase(Locale.ROOT));
        if(executor == null) {
            System.out.println("No executor found for command " + command);
            message.reply("Unknown command \"" + command + "\". Use \"" + getCommandName("help") + "\" for a list of commands.").queue();
            return;
        }
        System.out.println("Executing command " + command);
        CommandResult result = executor.execute(message, command, args);
        switch (result) {
            case WRONG_USAGE -> {
                message.reply("Wrong usage of command \"" + command + "\".\n\n**Usage:**\n" + executor.usage().replace("<command>",config.prefix() + command)).queue();
                break;
            }
        }
    }

    public String getCommandName(String command) {
        return getConfig().prefix() + " " + command;
    }

    public static List<String> getAvailableCategories() {
        List<String> categories = new ArrayList<>();
        return categories;
    }

}
