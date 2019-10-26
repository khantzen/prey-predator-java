package fr.noether.preypredator.domain;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class FoxReproductionTest {

    @Test
    public void two_foxes_should_produce_one_fox() {
        Territory territory = Territory.at(Coord.of(0,0));

        territory.addFox();
        territory.addFox();

        territory.startFoxReproduction();

        Assertions.assertThat(territory.totalFox()).isEqualTo(3);
    }
}
