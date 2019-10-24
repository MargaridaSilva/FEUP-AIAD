package agents;

import launchers.EnvironmentLauncher;

import java.util.Random;

public class PreyAgent extends AnimalAgent{

    private PreyAgent(EnvironmentLauncher model, int[] position, float energyExpenditure) {
        super(model, position, energyExpenditure);
        //this.node.setColor(this.color);
    }

    public static PreyAgent generatePreyAgent(EnvironmentLauncher model, int[] position) {
        Random random = new Random();
        float energyExpenditure = random.nextFloat();
        return new PreyAgent(model, position, energyExpenditure);
    }

    protected void setup() {
        // Printout a welcome message
        System.out.println("Hallo! Prey-agent "+ getAID().getName()+" is ready.");
    }
}
