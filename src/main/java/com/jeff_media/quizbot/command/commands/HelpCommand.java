package com.jeff_media.quizbot.command.commands;

import com.jeff_media.quizbot.QuizBot;
import com.jeff_media.quizbot.command.CommandExecutor;
import com.jeff_media.quizbot.command.CommandResult;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;

public class HelpCommand implements CommandExecutor {

    private final QuizBot bot;

    public HelpCommand(QuizBot bot) {
        this.bot = bot;
    }

    public String name() {
        return "help";
    }

    @Override
    public CommandResult execute(Message message, String command, String[] args) {
        EmbedBuilder builder = new EmbedBuilder();
        String prefix = bot.getConfig().prefix();
        for(CommandExecutor executor : bot.getCommandMap().values()) {
            String name = executor.name();
            String usage = executor.usage();
            String fullCommand = prefix + " " + name + (usage.isEmpty() ? "" : " " + usage);
            builder.addField(fullCommand, executor.description(), false);
        }
        message.replyEmbeds(builder.build()).queue();
        return CommandResult.OKAY;
    }

    @Override
    public String usage() {
        return "";
    }

    @Override
    public String description() {
        return "Shows this help message.";
    }
}
