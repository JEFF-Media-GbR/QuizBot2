package com.jeff_media.quizbot;

import com.jeff_media.quizbot.data.ImpairedShortTermMemoryBuffer;
import org.junit.Assert;
import org.junit.Test;

public class AllTests {

    @Test
    public void testShortTermMemoryBuffer() {
        ImpairedShortTermMemoryBuffer<Integer> buffer = new ImpairedShortTermMemoryBuffer<>(3);
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
