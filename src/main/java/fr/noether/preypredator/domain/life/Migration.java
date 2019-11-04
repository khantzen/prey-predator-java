package fr.noether.preypredator.domain.life;

import fr.noether.preypredator.domain.area.Coord;
import fr.noether.preypredator.domain.area.Territory;

import java.util.List;

public interface Migration {
    Coord nextCoord(
            List<Coord> adjacentCoord,
            Coord currentCoord,
            List<Territory> territories
    );
}
