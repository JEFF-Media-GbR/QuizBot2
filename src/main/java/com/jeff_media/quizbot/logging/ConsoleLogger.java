package com.jeff_media.quizbot.logging;

import com.jeff_media.quizbot.QuizBot;
import org.slf4j.LoggerFactory;

public class ConsoleLogger implements ILogger {

    private final ch.qos.logback.classic.Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(QuizBot.class);

    @Override
    public void info(String text) {
        logger.info(text);
    }

    @Override
    public void debug(String text) {
        logger.debug(text);
    }

    @Override
    public void warn(String text) {
        logger.warn(text);
    }

    @Override
    public void error(String text) {
        logger.error(text);
    }

    @Override
    public void error(String text, Throwable throwable) {
        logger.error(text, throwable);
    }
}
