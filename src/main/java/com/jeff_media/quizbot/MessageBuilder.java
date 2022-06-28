package com.jeff_media.quizbot;

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
    private boolean thenType;

    public MessageBuilder title(String title) {
        this.title = title;
        return this;
    }

    public MessageBuilder description(String description) {
        this.description = description;
        return this;
    }

    public MessageBuilder thenType() {
        this.thenType = true;
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

    public MessageBuilder embed() {
        this.embed = true;
        return this;
    }

    public MessageBuilder(TextChannel channel) {
        this.channel = channel;
    }

    public CompletableFuture<Message> send() {
        CompletableFuture<Message> future;
        if(embed) {
            EmbedBuilder builder = new EmbedBuilder();
            if(title != null) {
                builder.setTitle(title);
            }
            if(description != null) {
                builder.setDescription(description);
            }
            if(replyTo != null) {
                future = replyTo.replyEmbeds(builder.build()).submit();
            } else {
                future = channel.sendMessageEmbeds(builder.build()).submit();
            }
        } else {
            String message = "";
            if(title != null && description != null) {
                message = title + "\n" + description;
            } else if(title != null) {
                message = title;
            } else {
                message = description;
            }
            if(replyTo != null) {
                future = replyTo.reply(message).submit();
            } else {
                future = channel.sendMessage(message).submit();
            }
        }
        if(thenType) {
            future.thenAccept(message -> message.getTextChannel().sendTyping().queue());
        }
        return future;
    }
}
