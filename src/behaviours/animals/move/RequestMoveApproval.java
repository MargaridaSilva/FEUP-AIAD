package behaviours.animals.move;

import java.util.ArrayList;
import java.util.Arrays;

import agents.AnimalAgent;
import jade.lang.acl.ACLMessage;
import sajas.core.Agent;
import sajas.proto.ProposeInitiator;
import utils.Position;

public class RequestMoveApproval extends ProposeInitiator {

    private Position possiblePosition;
    private ArrayList<Integer> remainingMoves;
    private RandomManager randomManager;

    public RequestMoveApproval(Agent agent, ACLMessage msg, RandomManager randomManager, Position possiblePosition,
            ArrayList<Integer> remainingMoves) {
                super(agent, msg);
                
        this.possiblePosition = possiblePosition;
        this.randomManager = randomManager;
        this.remainingMoves = remainingMoves;
    }

    @Override
    protected void handleAcceptProposal(ACLMessage accept_proposal) {

        super.handleAcceptProposal(accept_proposal);

        AnimalAgent agent = (AnimalAgent) this.myAgent;

        agent.setPosition(this.possiblePosition);

        ArrayList<Integer> possibleMoves = new ArrayList<>(Arrays.asList(0,1,2,3));
        this.randomManager.setMoveCompleted(possibleMoves);
        this.randomManager.removeSubBehaviour(this);
    }

    @Override
    protected void handleRejectProposal(ACLMessage reject_proposal) {
        
        super.handleRejectProposal(reject_proposal);
        this.randomManager.setMoveCompleted(remainingMoves);
        this.randomManager.removeSubBehaviour(this);
    }
}