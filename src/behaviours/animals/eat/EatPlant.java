package behaviours.animals.eat;

import agents.AnimalAgent;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.core.AID;
import sajas.core.Agent;
import sajas.proto.AchieveREInitiator;
import utils.Communication;
import utils.Configs;
import utils.MessageConstructor;
import utils.Position;

import java.io.Serializable;

public class EatPlant extends Eat{

    public EatPlant(Agent a, ACLMessage msg) {
        super(a, msg);
    }

    public static ACLMessage prepareRequest(Agent agent, AID observer, Position plantPosition) {
        return Eat.prepareRequest(agent, observer, Communication.Ontology.EAT_PLANT, plantPosition);
    }

}
