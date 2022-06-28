package com.jeff_media.quizbot.data;

import com.jeff_media.quizbot.utils.AnswerUtils;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class AnswerList {

    @Getter private final List<Answer> correctAnswers;

    public AnswerList(Object object) {
        this.correctAnswers = new ArrayList<>();
        if(object instanceof List list) {
            list.forEach(answer -> this.correctAnswers.add(Answer.deserialize(answer)));
        } else {
            correctAnswers.add(Answer.deserialize(object));
        }
    }

    public String getCorrectAnswerDisplay() {
        return correctAnswers.get(0).getAnswer();
    }

    @Override
    public String toString() {
        return "AnswerList{" + "correctAnswers=" + correctAnswers + '}';
    }

    public boolean isCorrect(String input) {
        return correctAnswers.stream().anyMatch(correct -> correct.isCorrect(input));
    }

}
