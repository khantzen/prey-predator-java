package fr.noether.preypredator;

import fr.noether.preypredator.domain.Simulation;

public class Main {

    public static void main(String... args) {
        Simulation simulation = Simulation.init();
        simulation.launch();
    }

}
