package fr.noether.preypredator.domain;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.List;

public class AdjacentCoordTest {

    @Test
    public void valid_bottom_coord_should_appear_in_ajdacent() {
        var currentCoord = Coord.of(5, 5);
        var adjacent = currentCoord.adjacent(10, 10);
        Assertions.assertThat(adjacent).contains(Coord.of(6,5));
    }

    @Test
    public void invalid_bottom_coord_should_not_appear_in_adjacent() {
        var currentCoord = Coord.of(5, 5);
        var adjacent = currentCoord.adjacent(5, 10);
        Assertions.assertThat(adjacent).doesNotContain(Coord.of(6,5));

    }

    @Test
    public void valid_top_coord_should_appear_in_adjacent() {
        var currentCoord = Coord.of(5, 5);
        var adjacent = currentCoord.adjacent(10, 10);
        Assertions.assertThat(adjacent).contains(Coord.of(4,5));
    }

    @Test
    public void invalid_top_coord_should_not_appear_in_adjacent() {
        var currentCoord = Coord.of(0, 5);
        var adjacent = currentCoord.adjacent(10, 10);
        Assertions.assertThat(adjacent).doesNotContain(Coord.of(-1,5));
    }

    @Test
    public void valid_right_coord_should_appear_in_adjacent() {
        var currentCoord = Coord.of(0, 5);
        var adjacent = currentCoord.adjacent(10, 10);
        Assertions.assertThat(adjacent).contains(Coord.of(0,6));
    }

    @Test
    public void valid_left_coord_should_appear_in_adjacent() {
        var currentCoord = Coord.of(0, 5);
        var adjacent = currentCoord.adjacent(10, 10);
        Assertions.assertThat(adjacent).contains(Coord.of(0,4));
    }
}
