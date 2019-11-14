package agents;

import behaviours.mate.AnswerMateRequest;
import behaviours.mate.CallToMate;
import behaviours.BehaviourManager;
import launchers.EnvironmentLauncher;
import utils.Communication;
import utils.Position;

/**
 * A class to represent a Predator agent
 */
public final class PredatorAgent extends AnimalAgent {

    private PredatorAgent(EnvironmentLauncher model, Position position, Gender gender) {
        super(model, position, gender);
    }

    public static PredatorAgent generatePredatorAgent(EnvironmentLauncher model, Position position, Gender gender) {

        return new PredatorAgent(model, position, gender);
    }

    @Override
    protected void setup() {
        super.setup();

        // register services
        this.registerServices();

        super.addBehaviour(new BehaviourManager(this));

		System.out.println("Predator-agent "+ this.getAID().getName()+" is ready.");
    }

    private void registerServices() {
        if(this.gender == Gender.MALE) {
            this.registerService(Communication.ServiceType.PREDATOR_MATE, 
                             Communication.ServiceName.PREDATOR_REPRODUCTION, 
                             new String[]{Communication.Language.PREDATOR_MATE}, 
                             new String[]{Communication.Ontology.PREDATOR_FIND_MATE});
        }
    }

    @Override
    protected void takeDown() {
        super.takeDown();
        
        this.deRegisterServices();

        System.out.println("Predator-agent " + this.getAID() + " terminating");
    }

/*
    private class UpdatePreyList extends TickerBehaviour{

        public UpdatePreyList(PredatorAgent predatorAgent, int i) {
            super(predatorAgent, i);
        }

        @Override
        protected void onTick() {

            // Update the list of prey agents
            DFAgentDescription template = new DFAgentDescription();
            ServiceDescription sd = new ServiceDescription();
            sd.setType("prey-service");
            template.addServices(sd);

            try {
                DFAgentDescription[] result = DFService.search(myAgent, template);


                System.out.println("Found the following prey agents:");

                preyAgentList = new AID[result.length];

                for (int i = 0; i < result.length; ++i) {
                    preyAgentList[i] = result[i].getName();
                    System.out.println(preyAgentList[i].getName());
                }
               System.out.println();

            }
            catch (FIPAException fe) {
                fe.printStackTrace();
            }
        }
    }*/

}