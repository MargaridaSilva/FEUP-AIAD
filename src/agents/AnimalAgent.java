package agents;

import launchers.EnvironmentLauncher;
import uchicago.src.sim.gui.NetworkDrawable;
import uchicago.src.sim.network.DefaultDrawableNode;
import utils.Configs;
import utils.Position;

import java.awt.*;
import java.util.Random;

import behaviours.BehaviourManager;

/**
 * A class to represent an Animal agent
 */
public abstract class AnimalAgent extends GenericAgent {

    public enum Gender {MALE, FEMALE}
    protected Position position;
    protected double energy;
    protected double energyExpenditure;
    protected Gender gender;
    public DefaultDrawableNode node;

    protected AnimalAgent(EnvironmentLauncher model, String id, Position position, Gender gender) {
        super(model);
        this.position = position;
        this.gender = gender;

        Random random = new Random();

        // random number in [0.5 , 1.0]
        this.setEnergy(0.5 * random.nextDouble());

        // random number in [MIN_ENERGY_EXP, MAX_ENERGY_EXP]
        this.energyExpenditure = Configs.MIN_ENERGY_EXP + (Configs.MAX_ENERGY_EXP - Configs.MIN_ENERGY_EXP) * random.nextDouble();

        createNode(position, id);
    }

    @Override
    protected void setup() {
        super.setup();
        this.addBehaviour(new BehaviourManager(this));
    }

    @Override
    protected void takeDown() {
        super.takeDown();

        // TODO: inform the Observer agent that he is no longer in the world, so that 
        // the Observer won't register its position
    }

    protected abstract void createNode(Position position, String label);

    protected DefaultDrawableNode generateDrawableNode(NetworkDrawable drawable, String label) {
        Color color = getDefaultColor();
        DefaultDrawableNode node = new DefaultDrawableNode(label, drawable);
        node.setColor(color);
        return node;
    }

    public void decreaseEnergy() {

        this.energy -= energyExpenditure;
        updateNodeColor();
    }

    private Color getDefaultColor() {
        switch(gender) {
            case MALE:
                return Configs.MALE_COLOR;
            case FEMALE:
                return Configs.FEMALE_COLOR;
            default:
                return null;
        }
    }

    public  double getEnergy() {
        return energy;
    }

    public double getEnergyExpenditure() {
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
        int boardDensity = this.model.getBoardDensity();
        this.position = position.clone();
        this.node.setX(boardDensity * position.x);
        this.node.setY(boardDensity * position.y);
        this.decreaseEnergy();
    }

    protected void setNodeColor(Color color) {
        this.node.setColor(color);
    }

    public void setMateColor() {
        setNodeColor(Color.MAGENTA);
    }

    protected void updateNodeColor() {
        Color defaultColor = getDefaultColor();
        int opacity = (int)Math.round(255 * energy);
        if(opacity < 0)
            opacity = 1;
        Color newColor = new Color(defaultColor.getRed(), defaultColor.getGreen(), defaultColor.getBlue(), opacity);
        setNodeColor(newColor);
    }

    public void setNode(DefaultDrawableNode node) {
        this.node = node;
    }

    public void setEnergy(double energy) {
        this.energy = energy;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}