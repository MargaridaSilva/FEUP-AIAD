package behaviours.eat;


import behaviours.animals.eat.EatManager;
import jade.core.AID;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import sajas.core.Agent;
import sajas.proto.AchieveREInitiator;
import utils.Communication;
import utils.Locator;
import utils.MessageConstructor;
import utils.Position;

import java.io.Serializable;

public class FindFood extends AchieveREInitiator {

    EatManager parentBehaviour;

    public FindFood(Agent a, ACLMessage msg, EatManager parentBehaviour) {
        super(a, msg);
        this.parentBehaviour = parentBehaviour;
    }

    public static ACLMessage prepareRequest(Agent agent) {

        // prepare request message
        String ontology;
        Serializable content = "";

        AID observerAgent = Locator.findObserver(agent);

        if (observerAgent == null) {
            System.out.println("Predator-agent " + agent.getAID().getName() + " can't find the Observer agent.");
            return null;
        }

        ontology = Communication.Ontology.FIND_FOOD;
        ACLMessage msg = MessageConstructor.getMessage(observerAgent, ACLMessage.REQUEST,
                FIPANames.InteractionProtocol.FIPA_REQUEST, ontology, Communication.Language.FOOD, content);
        return msg;
    }

    @Override
    protected void handleInform(ACLMessage inform){
        System.out.println("RECEIVED OBSERVER MESSAGE");
        parentBehaviour.food = new Position(0,0);
    }
}
