package fr.noether.preypredator.domain;

import java.util.List;
import java.util.function.Predicate;

public class Reproduction {
    public static long countSpecieCouple(List<Specie> species) {
        Predicate<Specie> byReproductionAbility = Specie::canBreed;

        var individualThatCanReproduce = species.stream()
                .filter(byReproductionAbility)
                .count();

        return individualThatCanReproduce / 2;
    }
}
