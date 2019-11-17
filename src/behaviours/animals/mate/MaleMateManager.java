package behaviours.animals.mate;

import java.util.ArrayList;
import java.util.Arrays;

import agents.AnimalAgent;
import behaviours.animals.BehaviourManager;
import behaviours.animals.move.Move;
import behaviours.animals.move.MoveManager;
import behaviours.animals.move.MoveToGoal;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import sajas.core.behaviours.Behaviour;
import sajas.core.behaviours.TickerBehaviour;
import utils.Communication;
import utils.Configs;
import utils.Position;

public class MaleMateManager extends TickerBehaviour implements MoveManager {

    private static enum State {
        RANDOM_MOVE, MOVE_TO_FEMALE, END_MATE
    }

    private State state;
    private BehaviourManager behaviourManager;
    private boolean moveCompleted;
    private ArrayList<Integer> possibleMoves;
    private Position femalePosition;
    private AID female;
    private AnswerMateRequest answerMateRequest;

    public MaleMateManager(AnimalAgent a, BehaviourManager behaviourManager) {

        super(a, Configs.TICK_PERIOD);
        this.state = State.RANDOM_MOVE;
        this.behaviourManager = behaviourManager;
        this.moveCompleted = false;
        this.possibleMoves = new ArrayList<>(Arrays.asList(0, 1, 2, 3));
        this.femalePosition = null;
        this.female = null;
        this.addNextMove();
        this.answerMateRequest = new AnswerMateRequest(a, this);
        a.addBehaviour(answerMateRequest);
    }

    @Override
    protected void onTick() {
        System.out.println("MALE AGENT " + myAgent.getName() + "- MATING");

        AnimalAgent animal = (AnimalAgent) myAgent;
        
        if (animal.getEnergy() < Configs.MIN_ENERGY_MATE) {
            this.behaviourManager.removeSubBehaviour(this);
            this.behaviourManager.updateBehaviour();
            
            if(this.state == State.MOVE_TO_FEMALE)
            informFemaleWithoutEnergy();
            return;
        }
        
        switch (state) {
            case RANDOM_MOVE:
                if (moveCompleted)
                    addNextMove();
                break;
            case MOVE_TO_FEMALE:
                if(reachedFemale())
                    this.setEndMateState(); 
                else
                    addNextToFemaleMove();
                break;
            case END_MATE:
                this.mate();
                animal.removeMateColor();
                this.behaviourManager.removeSubBehaviour(this);
                this.behaviourManager.updateBehaviour();
                break;
        }
    }

    public boolean reachedFemale() {
        AnimalAgent agent = (AnimalAgent)myAgent;
        Position currentPosition = agent.getPosition();
        return currentPosition.equals(femalePosition);
    }

    public void mate() {
        
        ACLMessage terminateMsg = new ACLMessage(ACLMessage.INFORM);
        terminateMsg.setOntology(Communication.Ontology.PREDATOR_REACHED_FEMALE);
        terminateMsg.addReceiver(female);
        this.myAgent.send(terminateMsg);
    }

    public void setEndMateState() {

        this.state = State.END_MATE;
    }

    public void setMoveToFemaleState(AID female, Position femalePosition) {
        
        this.myAgent.removeBehaviour(answerMateRequest);
        this.female = female;
        this.femalePosition = femalePosition;
        this.moveCompleted = true;
        this.state = State.MOVE_TO_FEMALE;
    }

    public void setMoveCompleted(ArrayList<Integer> nextPossibleMoves) {
        this.moveCompleted = true;
        this.possibleMoves = nextPossibleMoves;
    }

    public void addNextToFemaleMove() {
        moveCompleted = false;
        MoveToGoal toGoalMove = new MoveToGoal(this, myAgent, femalePosition);
        this.behaviourManager.addSubBehaviour(toGoalMove);
    }

    private void informFemaleWithoutEnergy() {
        AnimalAgent animal = (AnimalAgent)myAgent;

        animal.removeMateColor();
        ACLMessage terminateMsg = new ACLMessage(ACLMessage.INFORM);
        terminateMsg.setOntology(Communication.Ontology.TERMINATE);
        terminateMsg.addReceiver(female);
        this.myAgent.send(terminateMsg);
    }

    @Override
    public void addNextMove() {
        
        moveCompleted = false;
        Move randomMoveBehaviour = new Move(this, myAgent, possibleMoves);
        this.behaviourManager.addSubBehaviour(randomMoveBehaviour);
    }

    @Override
    public void removeSubBehaviour(Behaviour b) {

        this.behaviourManager.removeSubBehaviour(b);
    }

    @Override
    public void addSubBehaviour(Behaviour b) {

        this.behaviourManager.addSubBehaviour(b);
    }
}
