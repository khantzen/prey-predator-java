package fr.noether.preypredator.domain;

import fr.noether.preypredator.util.MockCoordGenerator;
import fr.noether.preypredator.util.MockParametrizedRandomGenerator;
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

        Migration migration = new SpecieMigration(new MockParametrizedRandomGenerator(0, 0));
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

        world.migrateRabbits();
        world.migrateFoxes();
        world.startHunt();

        var territoryL0C1 = world.territoryAt(Coord.of(0, 1));
        Assertions.assertThat(territoryL0C1.totalRabbit()).isEqualTo(0);
        Assertions.assertThat(territoryL0C1.totalFox()).isEqualTo(1);
    }

    @Test
    public void one_hungry_fox_should_eat_only_one_rabbit() {
        var mockedCoordGenerator =
                new MockCoordGenerator(
                        Coord.of(0, 0),
                        Coord.of(0, 0),
                        Coord.of(0, 0),
                        Coord.of(0, 2),
                        Coord.of(0, 2)
                );

        var migration = new SpecieMigration(new MockParametrizedRandomGenerator(0, 0, 0, 0, 0, 0));
        var world = new World.Builder()
                .baseFoxCount(2)
                .baseRabbitCount(3)
                .totalLine(1)
                .totalColumn(3)
                .coordGenerator(mockedCoordGenerator)
                .foxMigration(migration)
                .rabbitMigration(migration)
                .coordGenerator(mockedCoordGenerator)
                .build();

        world.migrateRabbits();
        world.migrateFoxes();
        world.startHunt();

        var territoryL0C1 = world.territoryAt(Coord.of(0, 1));
        Assertions.assertThat(territoryL0C1.totalRabbit()).isEqualTo(1);
        Assertions.assertThat(territoryL0C1.totalFox()).isEqualTo(2);
    }

    @Test
    public void hunt_happen_on_multiple_territories() {
        var mockedCoordGenerator =
                new MockCoordGenerator(
                        Coord.of(0, 0),
                        Coord.of(0, 1),
                        Coord.of(0, 0),
                        Coord.of(0, 1)
                );

        var migration = new SpecieMigration(
                new MockParametrizedRandomGenerator(0, 0, 0, 0, 0, 0)
        );

        var world = new World.Builder()
                .baseRabbitCount(2)
                .baseFoxCount(2)
                .totalLine(2)
                .totalColumn(2)
                .coordGenerator(mockedCoordGenerator)
                .foxMigration(migration)
                .rabbitMigration(migration)
                .coordGenerator(mockedCoordGenerator)
                .build();

        world.migrateRabbits();
        world.migrateFoxes();
        world.startHunt();

        var territoryL0C0 = world.territoryAt(Coord.of(0, 0));
        Assertions.assertThat(territoryL0C0.totalFox()).isEqualTo(0);
        Assertions.assertThat(territoryL0C0.totalRabbit()).isEqualTo(0);

        var territoryL1C0 = world.territoryAt(Coord.of(1, 0));
        Assertions.assertThat(territoryL1C0.totalFox()).isEqualTo(1);
        Assertions.assertThat(territoryL1C0.totalRabbit()).isEqualTo(0);

        var territoryL0C1 = world.territoryAt(Coord.of(0, 1));
        Assertions.assertThat(territoryL0C1.totalFox()).isEqualTo(0);
        Assertions.assertThat(territoryL0C1.totalRabbit()).isEqualTo(0);

        var territoryL1C1 = world.territoryAt(Coord.of(1, 1));
        Assertions.assertThat(territoryL1C1.totalFox()).isEqualTo(1);
        Assertions.assertThat(territoryL1C1.totalRabbit()).isEqualTo(0);
    }

    @Test
    public void hungry_fox_should_be_fed_when_he_eats_a_rabbit() {
        var territory = Territory.at(Coord.of(0, 0));

        territory.addFox(Fox.hungry());
        territory.addRabbit(Rabbit.newBorn());

        territory.startHunt();

        Assertions.assertThat(territory.totalRabbit()).isEqualTo(0);

        Fox fox = territory.removeFox();
        Assertions.assertThat(fox.isFed).isTrue();
    }
}
