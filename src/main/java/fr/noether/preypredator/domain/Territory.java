package fr.noether.preypredator.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public class Territory {
    public final Coord position;

    private List<Rabbit> rabbits;
    private List<Rabbit> migratingRabbit;

    private List<Fox> foxes;
    private List<Fox> migratingFoxes;

    private Territory(Coord coord) {
        this.position = coord;
        this.rabbits = new ArrayList<>();
        this.migratingRabbit = new ArrayList<>();
        this.foxes = new ArrayList<>();
        this.migratingFoxes = new ArrayList<>();
    }

    static Territory at(Coord coord) {
        return new Territory(coord);
    }


    public void addRabbit(Rabbit rabbit) {
        this.rabbits.add(rabbit);
    }

    public Rabbit removeRabbit() {
        return this.rabbits.remove(0);
    }

    public int totalRabbit() {
        return this.rabbits.size();
    }


    public void addFox(Fox fox) {
        this.foxes.add(fox);
    }

    public Fox removeFox() {
        return this.foxes.remove(0);
    }

    public int totalFox() {
        return this.foxes.size();
    }


    public List<Coord> adjacentCoord(int totalLine, int totalColumn) {
        return this.position.adjacent(totalLine, totalColumn);
    }

    public void addFoxToMigration(Fox fox) {
        this.migratingFoxes.add(fox);
    }

    public void addRabbitMigration(Rabbit rabbit) {
        this.migratingRabbit.add(rabbit);
    }

    public void endFoxMigration() {
        this.foxes = new ArrayList<>(this.migratingFoxes);
        migratingFoxes = Collections.emptyList();
    }

    public void endRabbitMigration() {
        this.rabbits = new ArrayList<>(this.migratingRabbit);
        this.migratingRabbit = Collections.emptyList();
    }

    public boolean isOccupied() {
        return this.rabbits.size() != 0 || this.totalFox() != 0;
    }

    void startHunt() {
        for (int i = 0; i < totalFox(); i++) {
            removeRabbit();
        }
    }

    public void startRabbitReproduction() {
        if (this.foxes.size() == 0) {
            Predicate<Specie> byAdultRabbit = Specie::canBreed;

            var adultRabbit = this.rabbits.stream()
                    .filter(byAdultRabbit)
                    .count();

            var rabbitCouple = adultRabbit / 2;

            for (int i = 0; i < rabbitCouple; i++) {
                this.rabbits.add(Rabbit.newBorn());
            }
        }
    }

    public void startFoxReproduction() {
        long foxCouple = countSpecieCouple();

        for (int i = 0; i < foxCouple; i++)
            this.foxes.add(Fox.newBorn());
    }

    private long countSpecieCouple() {
        Predicate<Specie> byFedFox = Specie::canBreed;

        var fedFox = this.foxes.stream()
                .filter(byFedFox)
                .count();

        return fedFox / 2;
    }
}
