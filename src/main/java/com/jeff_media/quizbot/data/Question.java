package com.jeff_media.quizbot.data;

import com.jeff_media.quizbot.MapSerializable;
import com.jeff_media.quizbot.utils.AnswerUtils;
import com.jeff_media.quizbot.utils.YamlUtils;
import lombok.Getter;
import net.dv8tion.jda.api.entities.Message;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Objects;

public class Question extends MapSerializable {

    @Getter private final String question;
    @Getter private final AnswerList answerList;
    @Getter private final int extraTime;

    public Question(String question, AnswerList answerList) {
        this.question = question;
        this.answerList = answerList;
        this.extraTime = 0;
    }

    public Question(Map<String,Object> map) {
        this.question = YamlUtils.getString(map, "question");
        this.answerList = new AnswerList(YamlUtils.getList(map, "answers"));
        this.extraTime = (int) map.getOrDefault("extra-time",0);
    }

    public String getExtraInformation() {
        StringBuilder builder = new StringBuilder();
        if(extraTime > 0) {
            builder.append("Extra time: +").append(extraTime).append(" seconds");
        }
        return builder.toString();
    }

    @Override
    public Map<String, Object> serialize() {
        return new MapSerializable.Builder().put("question",question).put("answers", answerList.getCorrectAnswers()).serialize();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question1 = (Question) o;
        return question.equals(question1.question);
    }

    @Override
    public int hashCode() {
        return Objects.hash(question);
    }

    @Override
    public String toString() {
        return "Question{" + "question='" + question + '\'' + ", answerList=" + answerList + ", extraTime=" + extraTime + "} " + super.toString();
    }
}
