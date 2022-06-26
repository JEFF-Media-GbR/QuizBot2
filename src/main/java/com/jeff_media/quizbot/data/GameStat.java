package com.jeff_media.quizbot.data;

import lombok.Getter;
import net.dv8tion.jda.api.entities.Member;
import org.jetbrains.annotations.NotNull;

public class GameStat implements Comparable<GameStat> {

    @Getter private final Member member;
    @Getter private int correctAnswers;
    private long lastCorrectAnswer;

    public GameStat(Member member) {
        this.member = member;
    }

    public void registerCorrectAnswer() {
        correctAnswers++;
        lastCorrectAnswer = System.currentTimeMillis();
    }

    @Override
    public int compareTo(@NotNull GameStat o) {
        if(correctAnswers > o.correctAnswers) return -1;
        if(correctAnswers < o.correctAnswers) return 1;
        return Long.compare(lastCorrectAnswer, o.lastCorrectAnswer);
    }
}
