package com.jeff_media.quizbot.data;

import com.jeff_media.quizbot.MapSerializable;
import com.jeff_media.quizbot.YamlUtils;
import com.jeff_media.quizbot.config.Config;
import lombok.Getter;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Game {

    @Getter private final String name;
    @Getter private final List<String> authors;
    @Getter private final List<Question> questions;
    @Getter private final TextChannel channel;
    @Getter private final Member starter;

    public Game(String fileName, TextChannel channel, Member starter) throws CategoryNotFoundException {
        Config config = null;
        try {
            config = new Config(fileName);
        } catch (FileNotFoundException e) {
            throw new CategoryNotFoundException();
        }

        this.name = YamlUtils.getString(config, "name");
        this.authors = YamlUtils.getStringList(config,"authors");
        this.questions = new ArrayList<>();
        List<Map<String,Object>> questionMap = (List<Map<String, Object>>) config.get("questions");
        for(Map<String,Object> map : questionMap) {
            questions.add(new Question(map));
        }

        this.channel = channel;
        this.starter = starter;
    }

    public static class CategoryNotFoundException extends Exception {

    }

    public void handleMessage(Message message) {

    }
}
