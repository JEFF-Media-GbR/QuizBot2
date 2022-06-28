package com.jeff_media.quizbot.command.commands;

import com.jeff_media.quizbot.QuizBot;
import com.jeff_media.quizbot.command.CommandExecutor;
import com.jeff_media.quizbot.command.CommandResult;
import com.jeff_media.quizbot.data.Game;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.Locale;
import java.util.concurrent.CompletableFuture;

public class StartCommand implements CommandExecutor {

    private final QuizBot bot;

    public StartCommand(QuizBot bot) {
        this.bot = bot;
    }

    @Override
    public String name() {
        return "start";
    }

    @Override
    public String description() {
        return "Starts a new quiz.";
    }

    @Override
    public String usage() {
        return "<quiz file>";
    }

    @Override
    public CommandResult execute(Message message, String command, String[] args) {
        if (args.length == 0) {
            return CommandResult.WRONG_USAGE;
        }

        TextChannel channel = message.getTextChannel();
        Member member = message.getMember();
        String quizName = args[0].toLowerCase(Locale.ROOT);

        if (bot.getRunningGames().get(channel) != null) {
            message.reply("There's already a quiz running in this channel.").queue();
            return CommandResult.UNKNOWN_ERROR;
        }

        CompletableFuture<Game> future = Game.fromCategory(bot, quizName, channel, member);
        future.whenComplete((game, throwable) -> {
            if (throwable != null) {
                message.reply("Unknown category \"" + quizName + "\". Enter \"" + bot.getCommandName("list") + "\" for a list of available categories.").queue();
            } else {
                bot.getRunningGames().put(channel, game);
                game.start();
            }
        });


        return CommandResult.OKAY;
    }
}
