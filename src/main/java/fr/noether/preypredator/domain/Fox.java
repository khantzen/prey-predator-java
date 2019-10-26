package fr.noether.preypredator.domain;

public class Fox implements Specie {
    private static int MAJORITY = 3;
    public final int age;
    public final boolean isFed;

    private Fox(int age, boolean isFed) {
        this.age = age;
        this.isFed = isFed;
    }

    public static Fox withAge(int age) {
        return new Fox(age, true);
    }

    public static Fox newBorn() {
        return new Fox(0, true);
    }

    Fox incrementFoxAge() {
        if (age >= 5) {
            return new Fox(age+1, false);
        }
        return new Fox(age + 1, true);
    }

    public static Fox hungry() {
        return new Fox(5, false);
    }

    public boolean canReproduce() {
        return this.isFed && this.age > MAJORITY;
    }

    public Fox feed() {
        return new Fox(this.age, true);
    }
}
