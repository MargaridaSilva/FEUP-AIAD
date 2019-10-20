package agents;

import java.util.Random;

import launchers.EnvironmentLauncher;

/**
 * A class to represent a Predator agent
 */
public final class PredatorAgent extends AnimalAgent {

    private PredatorAgent(EnvironmentLauncher model, float energyExpenditure, Integer[] coordinates) {
        super(model, energyExpenditure, coordinates);
    }

    public static PredatorAgent generatePredatorAgent(EnvironmentLauncher model, int BOARD_DIM) {
        Random random = new Random();
        float energyExpenditure = random.nextFloat();
        int x = random.nextInt(BOARD_DIM);
        int y = random.nextInt(BOARD_DIM);
        Integer[] coordinates = new Integer[]{x,y};
        return new PredatorAgent(model, energyExpenditure, coordinates);
    }

    protected void setup() {
        // Printout a welcome message
		System.out.println("Hallo! Buyer-agent "+ getAID().getName()+" is ready.");
    }
}