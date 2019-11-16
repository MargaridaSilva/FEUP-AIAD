package behaviours.animals.eat;

import jade.core.AID;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
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
        ACLMessage getFoodRequest = prepareRequest(a);
        this.reset(getFoodRequest);
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

        ontology = Communication.Ontology.TELL_FOOD;
        ACLMessage msg = MessageConstructor.getMessage(observerAgent, ACLMessage.REQUEST,
                FIPANames.InteractionProtocol.FIPA_REQUEST, ontology, Communication.Language.FOOD, content);
        return msg;
    }

    @Override
    protected void handleInform(ACLMessage inform){
        try {
            Serializable positions = inform.getContentObject();
            parentBehaviour.food = new Position(0,0);
        } catch (UnreadableException e) {
            e.printStackTrace();
        }
    }
}
