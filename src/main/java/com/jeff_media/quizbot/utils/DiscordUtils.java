package com.jeff_media.quizbot.utils;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

import javax.annotation.Nullable;

public class DiscordUtils {

    public static void sendMessage(TextChannel channel, String message) {
        channel.sendMessage(message).queue();
    }

    public static void sendEmbed(TextChannel channel, @Nullable String title, @Nullable String description) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle(title);
        builder.setDescription(description);
        channel.sendMessageEmbeds(builder.build()).queue();
    }

}
