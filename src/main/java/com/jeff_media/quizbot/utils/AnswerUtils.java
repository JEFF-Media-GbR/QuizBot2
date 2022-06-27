package com.jeff_media.quizbot.utils;

import net.dv8tion.jda.api.entities.Message;

import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

public class AnswerUtils {

    private static final String[] CORRECT_RESPONSES = new String[] {
            "Correct!",
            "I'll second that!",
            "Exactly!",
            "Agreed!",
            "That's right!",
            "Sure thing!",
            "Of course!",
            "You're right!",
            "Word up!",
            "Spot-on!"
    };

    private static final String[] YOURE_ALL_NOOB_RESPONSES = new String[] {
            "No one? The correct answer would have been %s.",
            "Easy: %s.",
            "I know this one! %s!",
            "Really? It's %s of course.",
            "Why y'all such noobs? It's %s.",
            "Shame! It's obviously %s.",
            "Yikes! It would have been %s."
    };

    public static String getCorrectResponse() {
        return CORRECT_RESPONSES[ThreadLocalRandom.current().nextInt(CORRECT_RESPONSES.length)];
    }

    public static String getYoureAllNoobResponse(String correctAnswer) {
        return String.format(YOURE_ALL_NOOB_RESPONSES[ThreadLocalRandom.current().nextInt(YOURE_ALL_NOOB_RESPONSES.length)], "**" + correctAnswer + "**");
    }

    public static String stripMessage(Message message) {
        return stripText(message.getContentRaw());
    }

    public static String stripText(String text) {
        text = text.toLowerCase(Locale.ROOT);
        return text;
    }

}
