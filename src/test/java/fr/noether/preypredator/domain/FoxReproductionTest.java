package fr.noether.preypredator.domain;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class FoxReproductionTest {

    @Test
    public void two_foxes_should_produce_one_fox() {
        var territory = buildTerritoryWith(2);
        territory.startFoxReproduction();
        Assertions.assertThat(territory.totalFox()).isEqualTo(3);
    }

    @Test
    public void four_foxes_should_produce_two_foxes() {
        var territory = buildTerritoryWith(4);
        territory.startFoxReproduction();
        Assertions.assertThat(territory.totalFox()).isEqualTo(6);
    }

    @Test
    public void three_foxes_should_produces_one_fox() {
        var territory = buildTerritoryWith(3);
        territory.startFoxReproduction();
        Assertions.assertThat(territory.totalFox()).isEqualTo(4);
    }

    private Territory buildTerritoryWith(int foxCount) {
        Territory territory = Territory.at(Coord.of(0,0));

        for (int i = 0; i < foxCount; i++) {
            territory.addFox();
        }
        return territory;
    }
}