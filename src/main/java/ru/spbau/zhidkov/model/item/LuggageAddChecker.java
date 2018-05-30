package ru.spbau.zhidkov.model.item;

public interface LuggageAddChecker {
    boolean checkIfPossibleToAdd(Luggage luggage, Item item);
}
