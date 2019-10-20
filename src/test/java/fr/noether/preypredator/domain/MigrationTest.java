package fr.noether.preypredator.domain;

import fr.noether.preypredator.util.MockCoordGenerator;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class MigrationTest {

/*    @Test
    public void fox_should_move_from_one_territory_to_one_adjacent_territory() {
        CoordGenerator mockedCoordGenerator =
                new MockCoordGenerator(
                        Coord.of(0, 0),
                        Coord.of(0, 2)
                );

        World world = new World.Builder()
                .totalLine(1)
                .totalColumn(3)
                .baseRabbitCount(0)
                .baseFoxCount(2)
                .coordGenerator(mockedCoordGenerator)
                .build();

        world.migrateFoxes();

        Territory territoryL0C1 = world.territoryAt(Coord.of(0, 1));
        Assertions.assertThat(territoryL0C1.totalFox()).isEqualTo(2);
    }*/
}
