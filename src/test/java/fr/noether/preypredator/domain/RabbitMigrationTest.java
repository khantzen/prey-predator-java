package fr.noether.preypredator.domain;

import fr.noether.preypredator.util.CoordGenerator;
import fr.noether.preypredator.util.MockCoordGenerator;
import fr.noether.preypredator.util.MockRandomGenerator;
import fr.noether.preypredator.util.RandomGenerator;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class RabbitMigrationTest {

    @Test
    public void one_rabbit_should_migrate_from_one_territory_to_one_adjacent_territory() {
        CoordGenerator mockedCoordGenerator =
                new MockCoordGenerator(
                        Coord.of(0, 0)
                );

        RandomGenerator mockedRandomGenerator =
                new MockRandomGenerator(0, 0);

        World world = new World.Builder()
                .totalLine(1)
                .totalColumn(3)
                .baseRabbitCount(1)
                .baseFoxCount(0)
                .coordGenerator(mockedCoordGenerator)
                .foxMigration(new SpecieMigration(mockedRandomGenerator))
                .rabbitMigration(new SpecieMigration(mockedRandomGenerator))
                .build();

        world.migrateRabbit();

        Territory territoryL0C1 = world.territoryAt(Coord.of(0, 1));
        Territory territoryL0C0 = world.territoryAt(Coord.of(0, 0));

        Assertions.assertThat(territoryL0C1.totalRabbit()).isEqualTo(1);
    }
}
