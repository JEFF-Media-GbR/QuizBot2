package com.jeff_media.quizbot.logging;

public interface ILogger {
    void info(String text);

    void debug(String text);

    void warn(String text);

    void error(String text);

    void error(String text, Throwable throwable);
}
