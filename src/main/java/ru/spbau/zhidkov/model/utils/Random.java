package ru.spbau.zhidkov.model.utils;

/**
 * Class describing custom random generator
 */
public class Random {

    private final static java.util.Random random = new java.util.Random();

    public int rand(int l, int r) {
        int n = r - l + 1;
        return l + (random.nextInt() % n + n) % n;
    }
}
