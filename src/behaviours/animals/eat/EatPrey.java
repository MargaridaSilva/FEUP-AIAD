package behaviours.animals.eat;

import jade.lang.acl.ACLMessage;
import jade.core.AID;
import sajas.core.Agent;
import utils.Communication;

public class EatPrey extends Eat{

    public EatPrey(Agent a, ACLMessage msg) {
        super(a, msg);
    }

    public static ACLMessage prepareRequest(Agent agent, AID prey) {
        return Eat.prepareRequest(agent, prey, Communication.Ontology.EAT_PREY, null);
    }
}
