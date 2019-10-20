package fr.noether.preypredator.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class World {
    private final int worldSize;
    private final List<Territory> territories;

    private World(Builder builder) {
        this.worldSize = builder.totalLine * builder.totalColumn;

        this.territories = new ArrayList<>();

        for (int line = 0; line < builder.totalLine; line++) {
            for (int column = 0; column < builder.totalColumn; column++) {
                Coord position = Coord.of(line, column);
                territories.add(Territory.at(position));
            }
        }
    }

    public int size() {
        return this.worldSize;
    }

    public Territory findTerritoryAt(Coord position) {
        Predicate<Territory> byWantedPosition = t -> t.position().equals(position);

        return this.territories.stream()
                .filter(byWantedPosition)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public static class Builder {
        private int totalLine;
        private int totalColumn;

        public Builder totalLine(int totalLine) {
            this.totalLine = totalLine;
            return this;
        }

        public Builder totalColumn(int totalColumn) {
            this.totalColumn = totalColumn;
            return this;
        }

        public World build() {
            return new World(this);
        }
    }
}
