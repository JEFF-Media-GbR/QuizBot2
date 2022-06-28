package com.jeff_media.quizbot.command.commands;

import com.jeff_media.quizbot.QuizBot;
import com.jeff_media.quizbot.command.CommandExecutor;
import com.jeff_media.quizbot.command.CommandResult;
import com.jeff_media.quizbot.data.Game;
import com.jeff_media.quizbot.MessageBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;

public class StopCommand implements CommandExecutor {

    private final QuizBot bot;

    public StopCommand(QuizBot bot) {
        this.bot = bot;
    }

    @Override
    public CommandResult execute(Message message, String command, String[] args) {
        Game game = bot.getRunningGames().get(message.getTextChannel());
        if(game == null) {
            new MessageBuilder(message.getTextChannel())
                    .description("There's no quiz running in this channel.")
                    .replyTo(message)
                    .send();
            return CommandResult.UNKNOWN_ERROR;
        }
        Member starter = game.getStarter();
        if(!bot.getConfig().everyoneCanStop()) {
            if (!starter.equals(message.getMember())) {
                new MessageBuilder(message.getTextChannel())
                        .description("You can't stop the quiz if you're not the one who started it.")
                        .replyTo(message)
                        .send();
                return CommandResult.UNKNOWN_ERROR;
            }
        }

        game.endGameAndShowResults();

        return CommandResult.OKAY;
    }

    @Override
    public String name() {
        return "stop";
    }

    @Override
    public String description() {
        return "Stops the current quiz.";
    }

    @Override
    public String usage() {
        return "";
    }
}

