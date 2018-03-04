package ru.spbau.zhidkov.environment;

/**
 * Interface for classes producing storing pairs (key, value) functionality
 * @param <K> key type
 * @param <V> value type
 */
public interface Environment<K, V>  {

    /**
     * sets key variable to value
     * @param key key
     * @param value value
     */
    void set(K key, V value);

    /**
     * returns value associated with key.
     * returns {@code null} if there is no such value
     * @param key
     * @return value associated with key
     */
    V get(K key);

}
