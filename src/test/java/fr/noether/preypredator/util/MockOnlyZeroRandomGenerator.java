package fr.noether.preypredator.util;

import fr.noether.preypredator.domain.Coord;

import java.util.List;

public class MockOnlyZeroRandomGenerator implements RandomGenerator {
    @Override
    public int nextInt(int max) {
        return 0;
    }

    @Override
    public Coord from(List<Coord> collection) {
        return collection.get(0);
    }
}
