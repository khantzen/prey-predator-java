package fr.noether.preypredator.util;

import fr.noether.preypredator.domain.Coord;

import java.util.List;

public interface RandomGenerator {
    int nextInt(int max);

    Coord from(List<Coord> collection);
}
