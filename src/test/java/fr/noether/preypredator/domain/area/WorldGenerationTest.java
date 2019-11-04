package fr.noether.preypredator.domain.area;

import fr.noether.preypredator.domain.area.Coord;
import fr.noether.preypredator.domain.area.World;
import fr.noether.preypredator.util.CoordGenerator;
import fr.noether.preypredator.util.MockCoordGenerator;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class WorldGenerationTest {
    @Test
    public void world_should_be_a_dimensioned_grid() {
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
    public void world_should_be_able_to_give_a_territory_according_to_its_position() {
        var world = new World.Builder()
                .totalLine(10)
                .totalColumn(10)
                .build();

        var territoryL5C5 = world.territoryAt(Coord.of(5, 5));
        var territoryL6C7 = world.territoryAt(Coord.of(6, 7));

        Assertions.assertThat(territoryL5C5).isNotNull();

        Assertions.assertThat(territoryL5C5.position)
                .isNotEqualTo(Coord.of(5, 8));
        Assertions.assertThat(territoryL5C5.position)
                .isEqualTo(Coord.of(5, 5));
        Assertions.assertThat(territoryL6C7.position)
                .isEqualTo(Coord.of(6, 7));
    }

    @Test
    public void world_should_set_rabbits_following_the_coord_generator() {
        CoordGenerator mockedCoordGenerator =
                new MockCoordGenerator(
                        Coord.of(0,0),
                        Coord.of(2, 2),
                        Coord.of(0, 0)
                        );

        var world = new World.Builder()
                .totalLine(3)
                .totalColumn(3)
                .baseRabbitCount(3)
                .coordGenerator(mockedCoordGenerator)
                .build();

        var territoryL0C0 = world.territoryAt(Coord.of(0,0));
        var territoryL2C2 = world.territoryAt(Coord.of(2,2));

        Assertions.assertThat(territoryL0C0.totalRabbit()).isEqualTo(2);
        Assertions.assertThat(territoryL2C2.totalRabbit()).isEqualTo(1);
    }

    @Test
    public void world_should_set_foxes_following_the_coord_generator() {
        CoordGenerator mockedCoordGenerator =
                new MockCoordGenerator(
                        Coord.of(0,0),
                        Coord.of(2, 2),
                        Coord.of(0, 0)
                );

        var world = new World.Builder()
                .totalLine(3)
                .totalColumn(3)
                .baseRabbitCount(0)
                .baseFoxCount(3)
                .coordGenerator(mockedCoordGenerator)
                .build();

        var territoryL0C0 = world.territoryAt(Coord.of(0,0));
        var territoryL2C2 = world.territoryAt(Coord.of(2,2));

        Assertions.assertThat(territoryL0C0.totalFox()).isEqualTo(2);
        Assertions.assertThat(territoryL2C2.totalFox()).isEqualTo(1);
    }
}
