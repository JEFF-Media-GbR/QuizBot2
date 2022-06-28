package com.jeff_media.quizbot.data;

import java.util.*;

public class EqualDistributedList<T> extends ArrayList<T> implements Comparator<T>{

    private final Map<Integer,Integer> frequencies = new HashMap<>();
    private final IMemoryBuffer<T> buffer;

    public EqualDistributedList(Collection<T> collection) {
        super(collection);
        buffer = new ImpairedShortTermMemoryBuffer<>(Math.max(size() / 4,1));
    }

    @SafeVarargs
    public EqualDistributedList(T... items) {
        this(Arrays.asList(items));
    }

    public int getFrequency(T item) {
        return frequencies.getOrDefault(item.hashCode(), 0);
    }

    public void raiseFrequency(T item) {
        frequencies.put(item.hashCode(), getFrequency(item) + 1);
    }

    public T getNext() {
        if (isEmpty()) {
            return null;
        }
        shuffle();
        T item = get(0);
        raiseFrequency(item);
        return item;
    }

    private void shuffle() {
        Collections.shuffle(this);
        sort(this);
    }

    @Override
    public int compare(T o1, T o2) {
        int offset = Integer.compare(getFrequency(o1), getFrequency(o2));
        if(offset == 0) {
            offset = Boolean.compare(buffer.remembers(o1), buffer.remembers(o2));
        }
        return offset;
    }
}
