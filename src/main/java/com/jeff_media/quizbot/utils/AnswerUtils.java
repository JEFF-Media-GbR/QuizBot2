package com.jeff_media.quizbot.utils;

import com.jeff_media.quizbot.data.EqualDistributedList;
import net.dv8tion.jda.api.entities.Message;

import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

public class AnswerUtils {

    private static final EqualDistributedList<String> CORRECT_RESPONSES = new EqualDistributedList<String>(
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
    );

    private static final EqualDistributedList<String> YOURE_ALL_NOOB_RESPONSES = new EqualDistributedList<>(
            "No one? The correct answer would have been %s.",
            "Easy: %s.",
            "I know this one! %s!",
            "Really? It's %s of course.",
            "Why y'all such noobs? It's %s.",
            "Shame! It's obviously %s.",
            "Yikes! It would have been %s."
    );

    private static final EqualDistributedList<String> EVERYONE_DIED = new EqualDistributedList<>(
            "Did everyone die? Guess I'll stop then.",
            "Seems like everyone fell asleep. Guess I'll stop then.",
            "Okay, I get it, everyone went home. I'll shut up now.",
            "Whom am I actually talking to? I'll shut up now."
    );

    public static String getCorrectResponse() {
        return CORRECT_RESPONSES.getNext();
    }

    public static String getYoureAllNoobResponse(String correctAnswer) {
        return String.format(YOURE_ALL_NOOB_RESPONSES.getNext(), "**" + correctAnswer + "**");
    }

    public static String getEveryoneDiedResponse() {
        return EVERYONE_DIED.getNext();
    }

    public static String stripMessage(Message message) {
        return stripText(message.getContentRaw());
    }

    public static String stripText(String text) {
        text = text.toLowerCase(Locale.ROOT);
        return text;
    }

}
