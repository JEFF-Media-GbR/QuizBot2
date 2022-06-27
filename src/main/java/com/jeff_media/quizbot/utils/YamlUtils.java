package com.jeff_media.quizbot.utils;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class YamlUtils {

    public static List<String> getStringList(Map<String,Object> map, String key) {
        Object value = map.get(key);
        List<String> strings = new ArrayList<>();
        if(value instanceof List list) {
            ((List<?>)list).forEach(object -> strings.add(String.valueOf(object)));
        } else {
            strings.add(String.valueOf(value));
        }
        return strings;
    }

    public static String getString(Map<String,Object> map, String key) {
        Object value = map.get(key);
        if(value instanceof List list) {
            return String.valueOf(list.get(0));
        }
        return String.valueOf(value);
    }

    public static Map<String,Object> load(InputStream stream) {
        return new Yaml().load(stream);
    }
}
