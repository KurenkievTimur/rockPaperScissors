package com.kurenkievtimur;

import java.util.Random;

public enum Property {
    ROCK(0),
    FIRE(1),
    SCISSORS(2),
    SNAKE(3),
    HUMAN(4),
    TREE(5),
    WOLF(6),
    SPONGE(7),
    PAPER(8),
    AIR(9),
    WATER(10),
    DRAGON(11),
    DEVIL(12),
    LIGHTNING(13),
    GUN(14);

    private final int index;

    Property(int index) {
        this.index = index;
    }

    public static Property[] getProperties(String input) {
        String[] options = input.split(",");

        if (options.length == 1 && options[0].equals(""))
            return new Property[]{ROCK, PAPER, SCISSORS};

        Property[] properties = new Property[options.length];
        for (int i = 0; i < properties.length; i++) {
            properties[i] = getProperty(options[i]);
        }

        return properties;
    }

    public static Property getProperty(String property) {
        return Property.valueOf(property.toUpperCase());
    }

    public static Property getRandomProperty(Property[] properties) {
        Random random = new Random();

        int number = random.nextInt(properties.length);

        return properties[number];
    }

    public int getIndex() {
        return index;
    }

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}