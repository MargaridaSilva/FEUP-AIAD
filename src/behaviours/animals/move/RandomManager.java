package behaviours.animals.move;

import java.util.ArrayList;
import java.util.Arrays;

import agents.AnimalAgent;
import behaviours.animals.BehaviourManager;
import jade.util.leap.Collection;
import sajas.core.behaviours.Behaviour;
import sajas.core.behaviours.TickerBehaviour;
import utils.Configs;

public class RandomManager extends TickerBehaviour {

    private Move randomMoveBehaviour;
    private BehaviourManager parentBehaviour;
    private boolean moveCompleted;
    private ArrayList<Integer> possibleMoves;

    public RandomManager(AnimalAgent a, BehaviourManager parentBehaviour) {
        super(a, Configs.TICK_PERIOD);
        this.parentBehaviour = parentBehaviour;
        this.moveCompleted = false;
        this.possibleMoves = new ArrayList<>(Arrays.asList(0,1,2,3));
        this.addNextMove();
    }

    @Override
    protected void onTick() {

        AnimalAgent animal = (AnimalAgent)myAgent;

        if(animal.getEnergy() < Configs.MIN_ENERGY_RANDOM) {
            this.parentBehaviour.removeSubBehaviour(this);
            this.parentBehaviour.updateBehaviour();
        }
        else if(moveCompleted)
            this.addNextMove();

    }

    public void addNextMove() {
        
        moveCompleted = false;
        randomMoveBehaviour = new Move(this, myAgent, possibleMoves);
        this.parentBehaviour.addSubBehaviour(randomMoveBehaviour);
    }

    public void setMoveCompleted(ArrayList<Integer> nextPossibleMoves) {
        
        this.moveCompleted = true;
        this.possibleMoves = nextPossibleMoves;
    }
    
    
    public void removeSubBehaviour(Behaviour b) {
        this.parentBehaviour.removeSubBehaviour(b);
    }

    public void addSubBehaviour(Behaviour b) {
        this.parentBehaviour.addSubBehaviour(b);
    }
}
