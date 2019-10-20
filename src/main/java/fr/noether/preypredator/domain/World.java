package fr.noether.preypredator.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class World {
    private final int worldSize;
    private final List<Territory> territories;

    private World(Builder builder) {
        this.worldSize = builder.totalLine * builder.totalColumn;
        this.territories = buildTerritories(builder.totalLine, builder.totalColumn);

        for (int i = 0; i < builder.baseRabbitCount; i++) {
            Coord coord = builder.coordGenerator.next();
            territoryAt(coord).addRabbit();
        }

        for (int i = 0; i < builder.baseFoxCount; i++) {
            Coord coord = builder.coordGenerator.next();
            territoryAt(coord).addFox();
        }


    }

    private static List<Territory>  buildTerritories(int totalLine, int totalColumn) {
        return IntStream.range(0, totalLine)
                .mapToObj(line -> lineTerritories(totalColumn, line))
                .reduce(new ArrayList<>(), (l1, l2) -> { l1.addAll(l2); return l1; });
    }

    private static List<Territory> lineTerritories(int totalColumn, int line) {
        return IntStream.range(0, totalColumn)
                .mapToObj(column -> Coord.of(line, column))
                .map(Territory::at)
                .collect(Collectors.toList());
    }

    public int size() {
        return this.worldSize;
    }

    public Territory territoryAt(Coord position) {
        Predicate<Territory> byWantedPosition = t -> t.position().equals(position);

        return this.territories.stream()
                .filter(byWantedPosition)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
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
