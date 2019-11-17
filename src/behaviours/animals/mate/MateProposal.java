package behaviours.animals.mate;

import java.util.Enumeration;
import java.util.Vector;

import jade.lang.acl.ACLMessage;
import sajas.core.Agent;
import sajas.proto.ContractNetInitiator;

public class MateProposal extends ContractNetInitiator {

    private FemaleMateManager mateManager;

    public MateProposal(Agent agent, ACLMessage cfp, FemaleMateManager mateManager) {
        super(agent, cfp);
        this.mateManager = mateManager;
    }

    @Override
    protected void handleAllResponses(Vector responses, Vector acceptances) {

        // Evaluate all proposals
        float bestProposal = -1;
        Enumeration e = responses.elements();
        ACLMessage accept = null;

        while(e.hasMoreElements()) {
            System.out.println("|||| received propose");
            ACLMessage msg = (ACLMessage) e.nextElement();

            if (msg.getPerformative() == ACLMessage.PROPOSE) {
                ACLMessage reply = msg.createReply();
                reply.setPerformative(ACLMessage.REJECT_PROPOSAL);
                float proposal = 1 - Float.parseFloat(msg.getContent());
                if (proposal > bestProposal) {
                    acceptances.addElement(reply);
                    bestProposal = proposal;
                    accept = reply;
                }
            }
        }

        // Accept the proposal of the best proposer
        if (accept != null) {
            System.out.println("|||| goind to wait");
            accept.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
            this.mateManager.setWaitMaleState();
        }
    }
}