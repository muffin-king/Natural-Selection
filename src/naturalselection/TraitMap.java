package naturalselection;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class TraitMap {
    private final Map<Trait, Double> map;
    private static final Random RANDOM = new Random();

    public TraitMap() {
        map = new HashMap<>();
        map.put(Trait.MAX_SIZE, Trait.DEFAULT_MAX_SIZE);
        map.put(Trait.SPEED, Trait.DEFAULT_SPEED);
        map.put(Trait.VIRILITY, Trait.DEFAULT_VIRILITY);
    }

    public Double getTrait(Trait trait) {
        return map.get(trait);
    }

    public TraitMap mutate() {
        TraitMap newMap = new TraitMap();

        do {
            newMap.map.put(Trait.MAX_SIZE, map.get(Trait.MAX_SIZE) + RANDOM.nextDouble(Trait.MAX_SIZE_VARIANCE*2)-Trait.MAX_SIZE_VARIANCE);
        } while(newMap.map.get(Trait.MAX_SIZE) < 1);

        do {
            newMap.map.put(Trait.SPEED, map.get(Trait.SPEED) + RANDOM.nextDouble(Trait.SPEED_VARIANCE*2)-Trait.SPEED_VARIANCE);
        } while(newMap.map.get(Trait.SPEED) <= 0);


        do {
            newMap.map.put(Trait.VIRILITY, map.get(Trait.VIRILITY) + RANDOM.nextDouble(Trait.VIRILITY_VARIANCE*2)-Trait.VIRILITY_VARIANCE);
        } while(newMap.map.get(Trait.VIRILITY) <= 0);

        return newMap;
    }
}
