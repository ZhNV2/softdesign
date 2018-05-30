package ru.spbau.zhidkov.model.item;

import com.oracle.tools.packager.Log;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Luggage {

    private final static Logger LOG = LogManager.getLogger(Luggage.class);

    private final List<Item> items = new ArrayList<>();
    protected final List<LuggageAddChecker> luggageAddCheckers = new ArrayList<>();

    public Luggage(int limit) {
        luggageAddCheckers.add(new LuggageSizeChecker(limit));
    }

    public boolean tryAdd(Item item) {
        for (LuggageAddChecker luggageAddChecker : luggageAddCheckers) {
            if (!luggageAddChecker.checkIfPossibleToAdd(this, item)) {
                Log.debug("add cancel due to addChecker");
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
