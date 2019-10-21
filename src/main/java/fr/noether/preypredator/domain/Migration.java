package fr.noether.preypredator.domain;

import java.util.List;

public interface Migration {
    Coord nextCoord(
            List<Coord> adjacentCoord,
            Coord currentCoord,
            List<Territory> territories
    );
}
