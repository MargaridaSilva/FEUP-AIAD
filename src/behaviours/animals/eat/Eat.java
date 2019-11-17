package behaviours.animals.eat;

import agents.AnimalAgent;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.core.AID;
import sajas.core.Agent;
import sajas.proto.AchieveREInitiator;
import utils.Communication;
import utils.MessageConstructor;

import java.io.Serializable;

public class Eat extends AchieveREInitiator{

    public Eat(Agent a, ACLMessage msg) {
        super(a, msg);
    }

    public static ACLMessage prepareRequest(Agent agent, AID target, String ontology, Serializable content) {

        ACLMessage msg = MessageConstructor.getMessage(
                target,
                ACLMessage.INFORM,
                FIPANames.InteractionProtocol.FIPA_REQUEST,
                ontology,
                Communication.Language.FOOD,
                content);

        return msg;
    }

    @Override
    protected void handleInform(ACLMessage inform){
        String energy_str = inform.getContent();
        Double energy = Double.parseDouble(energy_str);
        AnimalAgent animalAgent = (AnimalAgent) myAgent;
        animalAgent.setEnergy(animalAgent.getEnergy() + energy);
    }
}
