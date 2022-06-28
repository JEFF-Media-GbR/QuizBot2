package com.jeff_media.quizbot.data;

import java.util.Map;

public class Answer {

    private final String answer;

    public static Answer deserialize(Object answer) {
        if(answer instanceof Map) {
            @SuppressWarnings("unchecked")
            Answer toReturn = deserialize((Map<String,Object>)answer);
            return toReturn;
        } else /*if(answer instanceof String string)*/{
            String string = String.valueOf(answer);
            if(Number.isNumber(string)) {
                return new NumericAnswer(string);
            }
            return new Answer(string);
        }/* else {
            throw new IllegalArgumentException("Could not parse answer: " + answer);
        }*/
    }

    public static Answer deserialize(Map<String,Object> map) {
        Object answer = map.get("answer");
        return deserialize(answer);
    }

    Answer(String answer) {
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
