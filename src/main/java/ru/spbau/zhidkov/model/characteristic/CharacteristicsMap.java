package ru.spbau.zhidkov.model.characteristic;

import java.util.HashMap;
import java.util.Map;

/**
 * Class wrapping <t>Map<CharacteristicName, T</t> and providing
 * basic methods
 *
 * @param <T> value
 */
public class CharacteristicsMap<T> {

    private final Map<CharacteristicName, T> map;

    public CharacteristicsMap(T initialAttack, T initialDefense, T initialHealth) {
        this.map = new HashMap<>();
        this.map.put(CharacteristicName.ATTACK, initialAttack);
        this.map.put(CharacteristicName.DEFENSE, initialDefense);
        this.map.put(CharacteristicName.HEALTH, initialHealth);
    }

    public void put(CharacteristicName characteristic, T value) {
        this.map.put(characteristic, value);
    }

    public T get(CharacteristicName characteristic) {
        return this.map.get(characteristic);
    }


}
