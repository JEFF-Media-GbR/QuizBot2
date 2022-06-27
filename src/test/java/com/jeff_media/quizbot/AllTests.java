package com.jeff_media.quizbot;

import com.jeff_media.quizbot.data.EqualDistributedList;
import com.jeff_media.quizbot.data.Question;
import com.jeff_media.quizbot.data.ShortTermMemoryBuffer;
import com.jeff_media.quizbot.utils.AnswerUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class AllTests {

    @Test
    public void testShortTermMemoryBuffer() {
        ShortTermMemoryBuffer<Integer> buffer = new ShortTermMemoryBuffer<>(3);
        buffer.remember(1);
        Assert.assertTrue(buffer.remembers(1));
        buffer.remember(2);
        Assert.assertTrue(buffer.remembers(1));
        buffer.remember(3);
        Assert.assertTrue(buffer.remembers(1));
        buffer.remember(4);
        Assert.assertFalse(buffer.remembers(1));
    }
}
