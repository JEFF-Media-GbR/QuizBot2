package com.jeff_media.quizbot.command.commands;

import com.jeff_media.quizbot.QuizBot;
import com.jeff_media.quizbot.command.CommandExecutor;
import com.jeff_media.quizbot.command.CommandResult;
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
        return CommandResult.UNKNOWN_ERROR;
    }

    @Override
    public String usage() {
        return "";
    }
}
