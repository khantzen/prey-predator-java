package fr.noether.preypredator.domain;

public class World {
    private final int worldSize;

    private World(Builder builder) {
        this.worldSize = builder.totalLine * builder.totalColumn;
    }

    public int size() {
        return this.worldSize;
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
