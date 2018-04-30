package ru.spbau.zhidkov.environment;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of <t>Environment</t> based on <t>HashMap</t>
 *
 * @param <K> key type
 * @param <V> value type
 */
public class EnvironmentImpl<K, V> implements Environment<K, V> {

    private final Map<K, V> map = new HashMap<>();

    @Override
    public void set(K key, V value) {
        map.put(key, value);
    }

    @Override
    public V getOrDefault(K key, V defaultValue) {
        return map.getOrDefault(key, defaultValue);
    }
}
