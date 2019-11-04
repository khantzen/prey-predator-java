package fr.noether.preypredator.domain.life;

import fr.noether.preypredator.domain.specie.Specie;

import java.util.List;

public class Reproduction {
    public static long countNewBorn(List<Specie> species) {
        var individualThatCanReproduce = species.stream()
                .filter(Specie::canReproduce)
                .count();

        return individualThatCanReproduce / 2;
    }
}
