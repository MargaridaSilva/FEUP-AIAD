package agents;

import launchers.EnvironmentLauncher;
import sajas.core.Agent;

/**
 * A class to represent an Animal agent
 */
public abstract class AnimalAgent extends Agent {

    protected EnvironmentLauncher model;
    protected float energy;
    protected float energyExpenditure;
    protected Integer[] coordinates;

    protected AnimalAgent(EnvironmentLauncher model, float energyExpenditure, Integer[] coordinates) {
        this.model = model;
        this.energy = 1;
        this.energyExpenditure = energyExpenditure;
        this.coordinates = coordinates;
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
        return this.energy;
    }

    public float getEnergyExpenditure() {
        return this.energyExpenditure;
    }

    public Integer[] getCoordinates() {
        return this.coordinates;
    }

    public int getX() {
        return this.coordinates[0];
    }

    public int getY() {
        return this.coordinates[1];
    }
}