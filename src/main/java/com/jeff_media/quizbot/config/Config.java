package com.jeff_media.quizbot.config;

import com.jeff_media.quizbot.YamlUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class Config implements Map<String,Object> {

    protected final Map<String, Object> map;

    public Config(String fileName) throws FileNotFoundException {
        boolean found = false;
        Map<String,Object> map = new HashMap<>();
        try {
            map.putAll(YamlUtils.load(getClass().getResourceAsStream("/" + fileName)));
            found = true;
        } catch (Exception ignored) {

        }
        try {
            map.putAll(YamlUtils.load(new FileInputStream(fileName)));
            found = true;
        } catch (Exception ignored) {

        }
        if(!found) {
            throw new FileNotFoundException();
        }
        this.map = Collections.unmodifiableMap(map);
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    @Override
    public Object get(Object key) {
        return map.get(key);
    }

    @Nullable
    @Override
    public Object put(String key, Object value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object remove(Object key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void putAll(@NotNull Map<? extends String, ?> m) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @NotNull
    @Override
    public Set<String> keySet() {
        return map.keySet();
    }

    @NotNull
    @Override
    public Collection<Object> values() {
        return map.values();
    }

    @NotNull
    @Override
    public Set<Entry<String, Object>> entrySet() {
        return map.entrySet();
    }
}
