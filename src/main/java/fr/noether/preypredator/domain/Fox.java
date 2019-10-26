package fr.noether.preypredator.domain;

public class Fox implements Specie {
    private final boolean isFed;

    public Fox(boolean isFed) {
        this.isFed = isFed;
    }

    public static Fox hungry() {
        return new Fox(false);
    }

    public static Fox newBorn() {
        return new Fox(true);
    }

    public boolean canReproduce() {
        return this.isFed;
    }
}
