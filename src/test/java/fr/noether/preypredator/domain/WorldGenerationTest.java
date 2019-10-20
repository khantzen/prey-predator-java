package fr.noether.preypredator.domain;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class WorldGenerationTest {
    @Test
    public void world_should_be_composed_of_a_dimensioned_grid_of_territories() {
        var world = new World.Builder()
                .totalLine(10)
                .totalColumn(10)
                .build();

        Assertions.assertThat(world.size()).isEqualTo(100);
    }
}
