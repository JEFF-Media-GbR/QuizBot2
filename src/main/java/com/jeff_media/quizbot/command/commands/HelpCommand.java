package com.jeff_media.quizbot.command.commands;

import com.jeff_media.quizbot.QuizBot;
import com.jeff_media.quizbot.command.CommandExecutor;
import com.jeff_media.quizbot.command.CommandResult;
import net.dv8tion.jda.api.entities.Message;

public class HelpCommand implements CommandExecutor {

    private final QuizBot bot;

    public HelpCommand(QuizBot bot) {
        this.bot = bot;
    }

    @Override
    public CommandResult execute(Message message, String command, String[] args) {
        message.reply("**Available commands:**\n\n" +
                "**" + bot.getCommandName("help") + "** - Shows this message.\n" +
                "**" + bot.getCommandName("list") + "** - Lists available categories.\n" +
                "**" + bot.getCommandName("start <category>") + "** - Starts a quiz.\n").queue();
        return CommandResult.OKAY;
    }

    @Override
    public String usage() {
        return null;
    }
}
