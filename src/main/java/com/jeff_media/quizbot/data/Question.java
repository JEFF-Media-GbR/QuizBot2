package com.jeff_media.quizbot.data;

import com.jeff_media.quizbot.MapSerializable;
import com.jeff_media.quizbot.YamlUtils;
import lombok.Getter;

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
}
