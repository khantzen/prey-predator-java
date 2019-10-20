package fr.noether.preypredator.domain;

public class Territory {
    private final Coord coord;
    private int rabbitCount;

    private Territory(Coord coord) {
        this.coord = coord;
    }

    static Territory at(Coord coord) {
        return new Territory(coord);
    }

    void addRabbit() {
        rabbitCount += 1;
    }

    public Coord position() {
        return coord;
    }

    public int totalRabbit() {
        return rabbitCount;
    }
}
