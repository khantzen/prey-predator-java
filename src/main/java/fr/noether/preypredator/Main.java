package fr.noether.preypredator;

import fr.noether.preypredator.domain.Simulation;

import java.util.Scanner;

public class Main {

    public static void main(String... args) {
        Simulation simulation = Simulation.init();
        simulation.launch();

        Scanner input = new Scanner(System.in);


    }

}
