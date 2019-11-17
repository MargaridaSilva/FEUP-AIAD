package behaviours.animals.eat;

import agents.AnimalAgent;
import agents.PredatorAgent;
import agents.PreyAgent;
import behaviours.animals.BehaviourManager;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import sajas.core.Agent;
import sajas.core.behaviours.Behaviour;
import sajas.proto.AchieveREResponder;
import utils.Communication;

public class WaitPredator extends AchieveREResponder {

    private static final MessageTemplate template = MessageTemplate.and(
            MessageTemplate.and(MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST),
                    MessageTemplate.MatchPerformative(ACLMessage.INFORM)),
            MessageTemplate.MatchOntology(Communication.Ontology.EAT_PREY));

    public WaitPredator(Agent prey) {
        super(prey, template);
    }

    @Override
    protected ACLMessage handleRequest(ACLMessage request) throws NotUnderstoodException, RefuseException {
        PreyAgent prey = (PreyAgent) myAgent;
        Double energy = prey.getEnergy();
        prey.setEnergy(0);
        ACLMessage inform = request.createReply();
        inform.setPerformative(ACLMessage.INFORM);
        inform.setContent(energy.toString());
        return inform;
    }

}