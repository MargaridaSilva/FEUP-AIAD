package behaviours.animals.move;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import agents.AnimalAgent;
import sajas.core.behaviours.Behaviour;
import utils.Communication;
import utils.Locator;
import utils.Position;
import jade.core.AID;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import sajas.core.Agent;

public class Move extends Behaviour {

    protected RandomManager randomManager;
    private final int[][] MOVES = {{1,0}, {-1,0}, {0,1}, {0,-1}};
    private ArrayList<Integer> remainingMoves;
    private RequestMoveApproval requestApprovalBehaviour;
    private boolean finish;

    public Move(RandomManager randomManager, Agent agent, ArrayList<Integer> remainingMoves) {
        super(agent);
        this.randomManager = randomManager;
        this.remainingMoves = remainingMoves;
        this.requestApprovalBehaviour = null;
        this.finish = false;
    }

    @Override
    public void action() {
        
        // Generate new move
        Position nextPossiblePosition = this.getNextPossiblePosition();

        if(remainingMoves.size() == 0) 
        { 
            this.finish = true;
            return;
        }

        ACLMessage proposal = this.getProposalMessage(nextPossiblePosition);
        requestApprovalBehaviour = new RequestMoveApproval(this.myAgent, proposal, this.randomManager, nextPossiblePosition, remainingMoves);
        this.randomManager.addSubBehaviour(requestApprovalBehaviour);
        this.finish = true;
    }

    @Override
    public boolean done() {
        
        return finish;
    }

    protected Position getNextPossiblePosition() {

        AnimalAgent agent = (AnimalAgent) this.myAgent;

        Random random = new Random(System.currentTimeMillis());

        int randomIndex = random.nextInt(remainingMoves.size());
        int[] move = this.MOVES[remainingMoves.get(randomIndex)];
        this.remainingMoves.remove(randomIndex);
        Position currentPosition = agent.getPosition();
        return getMovePosition(move, currentPosition);
    }

    protected Position getMovePosition(int[] move, Position initialPosition) {

        Position nextPosition = new Position(initialPosition.x + move[0], initialPosition.y + move[1]);
        return nextPosition;
    }

    protected ACLMessage getProposalMessage(Position possiblePosition) {
        
        // locate the Observer agent
        AID observerAgentName =  Locator.findObserver(myAgent);

        if(observerAgentName == null) {
            System.out.println("Predator-agent " + this.myAgent.getAID().getName() + " can't find the Observer agent.");
            return null;
        }

        // prepare Propose message
        ACLMessage msg = new ACLMessage(ACLMessage.PROPOSE);
        msg.addReceiver(observerAgentName);
        msg.setProtocol(FIPANames.InteractionProtocol.FIPA_PROPOSE);
        msg.setOntology(Communication.Ontology.VALIDATE_MOVE);
        try {
            msg.setContentObject(possiblePosition);
        } catch(IOException e) {
            e.printStackTrace();
        }

        return msg;
    }
}