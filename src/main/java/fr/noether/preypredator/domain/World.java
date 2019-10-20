package fr.noether.preypredator.domain;

import java.sql.ShardingKeyBuilder;

public class World {
    public int size() {
        return 100;
    }

    public static class Builder {
        public Builder totalLine(int totalLine) {
            return new Builder();
        }

        public Builder totalColumn(int totalColumn) {
            return new Builder();
        }

        public World build() {
            return new World();
        }
    }
}
