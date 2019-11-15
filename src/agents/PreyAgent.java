package agents;

import launchers.EnvironmentLauncher;
import uchicago.src.sim.gui.RectNetworkItem;
import utils.Position;

import behaviours.BehaviourManager;

public class PreyAgent extends AnimalAgent {

    private PreyAgent(EnvironmentLauncher model, String id, Position position, Gender gender) {

        super(model, id, position, gender);
    }

    public static PreyAgent generatePreyAgent(EnvironmentLauncher model, String id, Position position, Gender gender) {
        
        return new PreyAgent(model, id, position, gender);
    }

    public void createNode(Position position, String label) {
        int density = model.getBoardDensity();
        RectNetworkItem rect = new RectNetworkItem(position.x, position.y);
        rect.allowResizing(false);
        rect.setHeight(density);
        rect.setWidth(density);
        this.node = generateDrawableNode(rect, label);
    }

    @Override
    protected void setup() {

        super.addBehaviour(new BehaviourManager(this));

        System.out.println("Prey-agent "+ getAID().getName()+" is ready.");
        this.registerService("prey-service", "prey-name", new String[]{},  new String[]{});
    }

    @Override
    protected void takeDown() {
        super.takeDown();
        
        this.deRegisterServices();

        System.out.println("Prey-agent " + this.getAID() + " terminating");
    }

}
