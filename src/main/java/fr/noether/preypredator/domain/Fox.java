package fr.noether.preypredator.domain;

public class Fox implements Specie {
    private static int MAJORITY = 3;
    private final boolean isFed;
    private final int age;

    public Fox(int age, boolean isFed) {
        this.age = age;
        this.isFed = isFed;
    }

    public static Fox withAge(int age) {
        return new Fox(age, true);
    }

    public static Fox newBorn() {
        return new Fox(0, true);
    }

    public static Fox hungry() {
        return new Fox(5, false);
    }

    public boolean canReproduce() {
        return this.isFed && this.age > MAJORITY;
    }
}
