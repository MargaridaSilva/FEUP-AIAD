package agents;

import java.util.Random;

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

    private PredatorAgent(EnvironmentLauncher model, Position position, float energyExpenditure, Gender gender) {
        super(model, position, energyExpenditure, gender);
    }

    public static PredatorAgent generatePredatorAgent(EnvironmentLauncher model, Position position, Gender gender) {
        Random random = new Random();
        float energyExpenditure = random.nextFloat();
        return new PredatorAgent(model, position, energyExpenditure, gender);
    }

    @Override
    protected void setup() {
        super.setup();

        // register services
        this.registerServices();

        BehaviourManager agentManagerBehaviour = new BehaviourManager(this);

        // add behaviours
        if(this.gender == Gender.MALE)
            this.addBehaviour(new AnswerMateRequest(this, agentManagerBehaviour));
        
        if(this.gender == Gender.FEMALE)
            agentManagerBehaviour.addSubBehaviour(new CallToMate(this, agentManagerBehaviour));
        
        super.addBehaviour(agentManagerBehaviour);

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


}