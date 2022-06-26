package com.jeff_media.quizbot;

import com.jeff_media.quizbot.utils.YamlUtils;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public abstract class MapSerializable {

    private static final Map<Class<? extends MapSerializable>,Constructor<? extends MapSerializable>> CONSTRUCTORS = new HashMap<>();

    public abstract Map<String,Object> serialize();

    private static <T extends MapSerializable> Constructor<T> getConstructor(Class<T> clazz) {
        //noinspection unchecked
        Constructor<T> constructor = (Constructor<T>) CONSTRUCTORS.get(clazz);
        if (constructor == null) {
            try {
                constructor = clazz.getDeclaredConstructor(Map.class);
                CONSTRUCTORS.put(clazz, constructor);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("Could not find accessible Map constructor for " + clazz.getName(),e);
            }
        }
        return constructor;
    }

    public static <T extends MapSerializable> T deserialize(Map<String, Object> map, Class<T> clazz) {
        Constructor<T> constructor = getConstructor(clazz);
        try {
            return constructor.newInstance(map);
        } catch (Exception e) {
            throw new RuntimeException("Could not deserialize " + clazz.getName(),e);
        }
    }

    public static <T extends MapSerializable> T deserialize(InputStream stream, Class<T> clazz) {
        return deserialize(YamlUtils.load(stream),clazz);
    }

    public static class Builder {
        private final Map<String,Object> map = new HashMap<>();
        public Builder put(String key, Object value) {
            map.put(key,value);
            return this;
        }
        public Map<String,Object> serialize() {
            return map;
        }
    }

}
