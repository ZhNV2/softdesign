package ru.spbau.zhidkov.model.item;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Luggage {

    private final List<Item> items = new ArrayList<>();
    protected final List<LuggageAddChecker> luggageAddCheckers = new ArrayList<>();

    public Luggage(int limit) {
        luggageAddCheckers.add(new LuggageSizeChecker(limit));
    }

    public boolean tryAdd(Item item) {
        for (LuggageAddChecker luggageAddChecker : luggageAddCheckers) {
            if (!luggageAddChecker.checkIfPossibleToAdd(this, item)) {
                return false;
            }
        }
        items.add(item);
        return true;
    }

    public Optional<Item> get(int id) {
        return items.stream().filter(item -> item.getId() == id).findAny();
    }

    public boolean delete(int id) {
        return items.removeIf(item -> item.getId() == id);
    }

    public List<Item> items() {
        return items;
    }


}
