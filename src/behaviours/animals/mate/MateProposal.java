package behaviours.animals.mate;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;

import agents.AnimalAgent;
import jade.core.AID;
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

        if(responses.isEmpty()) {
            this.mateManager.resendMateRequest();
            return;
        }

        // Evaluate all proposals
        float bestProposal = -1;
        Enumeration e = responses.elements();
        ACLMessage accept = null;
        AID male = null;

        while (e.hasMoreElements()) {

            ACLMessage msg = (ACLMessage) e.nextElement();

            if (msg.getPerformative() == ACLMessage.PROPOSE) {
                ACLMessage reply = msg.createReply();
                reply.setPerformative(ACLMessage.REJECT_PROPOSAL);
                float proposal = 1 - Float.parseFloat(msg.getContent());
                if (proposal > bestProposal) {
                    acceptances.addElement(reply);
                    bestProposal = proposal;
                    accept = reply;
                    male = msg.getSender();
                }
            }
        }

        // Accept the proposal of the best proposer
        if (accept != null) {
            this.mateManager.setWaitMaleState(male);
            AnimalAgent agent = (AnimalAgent) myAgent;
            accept.setPerformative(ACLMessage.ACCEPT_PROPOSAL);

            try {
                accept.setContentObject(agent.getPosition());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}