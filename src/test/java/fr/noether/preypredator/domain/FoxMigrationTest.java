package fr.noether.preypredator.domain;

import fr.noether.preypredator.util.*;
import org.assertj.core.api.Assertions;
import org.junit.Ignore;
import org.junit.Test;

public class FoxMigrationTest {

    @Test
    public void fox_should_move_from_one_territory_to_one_adjacent_territory() {
        CoordGenerator mockedCoordGenerator =
                new MockCoordGenerator(
                        Coord.of(0, 0),
                        Coord.of(0, 2)
                );

        RandomGenerator mockedRandomGenerator =
                new MockParametrizedRandomGenerator(0, 0);

        World world = new World.Builder()
                .totalLine(1)
                .totalColumn(3)
                .baseRabbitCount(0)
                .baseFoxCount(2)
                .coordGenerator(mockedCoordGenerator)
                .foxMigration(new SpecieMigration(mockedRandomGenerator))
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
                new MockParametrizedRandomGenerator(0, 1, 2, 3);

        World world = new World.Builder()
                .totalLine(5)
                .totalColumn(5)
                .baseRabbitCount(0)
                .baseFoxCount(4)
                .coordGenerator(mockedCoordGenerator)
                .foxMigration(new SpecieMigration(mockedRandomGenerator))
                .build();

        world.migrateFoxes();
        var territoryL1C0 = world.territoryAt(Coord.of(1, 0));
        var territoryL2C4 = world.territoryAt(Coord.of(2, 4));
        var territoryL2C3 = world.territoryAt(Coord.of(2, 3));

        Assertions.assertThat(territoryL1C0.totalFox()).isEqualTo(1);
        Assertions.assertThat(territoryL2C4.totalFox()).isEqualTo(0);
        Assertions.assertThat(territoryL2C3.totalFox()).isEqualTo(1);
    }

    @Test
    public void after_migration_no_fox_should_left_in_territory() {
        var mockedCoordGenerator =
                new MockCoordGenerator(
                        Coord.of(3, 3),
                        Coord.of(3, 3),
                        Coord.of(3, 3),
                        Coord.of(3, 3),
                        Coord.of(3, 3)
                );

        var mockedRandomGenerator = new MockParametrizedRandomGenerator(0, 0, 0, 0, 0);

        World world = new World.Builder()
                .totalLine(5)
                .totalColumn(5)
                .baseRabbitCount(0)
                .baseFoxCount(5)
                .coordGenerator(mockedCoordGenerator)
                .foxMigration(new SpecieMigration(mockedRandomGenerator))
                .build();

        world.migrateFoxes();

        var territoryL3C3 = world.territoryAt(Coord.of(3, 3));
        Assertions.assertThat(territoryL3C3.totalFox()).isEqualTo(0);
    }

    @Test
    public void fox_should_migrate_only_once() {
        var mockedCoordGenerator =
                new MockCoordGenerator(
                        Coord.of(0, 0),
                        Coord.of(0, 0),
                        Coord.of(0, 1)
                );

        var mockedRandomGenerator = new MockParametrizedRandomGenerator(0, 0, 0, 0, 0);

        World world = new World.Builder()
                .totalLine(1)
                .totalColumn(2)
                .baseRabbitCount(0)
                .baseFoxCount(3)
                .coordGenerator(mockedCoordGenerator)
                .foxMigration(new SpecieMigration(mockedRandomGenerator))
                .build();

        world.migrateFoxes();

        var territoryL0C0 = world.territoryAt(Coord.of(0, 0));
        var territoryL0C1 = world.territoryAt(Coord.of(0, 1));

        Assertions.assertThat(territoryL0C0.totalFox()).isEqualTo(1);
        Assertions.assertThat(territoryL0C1.totalFox()).isEqualTo(2);
    }

    @Test
    public void fox_age_should_increment_when_migrating() {
        var mockedCoordGenerator =
                new MockCoordGenerator(
                        Coord.of(0, 0)
                );

        var mockedRandomGenerator = new MockOnlyZeroRandomGenerator();

        World world = new World.Builder()
                .totalLine(1)
                .totalColumn(2)
                .baseRabbitCount(0)
                .baseFoxCount(1)
                .coordGenerator(mockedCoordGenerator)
                .foxMigration(new SpecieMigration(mockedRandomGenerator))
                .build();

        world.migrateFoxes();

        var territoryL0C1 = world.territoryAt(Coord.of(0, 1));

        Fox fox = territoryL0C1.removeFox();
        Assertions.assertThat(fox.age).isEqualTo(1);
    }

    @Test
    public void fox_should_be_hungry_when_it_didnt_eat_at_least_one_time_in_5_cycle() {
        var mockedCoordGenerator =
                new MockCoordGenerator(
                        Coord.of(0, 0)
                );

        var mockedRandomGenerator = new MockOnlyZeroRandomGenerator();

        World world = new World.Builder()
                .totalLine(1)
                .totalColumn(2)
                .baseRabbitCount(0)
                .baseFoxCount(1)
                .coordGenerator(mockedCoordGenerator)
                .foxMigration(new SpecieMigration(mockedRandomGenerator))
                .build();

        for (int i = 0; i < 4; i++) {
            world.migrateFoxes();
        }

        var territoryL0C0 = world.territoryAt(Coord.of(0, 0));
        Fox fox = territoryL0C0.removeFox();
        Assertions.assertThat(fox.isFed).isTrue();
        territoryL0C0.addFox(fox);

        for (int i = 0; i < 2; i++) {
            world.migrateFoxes();
        }

        fox = territoryL0C0.removeFox();
        Assertions.assertThat(fox.isFed).isFalse();
    }

    @Test
    public void fox_should_die_from_hunger_after_3_migrations_being_hungry() {
        var mockedCoordGenerator =
                new MockCoordGenerator(
                        Coord.of(0, 0)
                );

        var mockedRandomGenerator = new MockOnlyZeroRandomGenerator();

        World world = new World.Builder()
                .totalLine(1)
                .totalColumn(2)
                .baseRabbitCount(0)
                .baseFoxCount(1)
                .coordGenerator(mockedCoordGenerator)
                .foxMigration(new SpecieMigration(mockedRandomGenerator))
                .build();

        for (int i = 0; i < 9; i++) {
            world.migrateFoxes();
        }

        Assertions.assertThat(world.totalFoxPopulation()).isEqualTo(0);
    }

    @Test
    public void eating_rabbit_should_allow_fox_to_live_longer() {
        var mockedCoordGenerator =
                new MockCoordGenerator(
                        Coord.of(0, 0)
                );

        var mockedRandomGenerator = new MockOnlyZeroRandomGenerator();

        World world = new World.Builder()
                .totalLine(1)
                .totalColumn(2)
                .baseRabbitCount(0)
                .baseFoxCount(1)
                .coordGenerator(mockedCoordGenerator)
                .foxMigration(new SpecieMigration(mockedRandomGenerator))
                .build();

        for (int i = 0; i < 8; i++) {
            world.migrateFoxes();
        }

        Territory territory = world.territoryAt(Coord.of(0, 0));
        territory.addRabbit(Rabbit.newBorn());
        territory.startHunt();

        for (int i = 0; i < 5; i++) {
            world.migrateFoxes();
        }

        Assertions.assertThat(world.totalFoxPopulation()).isEqualTo(1);

        for (int i = 0; i < 10; i++) {
            world.migrateFoxes();
        }

        Assertions.assertThat(world.totalFoxPopulation()).isEqualTo(0);
    }
}
