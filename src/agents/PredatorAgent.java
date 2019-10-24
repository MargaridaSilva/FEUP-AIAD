package agents;

import java.awt.*;
import java.util.Random;

import behaviours.Walk;
import launchers.EnvironmentLauncher;
import uchicago.src.sim.gui.Drawable;

/**
 * A class to represent a Predator agent
 */
public final class PredatorAgent extends AnimalAgent {

    private PredatorAgent(EnvironmentLauncher model, int[] position, float energyExpenditure) {
        super(model, position, energyExpenditure);
        //this.node.setColor(this.color);

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