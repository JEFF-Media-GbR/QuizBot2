package com.jeff_media.quizbot.data;

public class NumericAnswer extends Answer {

    private final Number num;
    public NumericAnswer(int num) {
        super(String.valueOf(num));
        this.num = new Number(num);
        System.out.println("Creating numeric answer");
    }

    public NumericAnswer(String num) {
        this(Integer.parseInt(num));
    }

    @Override
    public boolean isCorrect(String input) {
        return num.matches(input);
    }
}
