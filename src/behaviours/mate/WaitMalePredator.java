package behaviours.mate;

import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import sajas.core.Agent;
import sajas.proto.AchieveREResponder;
import utils.Communication;

public class WaitMalePredator extends AchieveREResponder {

    private static final MessageTemplate template = MessageTemplate.and(
            MessageTemplate.and(MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST),
                    MessageTemplate.MatchPerformative(ACLMessage.INFORM)),
            MessageTemplate.MatchOntology(Communication.Ontology.PREDATOR_REACHED_FEMALE));

    public WaitMalePredator(Agent femaleAgent) {
        super(femaleAgent, template);
    }

    @Override
    protected ACLMessage handleRequest(ACLMessage request) throws NotUnderstoodException, RefuseException {
        ACLMessage inform = request.createReply();
        inform.setPerformative(ACLMessage.INFORM);
        return inform;
    }

    


}