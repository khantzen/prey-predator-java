package fr.noether.preypredator.domain;

import fr.noether.preypredator.util.CoordGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class World {
    private final int totalLine;
    private final int totalColumn;

    private final Migration foxMigration;
    private final Migration rabbitMigration;

    private final List<Territory> territories;

    private World(Builder builder) {
        this.totalLine = builder.totalLine;
        this.totalColumn = builder.totalColumn;
        this.foxMigration = builder.foxMigration;
        this.rabbitMigration = builder.rabbitMigration;
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

    public void migrateFoxes() {
        Predicate<? super Territory> byFox = t -> t.totalFox() != 0;
        List<Territory> foxTerritories = territories.stream()
                .filter(byFox)
                .collect(toList());

        for (var territory : foxTerritories) {
            var adjacentCoords = territory.adjacentCoord(totalLine, totalColumn);
            migrateFoxesFrom(territory, adjacentCoords);
        }

        territories.forEach(Territory::endMigration);
    }

    public void migrateRabbits() {
        Predicate<? super Territory> byRabbit = t -> t.totalRabbit() != 0;

        List<Territory> rabbitTerritories = territories.stream()
                .filter(byRabbit)
                .collect(toList());

        for(var territory : rabbitTerritories) {
            var adjacentPosition = territory.adjacentCoord(totalLine, totalColumn);
            migrateRabbitFrom(territory, adjacentPosition);
        }
    }

    private void migrateRabbitFrom(Territory territory, List<Coord> adjacentPosition) {
        while (territory.totalRabbit() != 0) {
            territory.removeRabbit();
            var destination = rabbitMigration
                    .nextCoord(adjacentPosition, territory.position(), territories);
            territoryAt(destination).addRabbit();
        }
    }

    public Territory territoryAt(Coord position) {
        Predicate<Territory> byWantedPosition = t -> t.position().equals(position);

        return this.territories.stream()
                .filter(byWantedPosition)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    private void migrateFoxesFrom(Territory territory, List<Coord> adjacentCoords) {
        while (territory.totalFox() != 0) {
            territory.removeFox();
            var destination = foxMigration
                    .nextCoord(adjacentCoords, territory.position(), territories);

            territoryAt(destination).addFoxToMigration();
        }
    }

    public static class Builder {
        private int totalLine;
        private int totalColumn;
        private int baseRabbitCount;
        private int baseFoxCount;
        private CoordGenerator coordGenerator;
        private Migration foxMigration;
        private Migration rabbitMigration;

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

        public Builder foxMigration(Migration foxMigration) {
            this.foxMigration = foxMigration;
            return this;
        }

        public Builder rabbitMigration(Migration rabbitMigration) {
            this.rabbitMigration = rabbitMigration;
            return this;
        }

        public World build() {
            return new World(this);
        }
    }
}
