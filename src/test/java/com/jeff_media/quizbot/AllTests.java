package com.jeff_media.quizbot;

import com.jeff_media.quizbot.data.Question;
import com.jeff_media.quizbot.utils.AnswerUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class AllTests {

    @Test
    public void testQuestionDeserialize() {
        Question question = MapSerializable.deserialize(getClass().getResourceAsStream("/question.yml"), Question.class);
        Assert.assertEquals("Is this a test question?", question.getQuestion());
        Assert.assertEquals("Yes",question.getAnswer().getCorrectAnswerDisplay());
        Assert.assertEquals(List.of("Yes","No","Maybe","I don't know"),question.getAnswer().getCorrectAnswers());
    }

    @Test
    public void testAnswerRegex() {
        Assert.assertEquals("thisisthestrippedtext", AnswerUtils.stripText("This is the stripped text?"));
    }
}
