package fr.noether.preypredator.domain;

import java.util.List;

class Reproduction {
    static long countNewBorn(List<Specie> species) {
        var individualThatCanReproduce = species.stream()
                .filter(Specie::canReproduce)
                .count();

        return individualThatCanReproduce / 2;
    }
}
