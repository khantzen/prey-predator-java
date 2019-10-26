package fr.noether.preypredator.util;

import fr.noether.preypredator.domain.Coord;

import java.util.Collection;
import java.util.List;

public class MockParametrizedRandomGenerator implements RandomGenerator {
    private final int[] values;
    private int index;
    public MockParametrizedRandomGenerator(int... values) {
        this.values = values;
        this.index = 0;
    }

    @Override
    public int nextInt(int max) {
        var value = values[index];
        index++;
        return value;
    }

    @Override
    public Coord from(List<Coord> collection) {
        var value = collection.get(values[index]);
        index++;
        return value;
    }
}
