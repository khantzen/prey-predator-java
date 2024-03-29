package fr.noether.preypredator.domain;

import fr.noether.preypredator.domain.area.Forest;
import fr.noether.preypredator.domain.life.SpecieMigration;
import fr.noether.preypredator.util.CoordGenerator;
import fr.noether.preypredator.util.RandomCoordGenerator;
import fr.noether.preypredator.util.RandomGenerator;
import fr.noether.preypredator.util.Randomizer;

import java.util.Scanner;

public class Simulation {
    private final Forest forest;
    private final Scanner input;

    private Simulation(Forest forest) {
        this.forest = forest;
        this.input = new Scanner(System.in);
    }

    public static Simulation init() {
        int totalLine = 5;
        int totalColumn = 5;
        int startingRabbitCount = 5;
        int startingFoxCount = 5;

        RandomGenerator randomGenerator = new Randomizer();

        SpecieMigration foxMigration = new SpecieMigration(randomGenerator);
        SpecieMigration rabbitMigration = new SpecieMigration(randomGenerator);

        CoordGenerator coordGenerator =
                new RandomCoordGenerator(
                        totalLine,
                        totalColumn,
                        randomGenerator
                );

        var forest = new Forest.Builder()
                .totalLine(totalLine)
                .totalColumn(totalColumn)

                .baseRabbitCount(startingRabbitCount)
                .baseFoxCount(startingFoxCount)

                .coordGenerator(coordGenerator)

                .foxMigration(foxMigration)
                .rabbitMigration(rabbitMigration)

                .build();

        return new Simulation(forest);
    }

    public void launch() {
        do {
            forest.launchCycle();
            System.out.println("Rabbit count: " + forest.totalRabbitPopulation());
            System.out.println("Fox count: " + forest.totalFoxPopulation());
            this.editEcosystem();
        } while (true);
    }

    private void editEcosystem() {
        System.out.println("Would you like to edit current ecosystem ? y/[n]");
        String answer = this.input.nextLine();

        if ("y".equals(answer)) {
            repopulateWithRabbit();
            repopulateWithFox();
        }
    }

    private void repopulateWithRabbit() {
        System.out.println("How many rabbit would you like to add ? (default 0 | No Negative)");
        String rabbitToAddAnswer = this.input.nextLine();
        int rabbitToAdd = 0;
        try {
            rabbitToAdd = Math.abs(Integer.parseInt(rabbitToAddAnswer));
        } catch (NumberFormatException ignored) {}

        for (int i = 0; i < rabbitToAdd; i++) {
            this.forest.spawnDivinelyANewBornRabbit();
        }
    }

    private void repopulateWithFox() {
        System.out.println("How many fox would you like to add ? (default 0 | No Negative)");
        String foxToAddAnswer = this.input.nextLine();
        int foxToAdd = 0;
        try {
            foxToAdd = Math.abs(Integer.parseInt(foxToAddAnswer));
        } catch (NumberFormatException ignored) {}

        for (int i = 0; i < foxToAdd; i++) {
            this.forest.spawnDivinelyANewBornFox();
        }

    }
}
