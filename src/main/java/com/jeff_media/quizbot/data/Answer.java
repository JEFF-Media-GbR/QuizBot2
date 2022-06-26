package com.jeff_media.quizbot.data;

import com.jeff_media.quizbot.MapSerializable;
import com.jeff_media.quizbot.YamlUtils;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Answer {

    @Getter private final String correctAnswerDisplay;
    @Getter private final List<String> correctAnswers;

    public Answer(List<String> answers) {
        this.correctAnswers = new ArrayList<>(answers);
        this.correctAnswerDisplay = correctAnswers.get(0);
    }
}
