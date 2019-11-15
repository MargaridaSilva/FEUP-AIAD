package utils;

import java.awt.Color;

public final class Configs {

    public static final int TICK_PERIOD = 100;

    public static final Color MALE_COLOR = new Color(66, 149, 245);

    public static final Color FEMALE_COLOR = new Color(245, 66, 149);

    // Minimum energy expenditure
    public static final double MIN_ENERGY_EXP = 0.005;

    // Maximum energy expenditure
    public static final double MAX_ENERGY_EXP = 0.01;

    // Number of children
    public static final int NUM_CHILDREN = 2;

    // Minimum energy required for an animal to mate
    public static final double MIN_ENERGY_MATE = 0.8; 

    // Minimum energy required for an animal to move randomly
    public static final double MIN_ENERGY_RANDOM = 0.5;

}