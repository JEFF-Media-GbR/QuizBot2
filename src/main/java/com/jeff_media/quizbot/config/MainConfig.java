package com.jeff_media.quizbot.config;

import java.io.FileNotFoundException;

public class MainConfig extends Config {
    public MainConfig() throws FileNotFoundException {
        super("config.yml");
    }

    public String botToken() {
        return (String) map.get("bot-token");
    }

    public String prefix() {
        return (String) map.get("prefix");
    }

    public int timePerQuestion() {
        return (int) map.get("time-per-question");
    }

    public int typingDurationPerQuestion() {
        return (int) map.get("typing-duration-per-question");
    }

    public boolean everyoneCanStop() {
        return (boolean) map.get("everyone-can-stop");
    }

    public int winThreshold() {
        return (int) map.get("win-threshold");
    }

    public boolean distributeQuestionsEvenly() {
        return (boolean) map.get("distribute-questions-evenly");
    }

    public int stopAfterQuestionsWithoutAnswers() {
        return (int) map.get("stop-after-questions-without-answers");
    }
}
