package fr.noether.preypredator.domain;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runners.Parameterized;

public class WorldGenerationTest {
    @Test
    public void world_should_be_composed_of_a_dimensioned_grid_of_territories() {
        assertWorldSize(10, 10, 100);
    }

    private void assertWorldSize(int line, int column, int expectedSize) {
        var world = new World.Builder()
                .totalLine(line)
                .totalColumn(column)
                .build();

        Assertions.assertThat(world.size()).isEqualTo(expectedSize);
    }
}
