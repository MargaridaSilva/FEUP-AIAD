package behaviours.animals.mate;

import java.util.ArrayList;

import agents.AnimalAgent;
import behaviours.animals.BehaviourManager;
import jade.core.AID;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import sajas.core.Agent;
import sajas.core.behaviours.Behaviour;
import utils.Communication;
import utils.Locator;
import utils.MessageConstructor;

public class CallToMate extends Behaviour {

    private boolean finished;
    private BehaviourManager parentBehaviour;

    public CallToMate(Agent femaleAgent, BehaviourManager parentBehaviour) {
        super(femaleAgent);
        this.finished = false;
        this.parentBehaviour = parentBehaviour;
    }
    @Override
    public void action() {
        ArrayList<AID> malePredators = Locator.locate(this.myAgent, Communication.ServiceType.PREDATOR_MATE);
        ACLMessage msg = MessageConstructor.getMessage(malePredators, 
                                                    ACLMessage.CFP, 
                                                    FIPANames.InteractionProtocol.FIPA_CONTRACT_NET, 
                                                    Communication.Ontology.PREDATOR_FIND_MATE, 
                                                    Communication.Language.PREDATOR_MATE, 
                                                    ((AnimalAgent)this.myAgent).getPosition());
        this.parentBehaviour.addSubBehaviour(new MateProposal(this.myAgent, msg, this.parentBehaviour));
        this.finished = true;
    }

    @Override
    public boolean done() {
        return this.finished;
    }


    

}