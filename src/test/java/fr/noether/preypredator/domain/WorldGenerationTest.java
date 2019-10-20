package fr.noether.preypredator.domain;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runners.Parameterized;

public class WorldGenerationTest {
    @Test
    public void world_should_is_a_dimensioned_grid() {
        assertWorldSize(10, 10, 100);
        assertWorldSize(15, 10, 150);
    }

    private void assertWorldSize(int line, int column, int expectedSize) {
        var world = new World.Builder()
                .totalLine(line)
                .totalColumn(column)
                .build();

        Assertions.assertThat(world.size()).isEqualTo(expectedSize);
    }

    @Test
    public void world_is_composed_of_territories() {
        var world = new World.Builder()
                .totalLine(10)
                .totalColumn(10)
                .build();

        var territory = world.findTerritoryAt(Coord.of(5, 5));
        Assertions.assertThat(territory).isNotNull();
        Assertions.assertThat(territory.position()).isEqualTo(Coord.of(5, 5));
    }
}
