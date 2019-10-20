package fr.noether.preypredator.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class World {
    private final int worldSize;
    private final List<Territory> territories;

    private World(Builder builder) {
        this.worldSize = builder.totalLine * builder.totalColumn;

        this.territories = new ArrayList<>();

        for (int line = 0; line < builder.totalLine; line++) {
            for (int column = 0; column < builder.totalColumn; column++) {
                Coord position = Coord.of(line, column);
                territories.add(Territory.at(position));
            }
        }

        for (int i = 0; i < builder.baseRabbitCount; i++) {
            Coord coord = builder.coordGenerator.next();
            territoryAt(coord).addRabbit();
        }

        for (int i = 0; i < builder.baseFoxCount; i++) {
            Coord coord = builder.coordGenerator.next();
            territoryAt(coord).addFox();
        }

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
