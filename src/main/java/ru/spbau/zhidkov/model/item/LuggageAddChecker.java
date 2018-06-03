package ru.spbau.zhidkov.model.item;

/**
 * Class checking if addition to the luggage is possible
 */
public interface LuggageAddChecker {
    boolean checkIfPossibleToAdd(Luggage luggage, Item item);
}
