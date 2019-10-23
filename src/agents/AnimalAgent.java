package agents;

import launchers.EnvironmentLauncher;
import sajas.core.Agent;

/**
 * A class to represent an Animal agent
 */
public abstract class AnimalAgent extends Agent {

    protected EnvironmentLauncher model;
    protected int[] position;
    protected float energy;
    protected float energyExpenditure;

    protected AnimalAgent(EnvironmentLauncher model, int[] position, float energyExpenditure) {
        this.model = model;
        this.position = position;
        this.energy = 1;
        this.energyExpenditure = energyExpenditure;
    }

    @Override
    protected void setup() {
        super.setup();
    }

    @Override
    protected void takeDown() {
        super.takeDown();
    }

    public  float getEnergy() {
        return energy;
    }

    public float getEnergyExpenditure() {
        return energyExpenditure;
    }

    public int[] getCoordinates() {
        return position;
    }

    public int getX() {
        return position[0];
    }

    public int getY() {
        return position[1];
    }
}