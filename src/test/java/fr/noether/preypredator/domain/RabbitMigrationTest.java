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

        world.migrateRabbits();

        Territory territoryL0C1 = world.territoryAt(Coord.of(0, 1));
        Territory territoryL0C0 = world.territoryAt(Coord.of(0, 0));

        Assertions.assertThat(territoryL0C1.totalRabbit()).isEqualTo(1);
        Assertions.assertThat(territoryL0C0.totalRabbit()).isEqualTo(0);
    }

    @Test
    public void multiple_rabbits_should_move_from_their_territory_to_one_adjacent_territory() {
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
                .baseRabbitCount(4)
                .baseFoxCount(0)
                .coordGenerator(mockedCoordGenerator)
                .foxMigration(new SpecieMigration(mockedRandomGenerator))
                .rabbitMigration(new SpecieMigration(mockedRandomGenerator))
                .build();

        world.migrateRabbits();
        var territoryL1C0 = world.territoryAt(Coord.of(1, 0));
        var territoryL2C4 = world.territoryAt(Coord.of(2, 4));
        var territoryL2C3 = world.territoryAt(Coord.of(2, 3));

        Assertions.assertThat(territoryL1C0.totalRabbit()).isEqualTo(1);
        Assertions.assertThat(territoryL2C4.totalRabbit()).isEqualTo(0);
        Assertions.assertThat(territoryL2C3.totalRabbit()).isEqualTo(1);
    }

    @Test
    public void rabbit_should_migrate_only_once() {
        var mockedCoordGenerator =
                new MockCoordGenerator(
                        Coord.of(0, 0),
                        Coord.of(0, 0),
                        Coord.of(0, 1)
                );

        var mockedRandomGenerator = new MockRandomGenerator(0, 0, 0, 0, 0);

        World world = new World.Builder()
                .totalLine(1)
                .totalColumn(2)
                .baseRabbitCount(3)
                .baseFoxCount(0)
                .coordGenerator(mockedCoordGenerator)
                .foxMigration(new SpecieMigration(mockedRandomGenerator))
                .rabbitMigration(new SpecieMigration(mockedRandomGenerator))
                .build();

        world.migrateRabbits();

        var territoryL0C0 = world.territoryAt(Coord.of(0,0));
        var territoryL0C1 = world.territoryAt(Coord.of(0,1));

        Assertions.assertThat(territoryL0C0.totalRabbit()).isEqualTo(1);
        Assertions.assertThat(territoryL0C1.totalRabbit()).isEqualTo(2);
    }
}
