package com.jeff_media.quizbot.data;

import com.jeff_media.quizbot.MapSerializable;
import com.jeff_media.quizbot.utils.AnswerUtils;
import com.jeff_media.quizbot.utils.YamlUtils;
import lombok.Getter;
import net.dv8tion.jda.api.entities.Message;

import java.util.Locale;
import java.util.Map;

public class Question extends MapSerializable {

    @Getter private final String question;
    @Getter private final Answer answer;

    public Question(String question, Answer answer) {
        this.question = question;
        this.answer = answer;
    }

    public Question(Map<String,Object> map) {
        this.question = YamlUtils.getString(map, "question");
        this.answer = new Answer(YamlUtils.getStringList(map, "answers"));
    }

    @Override
    public Map<String, Object> serialize() {
        return new MapSerializable.Builder().put("question",question).put("answers",answer.getCorrectAnswers()).serialize();
    }

    /**
     * Checks if the given answer is correct. An answer is correct if it is one of the correct answers and if its length
     * isn't greater than the length of the correct answer + 30 chars. This is done to allow answers like "I think it's XYZ?"
     * without allowing hacks like sending 30 different answers in one message.
     * @param message
     * @return
     */
    public boolean isCorrectAnswer(Message message) {
        String given = AnswerUtils.stripMessage(message);
        return answer.getCorrectAnswers().stream().anyMatch(correct -> {
            correct = AnswerUtils.stripText(correct);
            int correctLength = correct.length();
            int givenLength = given.length();
            if(givenLength > correctLength + 30) {
                return false;
            }
            return given.contains(correct);
        });
    }
}
