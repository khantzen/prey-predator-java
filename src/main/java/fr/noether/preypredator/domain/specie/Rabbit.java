package fr.noether.preypredator.domain.specie;

public class Rabbit implements Specie {
    private static int MAJORITY = 3;

    public final int age;

    private Rabbit(int age) {
        this.age = age;
    }

    public static Rabbit newBorn() {
        return new Rabbit(0);
    }

    public static Rabbit withAge(int age) {
        return new Rabbit(age);
    }

    public boolean canReproduce() {
        return this.age > MAJORITY;
    }

    public Rabbit incrementAge() {
        return Rabbit.withAge(this.age + 1);
    }
}
