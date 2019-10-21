package fr.noether.preypredator.domain;

import fr.noether.preypredator.util.CoordGenerator;
import fr.noether.preypredator.util.MockCoordGenerator;
import fr.noether.preypredator.util.MockRandomGenerator;
import fr.noether.preypredator.util.RandomGenerator;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class MigrationTest {

    @Test
    public void fox_should_move_from_one_territory_to_one_adjacent_territory() {
        CoordGenerator mockedCoordGenerator =
                new MockCoordGenerator(
                        Coord.of(0, 0),
                        Coord.of(0, 2)
                );

        RandomGenerator mockedRandomGenerator =
                new MockRandomGenerator(0, 0);

        World world = new World.Builder()
                .totalLine(1)
                .totalColumn(3)
                .baseRabbitCount(0)
                .baseFoxCount(2)
                .coordGenerator(mockedCoordGenerator)
                .randomGenerator(mockedRandomGenerator)
                .build();

        world.migrateFoxes();

        Territory territoryL0C1 = world.territoryAt(Coord.of(0, 1));
        Territory territoryL0C0 = world.territoryAt(Coord.of(0, 0));

        Assertions.assertThat(territoryL0C1.totalFox()).isEqualTo(2);
        Assertions.assertThat(territoryL0C0.totalFox()).isEqualTo(0);
    }

    @Test
    public void multiple_fox_should_move_from_their_territory_to_one_adjacent_territory() {
        CoordGenerator mockedCoordGenerator =
                new MockCoordGenerator(
                        Coord.of(0, 0),
                        Coord.of(2, 4),
                        Coord.of(2, 2),
                        Coord.of(3, 3)
                );

        RandomGenerator mockedRandomGenerator =
                new MockRandomGenerator(0, 1, 2, 3);

        World world = new World.Builder()
                .totalLine(5)
                .totalColumn(5)
                .baseRabbitCount(0)
                .baseFoxCount(4)
                .coordGenerator(mockedCoordGenerator)
                .randomGenerator(mockedRandomGenerator)
                .build();

        world.migrateFoxes();
        var territoryL1C0 = world.territoryAt(Coord.of(1, 0));

        Assertions.assertThat(territoryL1C0.totalFox()).isEqualTo(1);
    }
}
