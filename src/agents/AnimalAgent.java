package agents;

import simulation.PredatorPreyModel;
import simulation.Space;
import uchicago.src.sim.gui.Drawable;
import utils.Communication;
import utils.Configs;
import utils.Locator;
import utils.Position;

import java.awt.*;
import java.util.Random;

import behaviours.animals.BehaviourManager;
import jade.core.AID;
import jade.lang.acl.ACLMessage;

/**
 * A class to represent an Animal agent
 */
public abstract class AnimalAgent extends GenericAgent implements Drawable {

    public enum Gender {MALE, FEMALE}
    protected Position position;
    protected double energy;
    protected double energyExpenditure;
    protected Gender gender;
    protected Color color;
    protected Space space;

    protected AnimalAgent(PredatorPreyModel model, Space space, String id, Position position, Gender gender) {
        super(model);
        this.space = space;
        this.position = position;
        this.gender = gender;
        this.color = getDefaultColor();

        Random random = new Random();

        // random number in [0.5 , 1.0]
        this.setEnergy(0.5 + 0.5 * random.nextDouble());

        // random number in [MIN_ENERGY_EXP, MAX_ENERGY_EXP]
        this.energyExpenditure = Configs.MIN_ENERGY_EXP + (Configs.MAX_ENERGY_EXP - Configs.MIN_ENERGY_EXP) * random.nextDouble();
    }

    @Override
    protected void setup() {
        super.setup();
        this.addBehaviour(new BehaviourManager(this));
    }

    @Override
    protected void takeDown() {
        super.takeDown();
        model.removeElement(this);
        informObserverDelete();
    }

    /**
     * Inform the Observer agent that this agent is going to terminate
     */
    private void informObserverDelete() {
        AID observerAgent = Locator.findObserver(this);
        ACLMessage terminateMsg = new ACLMessage(ACLMessage.INFORM);
        terminateMsg.setOntology(Communication.Ontology.TERMINATE);
        terminateMsg.addReceiver(observerAgent);
        this.send(terminateMsg);
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
        
        this.position = position.clone();
        //this.decreaseEnergy();
    }

    protected void setNodeColor(Color color) {
        this.color = color;
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