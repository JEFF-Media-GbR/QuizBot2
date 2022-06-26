package com.jeff_media.quizbot.command.commands;

import com.jeff_media.quizbot.command.CommandExecutor;
import com.jeff_media.quizbot.command.CommandResult;
import net.dv8tion.jda.api.entities.Message;

public class ListCommand implements CommandExecutor {
    @Override
    public CommandResult execute(Message message, String command, String[] args) {
        return null;
    }

    @Override
    public String usage() {
        return null;
    }
}
