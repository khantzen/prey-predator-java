package fr.noether.preypredator.domain;

import java.util.List;

public class Territory {
    private final Coord coord;
    private int rabbitCount;
    private int foxCount;
    private int migratingFox;
    private int migratingRabbit;

    private Territory(Coord coord) {
        this.coord = coord;
    }

    static Territory at(Coord coord) {
        return new Territory(coord);
    }

    public Coord position() {
        return coord;
    }

    public void addRabbit() {
        rabbitCount += 1;
    }

    public int totalRabbit() {
        return rabbitCount;
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

    public void addRabbitMigration() {
        this.migratingRabbit++;
    }

    public void endFoxMigration() {
        foxCount = migratingFox;
        migratingFox = 0;
    }

    public void endRabbitMigration() {
        rabbitCount = migratingRabbit;
        migratingRabbit = 0;
    }

    public void removeRabbit() {
        this.rabbitCount = Math.max(this.rabbitCount - 1, 0);
    }

    public boolean isOccupied() {
        return this.rabbitCount != 0 || this.totalFox() != 0;
    }

    void startHunt() {
        for (int i = 0; i < totalFox(); i++) {
            removeRabbit();
        }
    }
}
