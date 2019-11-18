package utils;

import java.awt.Color;

public final class Configs {

    public static final int TICK_PERIOD = 100;

    public static final Color MALE_COLOR = new Color(66, 149, 245);

    public static final Color FEMALE_COLOR = new Color(245, 66, 149);

    public static final Color PLANT_COLOR = new Color(58, 95, 11);

    // Minimum energy expenditure
    public static final double MIN_ENERGY_EXP = 0.005;

    // Maximum energy expenditure
    public static final double MAX_ENERGY_EXP = 0.01;

    // Max number of predator children
    public static final int PREDATOR_NUM_CHILDREN = 2;

    // Max number of prey children
    public static final int PREY_NUM_CHILDREN = 2;

    // Minimum energy required for an animal to mate
    public static final double MIN_ENERGY_MATE = 0.8; 

    // Minimum energy required for an animal to move randomly
    public static final double MIN_ENERGY_RANDOM = 0.5;

    // Energy given to preys when eat a plant
    public static final double PLANT_ENERGY = 0.1;

    // Energy level where agent starts looking for food
    public static final double MIN_ENERGY_EAT = 0;

    // Energy level where agent stops looking for food
    public static final double MAX_ENERGY_EAT = 0.5;

    public static final double LIVING_ENERGY = 0.005;

}