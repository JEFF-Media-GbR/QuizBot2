package com.jeff_media.quizbot.config;

import java.io.FileNotFoundException;

public class MainConfig extends Config {
    public MainConfig() throws FileNotFoundException {
        super("config.yml");
    }

    public String getBotToken() {
        return (String) map.get("bot-token");
    }

    public String getPrefix() {
        return (String) map.get("prefix");
    }

    public int getTimePerQuestion() {
        return (int) map.get("time-per-question");
    }

    public int getTypingDurationPerQuestion() {
        return (int) map.get("typing-duration-per-question");
    }

    public boolean everyoneCanStop() {
        return (boolean) map.get("everyone-can-stop");
    }

    public int getWinThreshold() {
        return (int) map.get("win-threshold");
    }
}
