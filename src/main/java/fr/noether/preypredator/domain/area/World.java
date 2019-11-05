package fr.noether.preypredator.domain.area;

import fr.noether.preypredator.domain.life.Migration;
import fr.noether.preypredator.domain.specie.Fox;
import fr.noether.preypredator.domain.specie.Rabbit;
import fr.noether.preypredator.util.CoordGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class World {
    private final int totalLine;
    private final int totalColumn;

    private final Migration foxMigration;
    private final Migration rabbitMigration;

    private final List<Territory> territories;

    private final CoordGenerator coordGenerator;

    private World(Builder builder) {
        this.totalLine = builder.totalLine;
        this.totalColumn = builder.totalColumn;
        this.foxMigration = builder.foxMigration;
        this.rabbitMigration = builder.rabbitMigration;
        this.territories = buildTerritories(builder.totalLine, builder.totalColumn);
        this.coordGenerator = builder.coordGenerator;
        populateWorldWith(builder.baseRabbitCount, this.addRabbit());
        populateWorldWith(builder.baseFoxCount, this.addFox());
    }

    private static List<Territory> buildTerritories(int totalLine, int totalColumn) {
        List<Territory> territories = new ArrayList<>();
        for (int line = 0; line < totalLine; line++) {
            for (int column = 0; column < totalColumn; column++) {
                Coord position = Coord.of(line, column);
                territories.add(Territory.at(position));
            }
        }
        return territories;
    }

    private void populateWorldWith(
            int populationCount,
            Consumer<Coord> populateMethod
    ) {
        IntStream.range(0, populationCount)
                .mapToObj(i -> this.coordGenerator.next())
                .forEach(populateMethod);
    }

    private Consumer<Coord> addRabbit() {
        return coord -> territoryAt(coord).addRabbit(Rabbit.newBorn());
    }

    private Consumer<Coord> addFox() {
        return coord -> territoryAt(coord).addFox(Fox.newBorn());
    }

    int size() {
        return territories.size();
    }

    public void launchCycle() {
        migrateFoxes();
        migrateRabbits();
        startHunt();
        launchSpecieReproduction(Territory::containsRabbits, Territory::startRabbitReproduction);
        launchSpecieReproduction(Territory::containFoxes, Territory::startFoxReproduction);
    }

    public void startHunt() {
        var occupiedTerritories = this.filterTerritories(Territory::isOccupied);
        occupiedTerritories.forEach(Territory::startHunt);
    }

    public void migrateFoxes() {
        migrateSpecie(Territory::containFoxes, this::migrateFoxesFrom);
        territories.forEach(Territory::endFoxMigration);
    }

    public void migrateRabbits() {
        migrateSpecie(Territory::containsRabbits, this::migrateRabbitFrom);
        territories.forEach(Territory::endRabbitMigration);
    }

    private void launchSpecieReproduction(
            Predicate<Territory> specieFiltering,
            Consumer<Territory> launchReproduction
    ) {
        filterTerritories(specieFiltering)
                .forEach(launchReproduction);
    }

    private void migrateSpecie(
            Predicate<? super Territory> bySpecie,
            Consumer<Territory> migrateSpeciesFrom
    ) {
        filterTerritories(bySpecie)
                .forEach(migrateSpeciesFrom);
    }

    private void migrateFoxesFrom(Territory territory) {
        var adjacentCoords = territory.adjacentCoord(totalLine, totalColumn);
        while (territory.containFoxes()) {
            Fox fox = territory.removeFox();

            var destination = this.foxMigration
                    .nextCoord(
                            adjacentCoords,
                            territory.position,
                            territories
                    );

            if (!fox.shouldDie()) {
                territoryAt(destination)
                        .addFoxToMigration(
                                fox.incrementFoxAge()
                        );
            }
        }
    }

    private void migrateRabbitFrom(Territory territory) {
        var adjacentPosition = territory.adjacentCoord(totalLine, totalColumn);
        while (territory.containsRabbits()) {
            Rabbit rabbit = territory.removeRabbit();

            var destination = this.rabbitMigration
                    .nextCoord(
                            adjacentPosition,
                            territory.position,
                            territories
                    );

            territoryAt(destination).addRabbitMigration(rabbit.incrementAge());
        }
    }

    public Territory territoryAt(Coord wantedPosition) {
        Predicate<Territory> byWantedPosition = t -> t.position.equals(wantedPosition);
        return filterTerritories(byWantedPosition)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public long totalRabbitPopulation() {
        return totalSpeciePopulation(
                Territory::containsRabbits,
                Territory::totalRabbit
        );
    }

    public long totalFoxPopulation() {
        return totalSpeciePopulation(
                Territory::containFoxes,
                Territory::totalFox
        );
    }

    private long totalSpeciePopulation(
            Predicate<Territory> specieFilter,
            Function<Territory, Integer> specieCount
    ) {
        return filterTerritories(specieFilter)
                .map(specieCount)
                .map(Integer::longValue)
                .reduce(0L, Long::sum);
    }

    public void spawnDivinelyANewBornRabbit() {
        Coord position = this.coordGenerator.next();
        territoryAt(position).addRabbit(Rabbit.newBorn());
    }

    public void spawnDivinelyANewBornFox() {
        Coord position = this.coordGenerator.next();
        territoryAt(position).addFox(Fox.newBorn());
    }

    private Stream<Territory> filterTerritories(
            Predicate<? super Territory> byPredicate
    ) {
        return territories.stream()
                .filter(byPredicate);
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
