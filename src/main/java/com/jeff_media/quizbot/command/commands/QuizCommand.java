package com.jeff_media.quizbot.command.commands;

import com.jeff_media.quizbot.QuizBot;
import com.jeff_media.quizbot.command.CommandExecutor;
import com.jeff_media.quizbot.command.CommandResult;
import com.jeff_media.quizbot.data.Game;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.Locale;

public class QuizCommand implements CommandExecutor {

    private final QuizBot bot;

    public QuizCommand(QuizBot bot) {
        this.bot = bot;
    }

    @Override
    public CommandResult execute(Message message, String command, String[] args) {
        if(args.length == 0) {
            return CommandResult.WRONG_USAGE;
        }

        switch (args[0].toLowerCase(Locale.ROOT)) {
            case "start" -> {
                if(args.length < 2) {
                    return CommandResult.WRONG_USAGE;
                } else {
                    return start(message, args[1]);
                }
            }
            default -> {
                return CommandResult.WRONG_USAGE;
            }
        }
    }

    public CommandResult start(Message message, String quizName) {

        TextChannel channel = message.getTextChannel();
        Member member = message.getMember();

        if(bot.getRunningGames().get(channel) != null) {
            message.reply("There's already a quiz running in this channel.").queue();
            return CommandResult.UNKNOWN_ERROR;
        }

        Game game;
        try {
            game = new Game(quizName, channel, member);
        } catch (Game.CategoryNotFoundException e) {
            message.reply("Unknown category \"" + quizName + "\". Enter \"" + bot.getCommandName("quiz list") + "\" for a list of available categories.").queue();
            return CommandResult.UNKNOWN_ERROR;
        }

        bot.getRunningGames().put(channel, game);

        channel.sendMessage(member.getEffectiveName() + " started quiz " + quizName).queue();

        return CommandResult.OKAY;
    }

    @Override
    public String usage() {
        return "<command> start <category>";
    }
}
