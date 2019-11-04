package fr.noether.preypredator.util;

import fr.noether.preypredator.domain.area.Coord;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

public class Randomizer implements RandomGenerator {
    private final Random random;

    public Randomizer() {
        this.random = new SecureRandom();
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
