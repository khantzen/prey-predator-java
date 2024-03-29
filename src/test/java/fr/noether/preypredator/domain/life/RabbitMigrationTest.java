package fr.noether.preypredator.domain.life;

import fr.noether.preypredator.domain.area.Coord;
import fr.noether.preypredator.domain.area.Territory;
import fr.noether.preypredator.domain.area.Forest;
import fr.noether.preypredator.domain.specie.Rabbit;
import fr.noether.preypredator.util.*;
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
                new MockOnlyZeroRandomGenerator();

        Forest forest = new Forest.Builder()
                .totalLine(1)
                .totalColumn(3)
                .baseRabbitCount(1)
                .baseFoxCount(0)
                .coordGenerator(mockedCoordGenerator)
                .foxMigration(new SpecieMigration(mockedRandomGenerator))
                .rabbitMigration(new SpecieMigration(mockedRandomGenerator))
                .build();

        forest.migrateRabbits();

        Territory territoryL0C1 = forest.territoryAt(Coord.of(0, 1));
        Territory territoryL0C0 = forest.territoryAt(Coord.of(0, 0));

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
                new MockParametrizedRandomGenerator(0, 1, 2, 3);

        Forest forest = new Forest.Builder()
                .totalLine(5)
                .totalColumn(5)
                .baseRabbitCount(4)
                .baseFoxCount(0)
                .coordGenerator(mockedCoordGenerator)
                .foxMigration(new SpecieMigration(mockedRandomGenerator))
                .rabbitMigration(new SpecieMigration(mockedRandomGenerator))
                .build();

        forest.migrateRabbits();
        var territoryL1C0 = forest.territoryAt(Coord.of(1, 0));
        var territoryL2C4 = forest.territoryAt(Coord.of(2, 4));
        var territoryL2C3 = forest.territoryAt(Coord.of(2, 3));

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

        var mockedRandomGenerator = new MockOnlyZeroRandomGenerator();

        Forest forest = new Forest.Builder()
                .totalLine(1)
                .totalColumn(2)
                .baseRabbitCount(3)
                .baseFoxCount(0)
                .coordGenerator(mockedCoordGenerator)
                .foxMigration(new SpecieMigration(mockedRandomGenerator))
                .rabbitMigration(new SpecieMigration(mockedRandomGenerator))
                .build();

        forest.migrateRabbits();

        var territoryL0C0 = forest.territoryAt(Coord.of(0,0));
        var territoryL0C1 = forest.territoryAt(Coord.of(0,1));

        Assertions.assertThat(territoryL0C0.totalRabbit()).isEqualTo(1);
        Assertions.assertThat(territoryL0C1.totalRabbit()).isEqualTo(2);
    }

    @Test
    public void rabbit_should_age_when_migrate() {
        var mockedCoordGenerator =
                new MockCoordGenerator(
                        Coord.of(0, 0)
                );

        var mockedRandomGenerator = new MockOnlyZeroRandomGenerator();

        Forest forest = new Forest.Builder()
                .totalLine(1)
                .totalColumn(2)
                .baseRabbitCount(1)
                .baseFoxCount(0)
                .coordGenerator(mockedCoordGenerator)
                .foxMigration(new SpecieMigration(mockedRandomGenerator))
                .rabbitMigration(new SpecieMigration(mockedRandomGenerator))
                .build();

        forest.migrateRabbits();

        Territory territoryL0C1 = forest.territoryAt(Coord.of(0, 1));
        Rabbit rabbit = territoryL0C1.removeRabbit();
        Assertions.assertThat(rabbit.age).isEqualTo(1);
    }
}
