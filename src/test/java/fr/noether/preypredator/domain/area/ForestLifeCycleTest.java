package fr.noether.preypredator.domain.area;

import fr.noether.preypredator.domain.life.Migration;
import fr.noether.preypredator.domain.life.SpecieMigration;
import fr.noether.preypredator.util.MockCoordGenerator;
import fr.noether.preypredator.util.MockOnlyZeroRandomGenerator;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class ForestLifeCycleTest {

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

        var forest = new Forest.Builder()
                .baseFoxCount(0)
                .baseRabbitCount(2)
                .totalLine(1)
                .totalColumn(3)
                .coordGenerator(mockedCoordGenerator)
                .foxMigration(migration)
                .rabbitMigration(migration)
                .coordGenerator(mockedCoordGenerator)
                .build();

        launchCycle(4, forest);

        Assertions.assertThat(forest.totalRabbitPopulation()).isEqualTo(3);
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

        var forest = new Forest.Builder()
                .baseFoxCount(2)
                .baseRabbitCount(0)
                .totalLine(1)
                .totalColumn(3)
                .coordGenerator(mockedCoordGenerator)
                .foxMigration(migration)
                .rabbitMigration(migration)
                .coordGenerator(mockedCoordGenerator)
                .build();

        launchCycle(4, forest);

        Assertions.assertThat(forest.totalFoxPopulation()).isEqualTo(3);
    }

    private void launchCycle(int cycle, Forest forest) {
        for (int i = 0; i < cycle; i++) {
            forest.launchCycle();
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

        var forest = new Forest.Builder()
                .baseFoxCount(2)
                .baseRabbitCount(1)
                .totalLine(1)
                .totalColumn(3)
                .coordGenerator(mockedCoordGenerator)
                .foxMigration(migration)
                .rabbitMigration(migration)
                .coordGenerator(mockedCoordGenerator)
                .build();

        launchCycle(4, forest);

        Assertions.assertThat(forest.totalFoxPopulation()).isEqualTo(3);
        Assertions.assertThat(forest.totalRabbitPopulation()).isEqualTo(0);
    }
}
