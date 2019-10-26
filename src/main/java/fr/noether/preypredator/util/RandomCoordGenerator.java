package fr.noether.preypredator.util;

import fr.noether.preypredator.domain.Coord;

public class RandomCoordGenerator implements CoordGenerator {
    private final RandomGenerator randomGenerator;

    private final int maxLine;
    private final int maxColumn;

    public RandomCoordGenerator(
            int maxLine,
            int maxColumn,
            RandomGenerator randomGenerator
    ) {
        this.maxLine = maxLine;
        this.maxColumn = maxColumn;
        this.randomGenerator = randomGenerator;
    }

    @Override
    public Coord next() {
        int line = this.randomGenerator.nextInt(this.maxLine);
        int column = this.randomGenerator.nextInt(this.maxColumn);

        return Coord.of(line, column);
    }
}
