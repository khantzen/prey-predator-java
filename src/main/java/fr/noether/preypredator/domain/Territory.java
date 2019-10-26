package fr.noether.preypredator.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public class Territory {
    private final Coord coord;
    private int rabbitCount;
    private int foxCount;
    private int migratingFox;
    private List<Rabbit> migratingRabbit;
    private List<Rabbit> rabbits;

    private Territory(Coord coord) {
        this.coord = coord;
        this.rabbits = new ArrayList<>();
        this.migratingRabbit = new ArrayList<>();
    }

    static Territory at(Coord coord) {
        return new Territory(coord);
    }

    public Coord position() {
        return coord;
    }

    public void addRabbit(Rabbit rabbit) {
        this.rabbits.add(rabbit);
    }

    public int totalRabbit() {
        return this.rabbits.size();
    }

    public void addFox() {
        foxCount += 1;
    }

    public void removeFox() {
        foxCount--;
    }

    public int totalFox() {
        return foxCount;
    }

    public List<Coord> adjacentCoord(int totalLine, int totalColumn) {
        return this.coord.adjacent(totalLine, totalColumn);
    }

    public void addFoxToMigration() {
        migratingFox++;
    }

    public void addRabbitMigration(Rabbit rabbit) {
        this.migratingRabbit.add(rabbit);
    }

    public void endFoxMigration() {
        foxCount = migratingFox;
        migratingFox = 0;
    }

    public void endRabbitMigration() {
        this.rabbits = new ArrayList<>(this.migratingRabbit);
        this.migratingRabbit = Collections.emptyList();
    }

    public Rabbit removeRabbit() {
        return this.rabbits.remove(0);
    }

    public boolean isOccupied() {
        return this.rabbitCount != 0 || this.totalFox() != 0;
    }

    void startHunt() {
        for (int i = 0; i < totalFox(); i++) {
            removeRabbit();
        }
    }

    public void startRabbitReproduction() {
        if (foxCount == 0) {
            Predicate<Rabbit> byAdultRabbit = Rabbit::canBreed;

            var adultRabbit = this.rabbits.stream()
                    .filter(byAdultRabbit)
                    .count();

            var rabbitCouple = adultRabbit/2;

            for (int i = 0; i < rabbitCouple; i++) {
                this.rabbits.add(Rabbit.newBorn());
            }
        }
    }

    public void startFoxReproduction() {
        if (this.foxCount == 2 || this.foxCount == 3) {
            this.foxCount++;
        } else {
            this.foxCount = 6;
        }
    }
}
