package agents;

import simulation.PredatorPreyModel;
import simulation.Space;
import uchicago.src.sim.gui.SimGraphics;
import utils.Position;

import behaviours.BehaviourManager;

public class PreyAgent extends AnimalAgent {

    private PreyAgent(PredatorPreyModel model, Space space, String id, Position position, Gender gender) {

        super(model, space, id, position, gender);
    }

    public static PreyAgent generatePreyAgent(PredatorPreyModel model, Space space, String id, Position position, Gender gender) {
        
        return new PreyAgent(model, space, id, position, gender);
    }

    @Override
    protected void setup() {

        super.addBehaviour(new BehaviourManager(this));

        System.out.println("Prey-agent "+ getAID().getName()+" is ready.");
        this.registerService("prey-service", "prey-name", new String[]{},  new String[]{});
    }

    // Drawable interface
    public int getX() {
        return position.x;
    }

    public int getY() {
        return position.y;
    }
  
    public void draw(SimGraphics g) {
        g.drawFastOval(color);
    }

    @Override
    protected void takeDown() {
        
        super.takeDown();
        
        this.deRegisterServices();

        System.out.println("Prey-agent " + this.getAID() + " terminating");
    }

}
