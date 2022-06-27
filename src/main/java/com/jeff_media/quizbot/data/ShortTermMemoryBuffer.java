package com.jeff_media.quizbot.data;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class ShortTermMemoryBuffer<T> {

    private final List<T> buffer = new ArrayList<>();
    private final int size;

    public ShortTermMemoryBuffer(int size) {
        this.size = size;
    }

    public void remember(T element) {
        buffer.add(element);
        if(buffer.size() > size) {
            buffer.remove(0);
        }
    }

    public boolean remembers(T element) {
        return buffer.contains(element);
    }

}
