package com.jeff_media.quizbot.utils;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.concurrent.CompletableFuture;

public class MessageBuilder {

    private final TextChannel channel;
    private String title;
    private String description;
    private Message replyTo;
    private boolean embed;

    public MessageBuilder title(String title) {
        this.title = title;
        return this;
    }

    public MessageBuilder description(String description) {
        this.description = description;
        return this;
    }

    public MessageBuilder replyTo(Message replyTo) {
        this.replyTo = replyTo;
        return this;
    }

    public MessageBuilder replyTo(CompletableFuture<Message> replyTo) {
        this.replyTo = replyTo.getNow(null);
        return this;
    }

    public MessageBuilder embed(boolean embed) {
        this.embed = embed;
        return this;
    }

    public MessageBuilder(TextChannel channel) {
        this.channel = channel;
    }

    public CompletableFuture<Message> send() {
        CompletableFuture<Message> future = new CompletableFuture<>();
        if(embed) {
            EmbedBuilder builder = new EmbedBuilder();
            if(title != null) {
                builder.setTitle(title);
            }
            if(description != null) {
                builder.setDescription(description);
            }
            if(replyTo != null) {
                replyTo.replyEmbeds(builder.build()).queue(future::complete, future::completeExceptionally);
            } else {
                channel.sendMessageEmbeds(builder.build()).queue(future::complete,future::completeExceptionally);
            }
        } else {
            String message = "";
            if(title != null && description != null) {
                message = "**" + title + "**\n" + description;
            } else if(title != null) {
                message = "**" + title + "**";
            } else {
                message = description;
            }
            if(replyTo != null) {
                replyTo.reply(message).queue(future::complete, future::completeExceptionally);
            } else {
                channel.sendMessage(message).queue(future::complete, future::completeExceptionally);
            }
        }
        return future;
    }
}
