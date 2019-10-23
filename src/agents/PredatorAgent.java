package agents;

import java.util.Random;

import launchers.EnvironmentLauncher;

/**
 * A class to represent a Predator agent
 */
public final class PredatorAgent extends AnimalAgent {

    private PredatorAgent(EnvironmentLauncher model, int[] position, float energyExpenditure) {
        super(model, position, energyExpenditure);
    }

    public static PredatorAgent generatePredatorAgent(EnvironmentLauncher model, int[] position) {
        Random random = new Random();
        float energyExpenditure = random.nextFloat();
        return new PredatorAgent(model, position, energyExpenditure);
    }

    protected void setup() {
        // Printout a welcome message
		System.out.println("Hallo! Predator-agent "+ getAID().getName()+" is ready.");
    }
}