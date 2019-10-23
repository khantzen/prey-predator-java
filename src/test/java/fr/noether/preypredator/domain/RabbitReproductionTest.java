package fr.noether.preypredator.domain;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class RabbitReproductionTest {
    @Test
    public void when_two_rabbits_met_with_no_foxes_should_produce_one_rabbit() {
        var territory = Territory.at(Coord.of(0, 0));

        territory.addRabbit();
        territory.addRabbit();

        territory.startReproduction();

        Assertions.assertThat(territory.totalRabbit()).isEqualTo(3);
    }

    @Test
    public void when_four_rabbit_mets_with_no_foxes_should_produce_two_rabbit() {
        var territory = Territory.at(Coord.of(0, 0));

        territory.addRabbit();
        territory.addRabbit();
        territory.addRabbit();
        territory.addRabbit();

        territory.startReproduction();
        Assertions.assertThat(territory.totalRabbit()).isEqualTo(6);
    }
}
