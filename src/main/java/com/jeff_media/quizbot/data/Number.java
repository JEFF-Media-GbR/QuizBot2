package com.jeff_media.quizbot.data;

import java.util.Locale;
import java.util.regex.Pattern;

public class Number {

    private static final Pattern PATTERN = Pattern.compile("^(0?[1-9]|[1-9][0-9])$");

    private final int number;

    public static boolean isNumber(String input) {
        return input.equals("0") || PATTERN.matcher(input).matches();
    }

    public Number(int value) {
        this.number = value;
    }

    public boolean matches(String input) {
        if(input.equals(String.valueOf(number))) {
            return true;
        }
        if(number == 0) {
            return input.equals("0") || input.equalsIgnoreCase("zero") || input.equalsIgnoreCase("none");
        }
        String[] words = DefaultNumber.getName(number);
        if(words.length == 1) {
            return input.equalsIgnoreCase(words[0]);
        } else {
            input = input.toLowerCase(Locale.ROOT);
            String tens = words[0];
            String ones = words[1];
            return input.equals(tens + " " + ones) || input.equalsIgnoreCase(tens + ones) || input.equalsIgnoreCase(tens + "-" + ones);
        }
    }

}
