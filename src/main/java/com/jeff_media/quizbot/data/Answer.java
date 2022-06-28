package com.jeff_media.quizbot.data;

import java.util.Map;

public class Answer {

    private final String answer;

    public static Answer deserialize(Object answer) {
        if(answer instanceof Map) {
            return new Answer((Map<String,Object>) answer);
        } else {
            return new Answer(String.valueOf(answer));
        }
    }
    private Answer(String answer) {
        this.answer = answer;
    }

    private Answer(Map<String, Object> object) {
        this.answer = String.valueOf(object.get("answer"));
    }

    @Override
    public String toString() {
        return "Answer{" + "answer='" + answer + '\'' + '}';
    }

    public boolean isCorrect(String input) {
        if(input.length() > answer.length() + 30) {
            return false;
        }
        return (" " + input + " ").toLowerCase().contains((" " + answer + " ").toLowerCase());
    }

    public String getAnswer() {
        return answer;
    }
}
