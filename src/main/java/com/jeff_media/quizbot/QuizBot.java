package com.jeff_media.quizbot;

import com.jeff_media.quizbot.command.CommandExecutor;
import com.jeff_media.quizbot.command.CommandResult;
import com.jeff_media.quizbot.command.commands.HelpCommand;
import com.jeff_media.quizbot.command.commands.ListCommand;
import com.jeff_media.quizbot.command.commands.StartCommand;
import com.jeff_media.quizbot.command.commands.StopCommand;
import com.jeff_media.quizbot.config.Config;
import com.jeff_media.quizbot.config.MainConfig;
import com.jeff_media.quizbot.data.Game;
import com.jeff_media.quizbot.logging.ConsoleLogger;
import com.jeff_media.quizbot.logging.ILogger;
import com.jeff_media.quizbot.utils.YamlUtils;
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

    @Getter private final ILogger logger = new ConsoleLogger();
    @Getter private final Map<TextChannel,Game> runningGames = new HashMap<>();
    @Getter private final Map<String, CommandExecutor> commandMap = new LinkedHashMap<>();
    @Getter private final List<String> availableQuizzes = new ArrayList<>();
    @Getter private final File categoriesFolder = new File("categories");
    @Getter private final MainConfig config;
    @Getter private final JDA jda;

    public QuizBot() {
        if(!categoriesFolder.exists()) {
            if(!categoriesFolder.mkdir()) {
                throw new RuntimeException("Could not create directory: " + categoriesFolder.getAbsolutePath());
            } else {
                logger.info("Created directory: " + categoriesFolder.getAbsolutePath());
            }
        }
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
        logger.info("Successfully logged in to Discord");
        jda.addEventListener(new MessageListener(this));
        registerCommand(new HelpCommand(this));
        registerCommand(new ListCommand(this));
        registerCommand(new StartCommand(this));
        registerCommand(new StopCommand(this));
        loadCategories();

    }

    private void loadCategories() {
        availableQuizzes.clear();
        File[] categories = categoriesFolder.listFiles();
        if(categories == null) {
            throw new RuntimeException("Not a directory or could not list files in directory: " + categoriesFolder.getAbsolutePath());
        }
        for (File file : categories) {
            availableQuizzes.add(file.getName().substring(0, file.getName().lastIndexOf(".")));
        }
        try {
            Config defaultCategories = new Config("default-categories.yml");
            availableQuizzes.addAll(YamlUtils.getStringList(defaultCategories, "default-categories"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        availableQuizzes.sort(String::compareToIgnoreCase);
        logger.info("Loaded " + availableQuizzes.size() + " categories: " + String.join(", ", availableQuizzes));
    }

    private void registerCommand(CommandExecutor executor) {
        commandMap.put(executor.name(), executor);
        logger.debug("Registered command \"" + executor.name() + "\" to executor " + executor.getClass().getName());
    }

    public void executeCommand(Message message, String command, String[] args) {
        logger.info(message.getAuthor().getName() + " executed command \"" + command + "\" with args " + Arrays.toString(args) + " in channel " + message.getChannel().getName());
        CommandExecutor executor = commandMap.get(command.toLowerCase(Locale.ROOT));
        if(executor == null) {
            logger.debug("No executor found for command " + command);
            message.reply("Unknown command \"" + command + "\". Use \"" + getCommandName("help") + "\" for a list of commands.").queue();
            return;
        }
        CommandResult result = executor.execute(message, command, args);
        switch (result) {
            case WRONG_USAGE -> {
                message.reply("Wrong usage of command \"" + command + "\".\n\n**Usage:**\n" + executor.usage().replace("<command>",config.prefix() + command)).queue();
                break;
            }
        }
        logger.debug("Command \"" + command + "\" executed with result " + result);
    }

    public String getCommandName(String command) {
        return getConfig().prefix() + " " + command;
    }

}
