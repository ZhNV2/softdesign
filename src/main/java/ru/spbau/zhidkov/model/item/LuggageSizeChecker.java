package ru.spbau.zhidkov.model.item;

/**
 * Class checking whether size of luggage will exceed limit after
 * addition or not
 */
public class LuggageSizeChecker implements LuggageAddChecker {

    private final int limit;

    public LuggageSizeChecker(int limit) {
        this.limit = limit;
    }

    @Override
    public boolean checkIfPossibleToAdd(Luggage luggage, Item item) {
        return luggage.items().size() + 1 <= limit;
    }

}
