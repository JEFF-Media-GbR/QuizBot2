package com.jeff_media.quizbot;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public final class YamlUtils {

    public static List<String> getStringList(Map<String,Object> map, String key) {
        Object value = map.get(key);
        if(value instanceof String) {
            return List.of((String)value);
        } else if(value instanceof List) {
            return (List<String>)value;
        } else {
            throw new IllegalArgumentException("Expected " + key + " to be a String or List<String>");
        }
    }

    public static String getString(Map<String,Object> map, String key) {
        Object value = map.get(key);
        if(value instanceof String) {
            return (String) value;
        } else if(value instanceof List) {
            return ((List<String>)value).get(0);
        } else {
            throw new IllegalArgumentException("Expected " + key + " to be a String or List<String>");
        }
    }

    public static Map<String,Object> load(InputStream stream) {
        return new Yaml().load(stream);
    }
}
