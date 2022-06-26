package com.jeff_media.quizbot.utils;

import net.dv8tion.jda.api.entities.Message;

import java.util.Locale;

public class AnswerUtils {

    //private static final String REMOVED_CHARS_REGEX = "[^a-z0-9.]";

    public static String stripMessage(Message message) {
        return stripText(message.getContentRaw());
    }

    public static String stripText(String text) {
        text = text.toLowerCase(Locale.ROOT);
        //text = text.replaceAll(REMOVED_CHARS_REGEX,"");
        return text;
    }

}
