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
}
