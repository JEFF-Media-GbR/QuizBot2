package com.jeff_media.quizbot.data;

import java.util.ArrayList;
import java.util.List;

public class ImpairedShortTermMemoryBuffer<T> {

    private final List<T> buffer = new ArrayList<>();
    private final int size;

    public ImpairedShortTermMemoryBuffer(int size) {
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
