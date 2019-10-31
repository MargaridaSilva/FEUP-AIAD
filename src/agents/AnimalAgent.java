package agents;

import launchers.EnvironmentLauncher;
import sajas.core.Agent;
import uchicago.src.sim.gui.Drawable;
import uchicago.src.sim.gui.SimGraphics;
import uchicago.src.sim.network.DefaultDrawableNode;

import java.awt.*;

/**
 * A class to represent an Animal agent
 */
public abstract class AnimalAgent extends Agent {

    public EnvironmentLauncher model;
    protected int[] position;
    protected float energy;
    protected float energyExpenditure;
    public DefaultDrawableNode node;

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

    public int[] getPosition() {
        return position;
    }

    public void setX(int value) {
        this.position[0] = value;
    }

    public void setY(int value) {
        this.position[1] = value;
    }

    public void setPosition(int[] position) {
        this.position = position;
    }

    /*@Override
    public void draw(SimGraphics simGraphics) {
        simGraphics.setDrawingCoordinates(getX(), getY(), 0);
        //scaling the circles
        //simGraphics.setDrawingParameters(10, 10, 1);
        simGraphics.drawCircle(this.color);
        //simGraphics.fillPolygon(this.color);
    }*/

    public void setNode(DefaultDrawableNode node) {
        this.node = node;
    }

    

    
}