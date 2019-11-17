package behaviours.observer;

import java.util.ArrayList;

import agents.ObserverAgent;
import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import sajas.core.Agent;
import sajas.proto.ProposeResponder;
import utils.Communication;
import utils.Position;

public class MoveApproval extends ProposeResponder {

    private static final MessageTemplate template = MessageTemplate.and(
            MessageTemplate.and(MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_PROPOSE),
                    MessageTemplate.MatchPerformative(ACLMessage.PROPOSE)),
            MessageTemplate.or(MessageTemplate.MatchOntology(Communication.Ontology.VALIDATE_MOVE_GOAL),
                    MessageTemplate.MatchOntology(Communication.Ontology.VALIDATE_MOVE)));

    public MoveApproval(Agent agent) {
        this(agent, template);
    }

    public MoveApproval(Agent agent, MessageTemplate messageTemplate) {
        super(agent, messageTemplate);
    }

    @Override
    protected ACLMessage prepareResponse(ACLMessage propose) throws NotUnderstoodException, RefuseException {

        ACLMessage reply = propose.createReply();
        Position position = null;
        ObserverAgent observer = (ObserverAgent) this.myAgent;

        try {
            if (propose.getOntology() == Communication.Ontology.VALIDATE_MOVE_GOAL) {
                ArrayList<Position> content = (ArrayList<Position>) propose.getContentObject();
                
                if (content.get(0).equals(content.get(1))) {
                    reply.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
                    observer.updateAgentPosition(propose.getSender(), content.get(0));
                    return reply;
                }
                position = content.get(0);

            } else
                position = (Position) propose.getContentObject();
        } catch (UnreadableException e) {
            e.printStackTrace();
        }

        boolean positionTaken = observer.isPositionTaken(position);
        boolean positionOutLimits = observer.isPositionOutLimits(position);

        if (positionTaken || positionOutLimits) {
            
            reply.setPerformative(ACLMessage.REJECT_PROPOSAL);
        } else {
            observer.updateAgentPosition(propose.getSender(), position);
            reply.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
        }

        return reply;
    }
}