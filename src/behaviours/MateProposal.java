package behaviours;

import java.util.Enumeration;
import java.util.Vector;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import sajas.core.Agent;
import sajas.proto.ContractNetInitiator;

public class MateProposal extends ContractNetInitiator {

    private Navigate parentBehaviour;

    public MateProposal(Agent agent, ACLMessage cfp, Navigate parentBehaviour) {
        super(agent, cfp);
        this.parentBehaviour = parentBehaviour;
    }

    @Override
    protected void handleAllResponses(Vector responses, Vector acceptances) {
        
        // Evaluate all proposals
        float bestProposal = -1;
        Enumeration e = responses.elements();
        ACLMessage accept = null;

        while(e.hasMoreElements()) {
            ACLMessage msg = (ACLMessage) e.nextElement();

            if (msg.getPerformative() == ACLMessage.PROPOSE) {
                ACLMessage reply = msg.createReply();
                reply.setPerformative(ACLMessage.REJECT_PROPOSAL);
                acceptances.addElement(reply);
                float proposal = 1 - Float.parseFloat(msg.getContent());
                if (proposal > bestProposal) {
                    bestProposal = proposal;
                    accept = reply;
                }
            }
            
            
        }
        // Accept the proposal of the best proposer
        if (accept != null) {
            accept.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
        }
    }
}