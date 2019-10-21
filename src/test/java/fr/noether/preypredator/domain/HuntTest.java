package fr.noether.preypredator.domain;

import fr.noether.preypredator.util.MockCoordGenerator;
import fr.noether.preypredator.util.MockRandomGenerator;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class HuntTest {

    @Test
    public void hungry_fox_should_eat_a_rabbit_that_end_on_the_same_case_as_him() {
        var mockedCoordGenerator =
                new MockCoordGenerator(
                        Coord.of(0, 0),
                        Coord.of(0, 2)
                );

        Migration migration = new SpecieMigration(new MockRandomGenerator(0,0));
        var world = new World.Builder()
                .baseFoxCount(1)
                .baseRabbitCount(1)
                .totalLine(1)
                .totalColumn(3)
                .coordGenerator(mockedCoordGenerator)
                .foxMigration(migration)
                .rabbitMigration(migration)
                .coordGenerator(mockedCoordGenerator)
                .build();

        world.lifeHappen();

        var territoryL0C1 = world.territoryAt(Coord.of(0, 1));
        Assertions.assertThat(territoryL0C1.totalRabbit()).isEqualTo(0);
        Assertions.assertThat(territoryL0C1.totalFox()).isEqualTo(1);
    }
}
