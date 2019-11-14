package behaviours;

import java.util.ArrayList;
import java.util.Random;

import agents.AnimalAgent;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import java.io.Serializable;
import jade.core.AID;
import sajas.core.Agent;
import sajas.core.behaviours.Behaviour;
import utils.Communication;
import utils.Locator;
import utils.MessageConstructor;
import utils.Position;

public class ChooseNextMove extends Behaviour {

    private boolean finished;
    private BehaviourManager parentBehaviour;
    private Random random;
    private final int[][] MOVES = {{1,0}, {-1,0}, {0,1}, {0,-1}};
    private ArrayList<Integer> remainingMoves;
    private Position goalPosition;
    
    public ChooseNextMove(Agent agent, BehaviourManager parentBehaviour, ArrayList<Integer> remainingMoves) {
        super(agent);
        this.finished = false;
        this.parentBehaviour = parentBehaviour;
        this.random = new Random();
        this.remainingMoves = remainingMoves;
    }

    public ChooseNextMove(Agent agent, BehaviourManager parentBehaviour, ArrayList<Integer> remainingMoves, Position goalPosition) {
        this(agent, parentBehaviour, remainingMoves);
        this.goalPosition = goalPosition;
    }


    @Override
    public void action() {

        if(remainingMoves.size() == 0) 
        { 
            this.finished = true;
            return;
        }
        
        Position possiblePosition = this.getMove();
        ACLMessage proposal = this.getProposalMessage(possiblePosition);
        RequestMoveApproval requestApprovalBehaviour = new RequestMoveApproval(this.myAgent, proposal, this.parentBehaviour, possiblePosition, remainingMoves);
        this.parentBehaviour.addSubBehaviour(requestApprovalBehaviour);
        this.finished = true;
    }

    private Position getMove() {

        int[] move;

        if(this.goalPosition == null)
            move = this.getRandomMove();
        else
            move = this.getMoveTowardsGoal();

        Position possiblePosition = this.getNextPosition(move);
        return possiblePosition;
    }

    private int[] getRandomMove() {
        int randomIndex = random.nextInt(remainingMoves.size());
        int[] nextMove = this.MOVES[remainingMoves.get(randomIndex)];
        this.remainingMoves.remove(randomIndex);
        return nextMove;
    }

    private int[] getMoveTowardsGoal() {

        double lowerDistance = Double.MAX_VALUE;
        int[] nextMove = null;

        for (Integer moveIndex : remainingMoves) {
            int[] move = this.MOVES[moveIndex];
            Position possiblePosition = this.getNextPosition(move);
            double distToGoal = goalPosition.getDist(possiblePosition);
            if(distToGoal < lowerDistance) {
                nextMove = move;
                lowerDistance = distToGoal;
            }
        }
        
        return nextMove;
    }

    private Position getNextPosition(int[] move) {
        AnimalAgent agent = (AnimalAgent) this.myAgent;
        return new Position(agent.getX() + move[0], agent.getY() + move[1]);
    }


    public ACLMessage getProposalMessage(Position possiblePosition) {
        
        // locate the Observer agent
        AID observerAgentName =  Locator.findObserver(this.myAgent);
        if(observerAgentName == null) {
            System.out.println("Predator-agent " + this.myAgent.getAID().getName() + " can't find the Observer agent.");
            return null;
        }

        // prepare Propose message
        String ontology;
        Serializable content = possiblePosition;
        if(this.goalPosition == null) 
            ontology = Communication.Ontology.VALIDATE_MOVE;
        else {
            ontology = Communication.Ontology.VALIDATE_MOVE_GOAL;
            ArrayList<Position> positions = new ArrayList<Position>();
            positions.add(possiblePosition);
            positions.add(this.goalPosition);
            content = positions;
        }

        ACLMessage msg = MessageConstructor.getMessage(observerAgentName, 
                                                        ACLMessage.PROPOSE, 
                                                        FIPANames.InteractionProtocol.FIPA_PROPOSE, 
                                                        ontology,
                                                        Communication.Language.MOVE, 
                                                        content);
        return msg;
    }

    @Override
    public boolean done() {
        return this.finished;
    }
}