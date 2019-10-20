package fr.noether.preypredator.util;

import fr.noether.preypredator.domain.Coord;

public class MockCoordGenerator implements CoordGenerator {
    private final Coord[] coords;
    private int index;

    public MockCoordGenerator(Coord... coords) {
        this.coords = coords;
        this.index = 0;
    }

    @Override
    public Coord next() {
        Coord coord = this.coords[this.index];
        this.index++;
        return coord;
    }
}
