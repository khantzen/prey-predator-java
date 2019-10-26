package fr.noether.preypredator.domain;

public class Fox implements Specie {
    private static int MAJORITY = 3;
    private static int HUNGRY_STATE = 5;

    public final int age;
    public final boolean isFed;
    private final int lastMeal;

    private Fox(int age, boolean isFed, int lastMeal) {
        this.age = age;
        this.isFed = isFed;
        this.lastMeal = lastMeal;
    }

    public static Fox withAge(int age) {
        return new Fox(age, true, age);
    }

    public static Fox newBorn() {
        return new Fox(0, true, 0);
    }

    Fox incrementFoxAge() {
        if (age >= HUNGRY_STATE) {
            return new Fox(age+1, false, lastMeal);
        }
        return new Fox(age + 1, true, lastMeal);
    }

    public static Fox hungry() {
        return new Fox(5, false, 0);
    }

    public boolean canReproduce() {
        return this.isFed && this.age > MAJORITY;
    }

    public Fox feed() {
        return new Fox(this.age, true, this.age);
    }

    public boolean shouldDie() {
        return this.age == 8;
    }
}
