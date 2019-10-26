package fr.noether.preypredator.util;

import fr.noether.preypredator.domain.Coord;

import java.util.List;
import java.util.Random;

public class Randomizer implements RandomGenerator {
    private final Random random;

    public Randomizer(long seed) {
        this.random = new Random(seed);
    }

    @Override
    public int nextInt(int max) {
        return this.random.nextInt(max);
    }

    @Override
    public Coord from(List<Coord> collection) {
        int index = this.random.nextInt(collection.size());
        return collection.get(index);
    }
}
