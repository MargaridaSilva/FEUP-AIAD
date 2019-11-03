package behaviours;

import java.util.ArrayList;

import agents.AnimalAgent;
import jade.lang.acl.ACLMessage;
import sajas.core.Agent;
import sajas.proto.ProposeInitiator;
import utils.Position;

public class RequestMoveApproval extends ProposeInitiator {

    private Position possiblePosition;
    private ArrayList<Integer> remainingMoves;
    private Navigate parentBehaviour;

    public RequestMoveApproval(Agent agent, ACLMessage msg, Navigate parentBehaviour, Position possiblePosition,
            ArrayList<Integer> remainingMoves) {
        super(agent, msg);
        this.possiblePosition = possiblePosition;
        this.parentBehaviour = parentBehaviour;
        this.remainingMoves = remainingMoves;
    }

    @Override
    protected void handleAcceptProposal(ACLMessage accept_proposal) {
        
        super.handleAcceptProposal(accept_proposal);

        AnimalAgent agent = (AnimalAgent) this.myAgent;

        agent.setPosition(this.possiblePosition);

        agent.node.setX(possiblePosition.x);
        agent.node.setY(possiblePosition.y);
    }

    @Override
    protected void handleRejectProposal(ACLMessage reject_proposal) {
        
        super.handleRejectProposal(reject_proposal);

        ChooseNextMove chooseNextMoveBehaviour = new ChooseNextMove(this.myAgent, this.parentBehaviour, this.remainingMoves);

        this.parentBehaviour.addSubBehaviour(chooseNextMoveBehaviour);
    }


}