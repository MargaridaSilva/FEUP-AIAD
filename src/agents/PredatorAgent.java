package agents;

import java.awt.*;
import java.util.Random;

import behaviours.Move;
import behaviours.Navigate;
import launchers.EnvironmentLauncher;
import uchicago.src.sim.gui.Drawable;
import utils.Position;

/**
 * A class to represent a Predator agent
 */
public final class PredatorAgent extends AnimalAgent {

    private PredatorAgent(EnvironmentLauncher model, Position position, float energyExpenditure) {
        super(model, position, energyExpenditure);
        //this.node.setColor(this.color);
    }

    public static PredatorAgent generatePredatorAgent(EnvironmentLauncher model, Position position) {
        Random random = new Random();
        float energyExpenditure = random.nextFloat();
        return new PredatorAgent(model, position, energyExpenditure);
    }

    @Override
    protected void setup() {
        super.setup();

        super.addBehaviour(new Navigate(this,100));

		System.out.println("Predator-agent "+ this.getAID().getName()+" is ready.");
    }

    @Override
    protected void takeDown() {
        super.takeDown();
        
        this.deRegisterServices();

        System.out.println("Predator-agent " + this.getAID() + " terminating");
    }


}