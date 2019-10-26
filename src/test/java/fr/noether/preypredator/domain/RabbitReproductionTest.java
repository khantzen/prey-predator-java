package fr.noether.preypredator.domain;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class RabbitReproductionTest {
    @Test
    public void when_two_rabbits_met_with_no_foxes_should_produce_one_rabbit() {
        Territory territory = createTerritoryWithNoFoxes(2);
        territory.startRabbitReproduction();
        Assertions.assertThat(territory.totalRabbit()).isEqualTo(3);
    }

    @Test
    public void when_four_rabbit_mets_with_no_foxes_should_produce_two_rabbits() {
        Territory territory = createTerritoryWithNoFoxes(4);
        territory.startRabbitReproduction();
        Assertions.assertThat(territory.totalRabbit()).isEqualTo(6);
    }

    @Test
    public void when_five_rabbits_met_with_no_foxes_should_produce_two_rabbits() {
        Territory territory = createTerritoryWithNoFoxes(5);
        territory.startRabbitReproduction();
        Assertions.assertThat(territory.totalRabbit()).isEqualTo(7);
    }

    @Test
    public void rabbit_should_not_breed_when_a_fox_is_present() {
        Territory territory = createTerritoryWith(5, 1);
        territory.startRabbitReproduction();
        Assertions.assertThat(territory.totalRabbit()).isEqualTo(5);
    }

    @Test
    public void rabbit_should_not_breed_when_under_age_of_3() {
        Territory territory = createTerritoryWith(1, 0);
        territory.addRabbit(Rabbit.newBorn());
        territory.startRabbitReproduction();
        Assertions.assertThat(territory.totalRabbit()).isEqualTo(2);
    }

    private Territory createTerritoryWithNoFoxes(int rabbitCount) {
        return this.createTerritoryWith(rabbitCount, 0);
    }

    private Territory createTerritoryWith(int rabbitCount, int foxCount) {
        var territory = Territory.at(Coord.of(0, 0));

        for (int i = 0; i < rabbitCount; i++) {
            territory.addRabbit(Rabbit.withAge(5));
        }

        for (int i = 0; i < foxCount; i++) {
            territory.addFox();
        }

        return territory;
    }

}
