package fr.noether.preypredator.domain;

public class Territory {
    private final Coord coord;

    private Territory(Coord coord) {
        this.coord = coord;
    }

    public static Territory at(Coord coord) {
        return new Territory(coord);
    }

    public Coord position() {
        return coord;
    }
}
