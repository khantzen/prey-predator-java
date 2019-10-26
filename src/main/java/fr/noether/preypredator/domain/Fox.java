package fr.noether.preypredator.domain;

public class Fox implements Specie {
    private static int MAJORITY = 3;
    private static int HUNGRY_STATE = 5;

    public final int age;
    public final boolean isFed;
    private final int lastMeal;

    private Fox(int age, int lastMeal) {
        this.age = age;
        this.isFed = age - lastMeal < HUNGRY_STATE;
        this.lastMeal = lastMeal;
    }

    public static Fox withAge(int age) {
        return new Fox(age, age);
    }

    static Fox newBorn() {
        return new Fox(0, 0);
    }

    Fox incrementFoxAge() {
        if (age - lastMeal >= HUNGRY_STATE) {
            return new Fox(age + 1, lastMeal);
        }
        return new Fox(age + 1, lastMeal);
    }

    public static Fox hungry() {
        return new Fox(5, 0);
    }

    public boolean canReproduce() {
        return this.isFed && this.age > MAJORITY;
    }

    Fox feed() {
        return new Fox(this.age, this.age);
    }

    boolean shouldDie() {
        return this.age - lastMeal >= 8;
    }
}
