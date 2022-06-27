package com.jeff_media.quizbot.command;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public interface CommandExecutor {

    String name();

    String description();

    String usage();

    CommandResult execute(Message message, String command, String[] args);

}
