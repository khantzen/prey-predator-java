package fr.noether.preypredator.domain;

import java.util.List;

public class Territory {
    private final Coord coord;
    private int rabbitCount;
    private int foxCount;

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
}
