package agents;

import launchers.EnvironmentLauncher;
import uchicago.src.sim.gui.Drawable;
import uchicago.src.sim.gui.SimGraphics;
import uchicago.src.sim.network.DefaultDrawableNode;
import utils.Position;

import java.awt.*;

/**
 * A class to represent an Animal agent
 */
public abstract class AnimalAgent extends GenericAgent {

    public enum Gender {MALE, FEMALE}
    protected Position position;
    protected float energy;
    protected float energyExpenditure;
    protected Gender gender;
    public DefaultDrawableNode node;

    protected AnimalAgent(EnvironmentLauncher model, Position position, float energyExpenditure, Gender gender) {
        super(model);
        this.position = position;
        this.energy = 1;
        this.energyExpenditure = energyExpenditure;
        this.gender = gender;
    }

    @Override
    protected void setup() {
        super.setup();
    }

    @Override
    protected void takeDown() {
        super.takeDown();

        // TODO: inform the Observer agent that he is no longer in the world, so that 
        // the Observer won't register its position
    }

    public  float getEnergy() {
        return energy;
    }

    public float getEnergyExpenditure() {
        return energyExpenditure;
    }

    public int getX() {
        return position.x;
    }

    public int getY() {
        return position.y;
    }

    public Position getPosition() {
        return position;
    }

    public void setX(int value) {
        this.position.x = value;
    }

    public void setY(int value) {
        this.position.y = value;
    }

    public void setPosition(Position position) {
        this.position = position.clone();
    }

    public void setMateColor() {
        this.node.setColor(Color.MAGENTA);
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