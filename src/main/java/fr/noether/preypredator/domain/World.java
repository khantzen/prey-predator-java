package fr.noether.preypredator.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.IntStream;

public class World {
    private final int totalLine;
    private final int totalColumn;
    private final List<Territory> territories;

    private World(Builder builder) {
        this.totalLine = builder.totalLine;
        this.totalColumn = builder.totalColumn;
        this.territories = buildTerritories(builder.totalLine, builder.totalColumn);
        populateWorldWith(builder.baseRabbitCount, builder.coordGenerator, this.addRabbit());
        populateWorldWith(builder.baseFoxCount, builder.coordGenerator, this.addFox());
    }

    private static List<Territory> buildTerritories(int totalLine, int totalColumn) {
        List<Territory> territories = new ArrayList<>();
        for (int i = 0; i < totalLine; i++) {
            for (int j = 0; j < totalColumn; j++) {
                Coord position = Coord.of(i, j);
                territories.add(Territory.at(position));
            }
        }
        return territories;
    }

    private void populateWorldWith(
            int populationCount,
            CoordGenerator coordGenerator,
            Consumer<Coord> populateMethod
    ) {
        IntStream.range(0, populationCount)
                .mapToObj(i -> coordGenerator.next())
                .forEach(populateMethod);
    }

    private Consumer<Coord> addRabbit() {
        return coord -> territoryAt(coord).addRabbit();
    }

    private Consumer<Coord> addFox() {
        return coord -> territoryAt(coord).addFox();
    }

    public int size() {
        return totalLine * totalColumn;
    }

    public Territory territoryAt(Coord position) {
        Predicate<Territory> byWantedPosition = t -> t.position().equals(position);

        return this.territories.stream()
                .filter(byWantedPosition)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public void migrateFoxes() {

    }

    public static class Builder {
        private int totalLine;
        private int totalColumn;
        private int baseRabbitCount;
        private CoordGenerator coordGenerator;
        private int baseFoxCount;

        public Builder totalLine(int totalLine) {
            this.totalLine = totalLine;
            return this;
        }

        public Builder totalColumn(int totalColumn) {
            this.totalColumn = totalColumn;
            return this;
        }

        public Builder baseRabbitCount(int baseRabbitCount) {
            this.baseRabbitCount = baseRabbitCount;
            return this;
        }

        public Builder baseFoxCount(int baseFoxCount) {
            this.baseFoxCount = baseFoxCount;
            return this;
        }

        public Builder coordGenerator(CoordGenerator coordGenerator) {
            this.coordGenerator = coordGenerator;
            return this;
        }

        public World build() {
            return new World(this);
        }
    }
}
