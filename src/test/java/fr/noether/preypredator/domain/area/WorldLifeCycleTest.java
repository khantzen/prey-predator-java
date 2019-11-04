package fr.noether.preypredator.domain.area;

import fr.noether.preypredator.domain.area.Coord;
import fr.noether.preypredator.domain.area.World;
import fr.noether.preypredator.domain.life.Migration;
import fr.noether.preypredator.domain.life.SpecieMigration;
import fr.noether.preypredator.util.MockCoordGenerator;
import fr.noether.preypredator.util.MockOnlyZeroRandomGenerator;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class WorldLifeCycleTest {

    @Test
    public void rabbit_should_met_and_reproduce() {
        var mockedCoordGenerator =
                new MockCoordGenerator(
                        Coord.of(0, 0),
                        Coord.of(0, 2)
                );

        Migration migration = new SpecieMigration(
                new MockOnlyZeroRandomGenerator()
        );

        var world = new World.Builder()
                .baseFoxCount(0)
                .baseRabbitCount(2)
                .totalLine(1)
                .totalColumn(3)
                .coordGenerator(mockedCoordGenerator)
                .foxMigration(migration)
                .rabbitMigration(migration)
                .coordGenerator(mockedCoordGenerator)
                .build();

        launchCycle(4, world);

        Assertions.assertThat(world.totalRabbitPopulation()).isEqualTo(3);
    }

    @Test
    public void fox_should_met_and_reproduce() {
        var mockedCoordGenerator =
                new MockCoordGenerator(
                        Coord.of(0, 0),
                        Coord.of(0, 2)
                );

        Migration migration = new SpecieMigration(
                new MockOnlyZeroRandomGenerator()
        );

        var world = new World.Builder()
                .baseFoxCount(2)
                .baseRabbitCount(0)
                .totalLine(1)
                .totalColumn(3)
                .coordGenerator(mockedCoordGenerator)
                .foxMigration(migration)
                .rabbitMigration(migration)
                .coordGenerator(mockedCoordGenerator)
                .build();

        launchCycle(4, world);

        Assertions.assertThat(world.totalFoxPopulation()).isEqualTo(3);
    }

    private void launchCycle(int cycle, World world) {
        for (int i = 0; i < cycle; i++) {
            world.launchCycle();
        }
    }

    @Test
    public void fox_and_rabbit_should_met_start_an_hunt_and_reproduce() {
        var mockedCoordGenerator =
                new MockCoordGenerator(
                        Coord.of(0, 0),
                        Coord.of(0, 2),
                        Coord.of(0, 2)
                );

        Migration migration = new SpecieMigration(
                new MockOnlyZeroRandomGenerator()
        );

        var world = new World.Builder()
                .baseFoxCount(2)
                .baseRabbitCount(1)
                .totalLine(1)
                .totalColumn(3)
                .coordGenerator(mockedCoordGenerator)
                .foxMigration(migration)
                .rabbitMigration(migration)
                .coordGenerator(mockedCoordGenerator)
                .build();

        launchCycle(4, world);

        Assertions.assertThat(world.totalFoxPopulation()).isEqualTo(3);
        Assertions.assertThat(world.totalRabbitPopulation()).isEqualTo(0);
    }
}
