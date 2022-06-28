package com.jeff_media.quizbot.data;

import lombok.Getter;

import java.util.Arrays;
import java.util.regex.Pattern;

public enum DefaultNumber {
    ONE(1,"one"),
    TWO(2,"two"),
    THREE(3,"three"),
    FOUR(4,"four"),
    FIVE(5,"five"),
    SIX(6,"six"),
    SEVEN(7,"seven"),
    EIGHT(8,"eight"),
    NINE(9,"nine"),
    TEN(10,"ten"),
    ELEVEN(11,"eleven"),
    TWELVE(12,"twelve"),
    THIRTEEN(13,"thirteen"),
    FOURTEEN(14,"fourteen"),
    FIFTEEN(15,"fifteen"),
    SIXTEEN(16,"sixteen"),
    SEVENTEEN(17,"seventeen"),
    EIGHTEEN(18,"eighteen"),
    NINETEEN(19,"nineteen"),
    TWENTY(20,"twenty"),
    THIRTY(30,"thirty"),
    FORTY(40,"forty"),
    FIFTY(50,"fifty"),
    SIXTY(60,"sixty"),
    SEVENTY(70,"seventy"),
    EIGHTY(80,"eighty"),
    NINETY(90,"ninety");

    @Getter private final int value;
    @Getter private final String name;

    DefaultNumber(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public static DefaultNumber byValue(int value) {
        return Arrays.stream(values()).filter(defaultNumber -> defaultNumber.value == value).findFirst().orElse(null);
    }

    public static DefaultNumber getTens(int value) {
        int tens = value / 10;
        if(tens == 0) return null;
        return byValue(tens * 10);
    }

    public static DefaultNumber getOnes(int value) {
        int ones = value % 10;
        if(ones == 0) return null;
        return byValue(ones);
    }

    public static String[] getName(int number) {
        if(number >= 100 || number < 0) return null;
        DefaultNumber num = byValue(number);
        if(num != null) {
            return new String[]{num.name};
        }
        DefaultNumber ones = getOnes(number);
        DefaultNumber tens = getTens(number);
        if(ones == null) {
            return new String[]{tens.name};
        }
        return new String[] {tens.name, ones.name};
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(getName(30)));
    }

}
