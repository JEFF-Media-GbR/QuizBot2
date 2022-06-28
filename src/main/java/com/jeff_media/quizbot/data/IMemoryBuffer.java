package com.jeff_media.quizbot.data;

public interface IMemoryBuffer<T> {

    void remember(T element);

    boolean remembers(T element);

}
