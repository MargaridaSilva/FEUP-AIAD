package behaviours;

import agents.ObserverAgent;
import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import sajas.core.Agent;
import sajas.proto.ProposeResponder;
import utils.Position;

public class MoveApproval extends ProposeResponder {

    private static final MessageTemplate template = MessageTemplate.and(MessageTemplate.and(
  		    MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_PROPOSE),
              MessageTemplate.MatchPerformative(ACLMessage.PROPOSE)), MessageTemplate.MatchOntology("position"));

    public MoveApproval(Agent agent) {
        this(agent, template);
    }

    public MoveApproval(Agent agent, MessageTemplate messageTemplate) {
        super(agent, messageTemplate);
    }

    @Override
    protected ACLMessage prepareResponse(ACLMessage propose) throws NotUnderstoodException, RefuseException {

        Position content = null;
        try {
            content = (Position)propose.getContentObject();
        } catch (UnreadableException e) {
            e.printStackTrace();
        }
        
        ACLMessage reply = propose.createReply();
        boolean positionTaken = ((ObserverAgent)this.myAgent).isPositionTaken(content);
        boolean positionOutLimits = ((ObserverAgent)this.myAgent).isPositionOutLimits(content);
        
        if(positionTaken || positionOutLimits) {
            reply.setPerformative(ACLMessage.REJECT_PROPOSAL);
        } else {
            reply.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
        }
        return reply;
    }
}