package behaviours.observer;


import java.util.ArrayList;

import agents.ObserverAgent;
import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.FailureException;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import sajas.core.Agent;
import sajas.proto.AchieveREResponder;
import utils.Communication;
import utils.Position;

public class MoveApproval extends AchieveREResponder {

    private static final MessageTemplate template = MessageTemplate.and(
            MessageTemplate.and(MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.ITERATED_FIPA_REQUEST),
                    MessageTemplate.MatchPerformative(ACLMessage.REQUEST)),
            MessageTemplate.or(MessageTemplate.MatchOntology(Communication.Ontology.VALIDATE_MOVE_GOAL),
                    MessageTemplate.MatchOntology(Communication.Ontology.VALIDATE_MOVE)));

    public MoveApproval(Agent agent) {
        this(agent, template);
    }

    public MoveApproval(Agent agent, MessageTemplate messageTemplate) {
        super(agent, messageTemplate);
    }

    @Override
    protected ACLMessage handleRequest(ACLMessage request) throws NotUnderstoodException, RefuseException {

        ACLMessage reply = request.createReply();
        reply.setPerformative(ACLMessage.AGREE);
        return reply;
    }

    @Override
    protected ACLMessage prepareResultNotification(ACLMessage request, ACLMessage response) throws FailureException {
       
        ACLMessage reply = request.createReply();
        Position position = null;
        ObserverAgent observer = (ObserverAgent) this.myAgent;

        try {
            if (request.getOntology() == Communication.Ontology.VALIDATE_MOVE_GOAL) {
                ArrayList<Position> content = (ArrayList<Position>) request.getContentObject();

                if (content.get(0) == content.get(1)) {
                    reply.setPerformative(ACLMessage.INFORM);
                    observer.updateAgentPosition(request.getSender(), content.get(0));
                    return reply;
                }
                position = content.get(0);

            } else
                position = (Position) request.getContentObject();
        } catch (UnreadableException e) {
            e.printStackTrace();
        }

        boolean positionTaken = observer.isPositionTaken(position);
        boolean positionOutLimits = observer.isPositionOutLimits(position);

        if (positionTaken || positionOutLimits) {
            reply.setPerformative(ACLMessage.FAILURE);
        } else {
            reply.setPerformative(ACLMessage.INFORM);
            observer.updateAgentPosition(request.getSender(), position);
        }

        return reply;
    }
}