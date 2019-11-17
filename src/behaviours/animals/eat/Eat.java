package behaviours.animals.eat;

import agents.AnimalAgent;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import sajas.core.AID;
import sajas.core.Agent;
import sajas.proto.AchieveREInitiator;
import utils.Communication;
import utils.Locator;
import utils.MessageConstructor;

public class Eat extends AchieveREInitiator{


    public Eat(Agent a, ACLMessage msg) {
        super(a, msg);
    }

    public static ACLMessage prepareRequest(Agent agent, AID prey) {

        ACLMessage msg = MessageConstructor.getMessage(
                prey,
                ACLMessage.INFORM,
                FIPANames.InteractionProtocol.FIPA_REQUEST,
                Communication.Ontology.EAT_PREY,
                Communication.Language.FOOD,
                null);

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
