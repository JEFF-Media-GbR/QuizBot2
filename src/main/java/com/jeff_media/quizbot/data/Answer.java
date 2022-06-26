package com.jeff_media.quizbot.data;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Answer {

    @Getter private final String correctAnswerDisplay;
    @Getter private final List<String> correctAnswers;

    public Answer(Object answers) {
        this.correctAnswers = new ArrayList<String>();
        if(answers instanceof List list) {
            list.forEach(answer -> this.correctAnswers.add(String.valueOf(answer)));
        } else {
            correctAnswers.add(String.valueOf(answers));
        }
        this.correctAnswerDisplay = correctAnswers.get(0);
    }

}
