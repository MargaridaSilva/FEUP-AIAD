package behaviours.animals.move;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import agents.AnimalAgent;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.core.AID;
import sajas.core.Agent;
import utils.Communication;
import utils.Locator;
import utils.Position;

public class MoveToGoal extends Move {

    private Position goalPosition;

    public MoveToGoal(MoveManager moveManager, Agent a, Position goalPosition) {
        super(moveManager, a, new ArrayList<>(Arrays.asList(0,1,2,3)));
        this.goalPosition = goalPosition;
    }

    @Override
    protected Position getNextPossiblePosition() {

        AnimalAgent agent = (AnimalAgent) this.myAgent;
        Position currentPosition = agent.getPosition();
        double lowerDistance = Double.MAX_VALUE;
        Position nextPosition = null;
        
        for (Integer moveIndex : remainingMoves) {
            int[] move = MOVES[moveIndex];
            Position nextPossiblePosition = getMovePosition(move, currentPosition);
            double distToGoal = goalPosition.getDist(nextPossiblePosition);
            if(distToGoal < lowerDistance) {
                nextPosition = nextPossiblePosition;
                lowerDistance = distToGoal;
            }
        }
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
        msg.setOntology(Communication.Ontology.VALIDATE_MOVE_GOAL);
        try {
            ArrayList<Position> content = new ArrayList<>(Arrays.asList(possiblePosition, goalPosition));
            msg.setContentObject(content);
        } catch(IOException e) {
            e.printStackTrace();
        }

        return msg;
    }
}