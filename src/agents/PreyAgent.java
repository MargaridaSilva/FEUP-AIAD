package agents;

import simulation.PredatorPreyModel;
import simulation.Space;
import uchicago.src.sim.gui.SimGraphics;
import utils.Communication;
import utils.Position;

import behaviours.animals.BehaviourManager;

public class PreyAgent extends AnimalAgent {

    private PreyAgent(PredatorPreyModel model, Space space, String id, Position position, Gender gender) {

        super(model, space, id, position, gender);
        this.energyExpenditure = model.getEnergy_expenditure_preys();
    }

    public static PreyAgent generatePreyAgent(PredatorPreyModel model, Space space, String id, Position position, Gender gender) {
        
        return new PreyAgent(model, space, id, position, gender);
    }

    @Override
    protected void setup() {

        super.addBehaviour(new BehaviourManager(this));

        // register services
        this.registerServices();
        
        System.out.println("Prey-agent "+ getAID().getName()+" is ready.");
        
    }

    private void registerServices() {
        this.registerService("prey-service", "prey-name", new String[]{},  new String[]{});
        if(this.gender == Gender.MALE) {
            this.registerService(Communication.ServiceType.PREY_MATE, 
                             Communication.ServiceName.REPRODUCTION, 
                             new String[]{Communication.Language.MATE}, 
                             new String[]{Communication.Ontology.FIND_MATE});
        }
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
