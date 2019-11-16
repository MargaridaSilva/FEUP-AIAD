package behaviours;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Vector;

import agents.AnimalAgent;
import jade.core.AID;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import sajas.core.Agent;
import sajas.proto.IteratedAchieveREInitiator;
import utils.Communication;
import utils.Locator;
import utils.MessageConstructor;
import utils.Position;

public class Move extends IteratedAchieveREInitiator {

    protected static final int[][] MOVES = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };
    protected Position nextPosition;
    protected boolean moveApproved;
    protected ArrayList<Integer> remainingMoves;

    public Move(Agent a) {
        this(a, false);
    }

    /***
     * Useful to skip call to firstMove()
     */
    public Move(Agent a, boolean skipFirstMove) {
        super(a, null);
        
        this.remainingMoves = new ArrayList<>(Arrays.asList(0, 1, 2, 3));
        this.nextPosition = null;
        this.moveApproved = false;
        if(!skipFirstMove)
            this.firstMove();
    }

    protected void firstMove() {

        getMove();
        ACLMessage firstRequestMessage = getProposalMessage(nextPosition);
        this.reset(firstRequestMessage);
    }

    @Override
    protected void handleAllResultNotifications(Vector resultNotifications, Vector nextRequests) {

        if (resultNotifications.isEmpty())
            return;

        ACLMessage response = (ACLMessage) resultNotifications.get(0);

        if (response.getPerformative() == ACLMessage.INFORM) {
            this.moveApproved = true;
            
            ((AnimalAgent) myAgent).setPosition(this.nextPosition);
            return;
        } else if (response.getPerformative() == ACLMessage.FAILURE) {
            getMove();
            ACLMessage newRequestMessage = getProposalMessage(nextPosition);
            nextRequests.addElement(newRequestMessage);
        }
    }
    
    @Override
    protected boolean checkTermination(boolean currentDone, int currentResult) {
        
        boolean terminated = false;
        
        if(remainingMoves.isEmpty()) {
            System.out.println("Agent " + myAgent.getAID() + " can't move to any position.");
            terminated = true;
        }
        else if(moveApproved)
            terminated = true;

        return terminated;
    }

    protected void getMove() {

        if(remainingMoves.isEmpty())  // all positions are taken
            return;

        Random random = new Random();
        int randomIndex = random.nextInt(remainingMoves.size());
        int[] move = MOVES[remainingMoves.get(randomIndex)];
        this.remainingMoves.remove(randomIndex);
        getNextPosition(move);
    }

    protected void getNextPosition(int[] move) {
        AnimalAgent agent = (AnimalAgent) this.myAgent;
        this.nextPosition = new Position(agent.getX() + move[0], agent.getY() + move[1]);
    }

    protected ACLMessage getProposalMessage(Position possiblePosition) {

        // prepare request message
        String ontology;
        Serializable content = possiblePosition;

        AID observerAgent = Locator.findObserver(myAgent);
        if (observerAgent == null) {
            System.out.println("Predator-agent " + this.myAgent.getAID().getName() + " can't find the Observer agent.");
            return null;
        }

        ontology = Communication.Ontology.VALIDATE_MOVE;
        ACLMessage msg = MessageConstructor.getMessage(observerAgent, ACLMessage.REQUEST,
                FIPANames.InteractionProtocol.ITERATED_FIPA_REQUEST, ontology, Communication.Language.MOVE, content);
        return msg;
    }
}