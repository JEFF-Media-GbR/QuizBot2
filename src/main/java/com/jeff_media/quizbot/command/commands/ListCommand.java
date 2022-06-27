package com.jeff_media.quizbot.command.commands;

import com.jeff_media.quizbot.QuizBot;
import com.jeff_media.quizbot.command.CommandExecutor;
import com.jeff_media.quizbot.command.CommandResult;
import com.jeff_media.quizbot.utils.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;

public class ListCommand implements CommandExecutor {

    private final QuizBot bot;

    public ListCommand(QuizBot bot) {
        this.bot = bot;
    }

    public String name() {
        return "list";
    }

    public String description() {
        return "Shows a list of available quizzes.";
    }

    @Override
    public CommandResult execute(Message message, String command, String[] args) {
        new MessageBuilder(message.getTextChannel()).replyTo(message).title("Available quizzes").description(String.join(", ",bot.getCategories())).send();
        return CommandResult.OKAY;
    }

    @Override
    public String usage() {
        return "";
    }
}
