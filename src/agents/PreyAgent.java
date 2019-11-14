package agents;

import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import launchers.EnvironmentLauncher;
import sajas.core.behaviours.CyclicBehaviour;
import sajas.domain.DFService;
import utils.Position;
import behaviours.Navigate;

import java.util.Random;

public class PreyAgent extends AnimalAgent{

    private PreyAgent(EnvironmentLauncher model, Position position, float energyExpenditure) {
        super(model, position, energyExpenditure);
        //this.node.setColor(this.color);
    }

    public static PreyAgent generatePreyAgent(EnvironmentLauncher model, Position position) {
        Random random = new Random();
        float energyExpenditure = random.nextFloat();
        return new PreyAgent(model, position, energyExpenditure);
    }

    @Override
    protected void setup() {

        super.setup();
        super.addBehaviour(new Navigate(this,100));

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
