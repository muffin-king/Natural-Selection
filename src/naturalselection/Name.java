package naturalselection;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Name {
    private static final List<String> NAMES = new ArrayList<>();
    private static final Random RANDOM = new Random();

    private final static List<String> TITLES_FASTEST = new ArrayList<>();

    static {
        NAMES.add("Jeff");
        NAMES.add("Cookie Monster");
        NAMES.add("Bill");
        NAMES.add("Kate");
        NAMES.add("Alex");
        NAMES.add("Zach");
        NAMES.add("Dumb");
        NAMES.add("Silly");
        NAMES.add("Bob");
        NAMES.add("Little Dude");
        NAMES.add("Nameless One");
        NAMES.add("Jack");
        NAMES.add("Gonk");
        NAMES.add("Keyboard");
        NAMES.add("The set of all points equidistant from a point");
        NAMES.add("Yummy");
        NAMES.add("Consumer of Souls");
        NAMES.add("Cuisinart CPT-122BK 2-Slice Compact Plastic Toaster, Black");
        NAMES.add("Rose");
        NAMES.add("Karen");
        NAMES.add("The Unfathomable One");
        NAMES.add("Scrunkly");
        NAMES.add("Delicious");
        NAMES.add("Spongebob");
        NAMES.add("Chimichanga");
        NAMES.add("Jebodiah");
        NAMES.add("Bartholomew");
        NAMES.add("Dinkle");
        NAMES.add("Sprinkle");
        NAMES.add("Tinkle");
        NAMES.add("Winkle");
        NAMES.add("Sinkhole");
        NAMES.add("Adopted");
        NAMES.add("Jeb");
        NAMES.add("Cthulhu the Unstoppable");
        NAMES.add("Jamaican Me Crazy");
        NAMES.add("Joe");
        NAMES.add("Angle Side Side");
        NAMES.add("Law of Cosines");
        NAMES.add("01000011 01100101 01101100 01101100");
        NAMES.add("Matilda");
        NAMES.add("Square");
        NAMES.add("-.-. . .-.. .-..");
        NAMES.add("Zachary Grinberg");
        NAMES.add("(A)sex Haver");
        NAMES.add("Barometer");
        NAMES.add("Ronald McDonald");
        NAMES.add("Peter Piper");
        NAMES.add("Picked a patch");
        NAMES.add("Of pickled peppers");
        NAMES.add("Preliminarily");
        NAMES.add("Poopoo");
        NAMES.add("Penguins");
        NAMES.add("Probably pee pineapples");
        NAMES.add("Perhaps piper pots");
        NAMES.add("These pee pineapples");
        NAMES.add("Adopted (Affectionate)");
        NAMES.add("George Washington");

        TITLES_FASTEST.add("Ignorer of Red Lights");
        TITLES_FASTEST.add("Consumer of Caffeine");
        TITLES_FASTEST.add("Walker of Fastness");
    }

    public static String takeName() {
        return NAMES.get(RANDOM.nextInt(NAMES.size()));
    }
}
