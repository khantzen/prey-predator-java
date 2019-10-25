package fr.noether.preypredator.domain;

public class Rabbit {
    private static int MAJORITY = 3;

    private final int age;

    private Rabbit(int age) {
        this.age = age;
    }

    public static Rabbit newBorn() {
        return new Rabbit(0);
    }

    public static Rabbit withAge(int age) {
        return new Rabbit(age);
    }

    public boolean canBreed() {
        return this.age > MAJORITY;
    }
}
