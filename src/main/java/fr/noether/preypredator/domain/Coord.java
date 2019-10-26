package fr.noether.preypredator.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Coord {
    private final int line;
    private final int column;

    public Coord(int line, int column) {
        this.line = line;
        this.column = column;
    }

    public static Coord of(int line, int column) {
        return new Coord(line, column);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coord coord = (Coord) o;
        return line == coord.line &&
                column == coord.column;
    }

    public List<Coord> adjacent(int totalLine, int totalColumn) {
        var adjacentCoords = new ArrayList<Coord>();
        if (bottomCoordIsValid(totalLine)) {
            adjacentCoords.add(bottomCoord());
        }

        if (topCoordIsValid()) {
            adjacentCoords.add(topCoord());
        }

        if (rightCoordIsValid(totalColumn)) {
            adjacentCoords.add(rightCoord());
        }

        if (leftCoordIsValid()) {
            adjacentCoords.add(leftCoord());
        }

        return adjacentCoords;
    }

    private boolean bottomCoordIsValid(int totalLine) {
        return this.line + 1 < totalLine;
    }

    private Coord bottomCoord() {
        return Coord.of(this.line + 1, this.column);
    }

    private boolean topCoordIsValid() {
        return this.line - 1 >= 0;
    }

    private Coord topCoord() {
        return Coord.of(this.line - 1, this.column);
    }

    private boolean leftCoordIsValid() {
        return this.column - 1 >= 0;
    }

    private Coord leftCoord() {
        return Coord.of(this.line, this.column - 1);
    }

    private boolean rightCoordIsValid(int totalColumn) {
        return this.column + 1 < totalColumn;
    }

    private Coord rightCoord() {
        return Coord.of(this.line, this.column + 1);
    }
}
