package com.jeff_media.quizbot.data;

import java.util.HashMap;
import java.util.Map;

/**
 * This is used to prevent the bot from asking the same questions over and over again. A question will only be asked again
 * when no other questions in the chosen category are left.
 */
public class QuestionDistributionComparator implements java.util.Comparator<Question> {

    private static final QuestionDistributionComparator INSTANCE = new QuestionDistributionComparator();
    private static final Map<Integer,Integer> FREQUENCY_MAP = new HashMap<>();

    public static QuestionDistributionComparator instance() {
        return INSTANCE;
    }

    @Override
    public int compare(Question q1, Question q2) {
        return getFrequency(q1) - getFrequency(q2);
    }

    public static void raiseFrequency(Question question) {
        FREQUENCY_MAP.put(question.hashCode(),FREQUENCY_MAP.getOrDefault(question.hashCode(),0) + 1);
    }

    public static int getFrequency(Question question) {
        return FREQUENCY_MAP.getOrDefault(question.hashCode(),0);
    }
}
