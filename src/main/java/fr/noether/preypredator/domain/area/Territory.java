package fr.noether.preypredator.domain.area;

import fr.noether.preypredator.domain.life.Reproduction;
import fr.noether.preypredator.domain.specie.Fox;
import fr.noether.preypredator.domain.specie.Rabbit;

import java.util.ArrayList;
import java.util.List;

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

    public static Territory at(Coord coord) {
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


    List<Coord> adjacentCoord(int totalLine, int totalColumn) {
        return this.position.adjacent(totalLine, totalColumn);
    }

    void addFoxToMigration(Fox fox) {
        this.migratingFoxes.add(fox);
    }

    void addRabbitMigration(Rabbit rabbit) {
        this.migratingRabbit.add(rabbit);
    }

    void endFoxMigration() {
        this.foxes = new ArrayList<>(this.migratingFoxes);
        migratingFoxes = new ArrayList<>();
    }

    void endRabbitMigration() {
        this.rabbits = new ArrayList<>(this.migratingRabbit);
        this.migratingRabbit = new ArrayList<>();
    }

    boolean isOccupied() {
        return this.rabbits.size() != 0 || this.totalFox() != 0;
    }

    public void startHunt() {
        int rabbitToRemove = Math.min(totalFox(), totalRabbit());
        for (int i = 0; i < rabbitToRemove; i++) {
            removeRabbit();
            Fox fox = removeFox();
            addFox(fox.feed());
        }
    }

    public void startRabbitReproduction() {
        if (this.foxes.size() == 0) {
            var newBornRabbitCount =
                    Reproduction.countNewBorn(new ArrayList<>(this.rabbits));

            for (int i = 0; i < newBornRabbitCount; i++) {
                this.rabbits.add(Rabbit.newBorn());
            }
        }
    }

    public void startFoxReproduction() {
        long newBornFoxCount =
                Reproduction.countNewBorn(new ArrayList<>(this.foxes));

        for (int i = 0; i < newBornFoxCount; i++)
            this.foxes.add(Fox.newBorn());
    }

    boolean containsRabbits() {
        return totalRabbit() != 0;
    }

    boolean containsFoxes() {
        return totalFox() != 0;
    }
}
