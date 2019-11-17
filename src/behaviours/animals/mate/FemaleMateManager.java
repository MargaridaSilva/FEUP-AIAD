package behaviours.animals.mate;

import java.util.ArrayList;
import java.util.Arrays;

import agents.AnimalAgent;
import behaviours.animals.BehaviourManager;
import behaviours.animals.move.Move;
import behaviours.animals.move.MoveManager;
import behaviours.animals.move.MoveToGoal;
import sajas.core.behaviours.Behaviour;
import sajas.core.behaviours.TickerBehaviour;
import utils.Configs;
import utils.Position;

public class FemaleMateManager extends TickerBehaviour implements MoveManager {

    private static enum State {
        RANDOM_MOVE_CFP, WAIT_MALE, MATE, END_MATE
    }

    private State state;
    private BehaviourManager behaviourManager;
    private boolean moveCompleted;
    private ArrayList<Integer> possibleMoves;

    public FemaleMateManager(AnimalAgent a, BehaviourManager behaviourManager) {

        super(a, Configs.TICK_PERIOD);
        this.state = State.RANDOM_MOVE_CFP;
        System.out.println("new female mate manaegr");
        this.behaviourManager = behaviourManager;
        this.setRandomMoveCFPState();

    }

    @Override
    protected void onTick() {

        System.out.println("--> Female: " + this.state);

        AnimalAgent animal = (AnimalAgent) myAgent;
        /*
        if (animal.getEnergy() < Configs.MIN_ENERGY_MATE) {
            this.behaviourManager.removeSubBehaviour(this);
            this.behaviourManager.updateBehaviour();
            return;
        }
*/
        switch (state) {
            case RANDOM_MOVE_CFP:
                if (moveCompleted)
                    addNextMove();
                break;
            case WAIT_MALE:
                break;
            case MATE:
                mate();
                break;
            case END_MATE:
                this.behaviourManager.removeSubBehaviour(this);
                this.behaviourManager.updateBehaviour();
                break;
        }
    }

    public void mate() {
        
        // TODO
    }

    public void setRandomMoveCFPState() {

        this.state = State.RANDOM_MOVE_CFP;
        this.launchCFP();
    }

    public void setMateState() {

        this.state = State.MATE;
    }

    public void setWaitMaleState() {
        
        this.state = State.WAIT_MALE;
        WaitMale waitMale = new WaitMale(myAgent, this);
        this.addSubBehaviour(waitMale);
    }

    public void setMoveCompleted(ArrayList<Integer> nextPossibleMoves) {

        this.moveCompleted = true;
        this.possibleMoves = nextPossibleMoves;
    }

    @Override
    public void addNextMove() {
        
        moveCompleted = false;
        Move randomMoveBehaviour = new Move(this, myAgent, possibleMoves);
        this.addSubBehaviour(randomMoveBehaviour);
    }

    public void launchCFP() {

        this.moveCompleted = false;
        this.possibleMoves = new ArrayList<>(Arrays.asList(0, 1, 2, 3));
        CallToMate callToMate = new CallToMate(myAgent, this);
        this.addSubBehaviour(callToMate);
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
