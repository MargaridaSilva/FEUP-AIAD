package agents;

import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import launchers.EnvironmentLauncher;
import sajas.core.Agent;
import sajas.domain.DFService;

/**
 * Each world has an Observer agent which keeps track of the world's state,
 * namely the disposal of the agents in the grid. The Observer agent is not part
 * of the world, it just exchanges messages with the other agents, which can
 * request information regarding the state of the world around them.
 */
public class ObserverAgent extends GenericAgent {

    public ObserverAgent(EnvironmentLauncher model) {
        super(model);
    }

    @Override
    protected void setup() {
        super.setup();

        this.registerService("validate-move", "track-world", new String[]{"move-language"}, new String[]{"validate-move-ontology"});
        
        System.out.println("Observer-agent "+ getAID().getName()+" is ready.");
    }

    @Override
    protected void takeDown() {
        super.takeDown();
        
        this.deRegisterServices();

        System.out.println("Observer-agent " + this.getAID() + " terminating");
    }



}