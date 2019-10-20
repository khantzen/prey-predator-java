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

    @Override
    public int hashCode() {
        return Objects.hash(line, column);
    }

    public List<Coord> ajacents() {
        return new ArrayList<>();
    }
}
