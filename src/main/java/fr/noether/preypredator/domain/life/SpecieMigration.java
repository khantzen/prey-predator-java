package fr.noether.preypredator.domain.life;

import fr.noether.preypredator.domain.area.Coord;
import fr.noether.preypredator.domain.area.Territory;
import fr.noether.preypredator.domain.life.Migration;
import fr.noether.preypredator.util.RandomGenerator;

import java.util.List;

public class SpecieMigration implements Migration {
    private final RandomGenerator randomGenerator;

    public SpecieMigration(RandomGenerator randomGenerator) {
        this.randomGenerator = randomGenerator;
    }

    @Override
    public Coord nextCoord(
            List<Coord> adjacentCoord,
            Coord currentCoord,
            List<Territory> territories
    ) {
        return this.randomGenerator.from(adjacentCoord);
    }
}
