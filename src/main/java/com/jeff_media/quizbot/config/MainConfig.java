package com.jeff_media.quizbot.config;

import java.io.FileNotFoundException;

public class MainConfig extends Config {
    public MainConfig() throws FileNotFoundException {
        super("config.yml");
    }

    public String botToken() {
        return (String) map.getOrDefault("bot-token","YOUR_BOT_TOKEN");
    }

    public String prefix() {
        return (String) map.getOrDefault("prefix","?quiz");
    }

    public int timePerQuestion() {
        return (int) map.getOrDefault("time-per-question",20);
    }

    public int typingDurationPerQuestion() {
        return (int) map.getOrDefault("typing-duration-per-question",4);
    }

    public boolean everyoneCanStop() {
        return (boolean) map.getOrDefault("everyone-can-stop", false);
    }

    public int winThreshold() {
        return (int) map.getOrDefault("win-threshold", 10);
    }

    public boolean distributeQuestionsEvenly() {
        return (boolean) map.getOrDefault("distribute-questions-evenly", true);
    }

    public int stopAfterQuestionsWithoutAnswers() {
        return (int) map.getOrDefault("stop-after-questions-without-answers", 10);
    }
}
