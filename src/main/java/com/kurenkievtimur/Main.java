package com.kurenkievtimur;

import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // write your code here
        File file = new File("rating.txt");

        input(file);
    }

    public static void input(File file) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        initPlayer(file, name);
        System.out.printf("Hello, %s\n", name);

        String properties = scanner.nextLine();
        while (!isValidProperties(properties)) {
            System.out.println("Invalid input");
            properties = scanner.nextLine();
        }

        Property[] enumProperties = Property.getProperties(properties);
        System.out.println("Okay, let's start");

        boolean isExit = false;
        while (!isExit) {
            String input = scanner.next();
            switch (input) {
                case "rock", "fire", "scissors", "snake", "human", "tree", "wolf", "sponge", "paper", "air", "water", "dragon", "devil", "lightning", "gun" -> {
                    if (!isAvailableProperties(input, enumProperties)) {
                        System.out.println("Invalid input");
                        continue;
                    }

                    Property player = Property.getProperty(input);
                    Property computer = Property.getRandomProperty(enumProperties);
                    if (isDraw(player, computer)) {
                        System.out.printf("There is a draw (%s)\n", computer);
                        overwriteFile(file, name, 50);
                    } else if (isWin(player, computer)) {
                        System.out.printf("Well done. The computer chose %s and failed\n", computer);
                        overwriteFile(file, name, 100);
                    } else {
                        System.out.printf("Sorry, but the computer chose %s\n", computer);
                    }
                }
                case "!rating" -> System.out.printf("Your rating: %d\n", getRating(file, name));
                case "!exit" -> isExit = true;
                default -> System.out.println("Invalid input");
            }
        }
        System.out.println("Bye!");
    }

    public static boolean isAvailableProperties(String input, Property[] properties) {
        for (Property property : properties) {
            if (property.equals(Property.getProperty(input))) {
                return true;
            }
        }

        return false;
    }

    public static boolean isValidProperties(String input) {
        String[] properties = input.split(",");
        for (String property : properties) {
            if (!property.matches("rock|fire|scissors|snake|human|tree|wolf|sponge|paper|air|water|dragon|devil|lightning|gun|")) {
                return false;
            }
        }

        return true;
    }

    public static void initPlayer(File file, String input) {
        boolean isExists = false;
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                String name = scanner.nextLine().split(" ")[0];
                if (name.equals(input))
                    isExists = true;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (!isExists) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(file, true))) {
                writer.println("%s %s".formatted(input, 0));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean isWin(Property player, Property computer) {
        Property[] properties = Property.values();

        Property[] winProperties = new Property[8];
        for (int i = player.getIndex() + 1, counter = 0; counter < 7; i++) {
            if (i == properties.length) {
                i = 0;
            }
            winProperties[counter] = properties[i];
            counter++;
        }

        for (Property property : winProperties) {
            if (computer.equals(property)) {
                return true;
            }
        }

        return false;
    }

    public static boolean isDraw(Property player, Property computer) {
        return player.equals(computer);
    }

    public static int getRating(File file, String input) {
        int rating = 0;
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                String[] properties = scanner.nextLine().split(" ");
                String name = properties[0];
                if (name.equals(input)) {
                    rating = Integer.parseInt(properties[1]);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return rating;
    }

    public static String[] getRows(File file) {
        StringBuilder builder = new StringBuilder();

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                builder.append(scanner.nextLine()).append(System.lineSeparator());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        builder.delete(builder.length() - 2, builder.length());
        return builder.toString().split(System.lineSeparator());
    }

    public static void overwriteFile(File file, String input, int points) {
        String[] rows = getRows(file);

        try (PrintWriter writer = new PrintWriter(file)) {
            for (String row : rows) {
                String[] properties = row.split(" ");

                String name = properties[0];
                int rating = Integer.parseInt(properties[1]);

                if (name.equals(input))
                    rating += points;

                writer.println("%s %d".formatted(name, rating));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}