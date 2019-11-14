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

import java.util.Random;

import behaviours.BehaviourManager;

public class PreyAgent extends AnimalAgent{

    private PreyAgent(EnvironmentLauncher model, Position position, Gender gender) {
        super(model, position, gender);
        //this.node.setColor(this.color);
    }

    public static PreyAgent generatePreyAgent(EnvironmentLauncher model, Position position, Gender gender) {
        
        return new PreyAgent(model, position, gender);
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
