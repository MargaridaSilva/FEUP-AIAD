package behaviours.animals.move;

import java.util.ArrayList;
import java.util.Arrays;

import agents.AnimalAgent;
import behaviours.animals.BehaviourManager;
import sajas.core.behaviours.Behaviour;
import sajas.core.behaviours.TickerBehaviour;
import utils.Configs;

public class RandomManager extends TickerBehaviour implements MoveManager {

    private BehaviourManager behaviourManager;
    private boolean moveCompleted;
    private ArrayList<Integer> possibleMoves;

    public RandomManager(AnimalAgent a, BehaviourManager behaviourManager) {
        super(a, Configs.TICK_PERIOD);
        this.behaviourManager = behaviourManager;
        this.moveCompleted = false;
        this.possibleMoves = new ArrayList<>(Arrays.asList(0,1,2,3));
        this.addNextMove();
    }

    @Override
    protected void onTick() {
        System.out.println("AGENT " + myAgent.getName() + "- CHILLING");
        AnimalAgent animal = (AnimalAgent)myAgent;

        if(animal.getEnergy() < Configs.MIN_ENERGY_RANDOM) {
            this.behaviourManager.removeSubBehaviour(this);
            this.behaviourManager.updateBehaviour();
        }
        else if(moveCompleted)
            this.addNextMove();

    }

    public void addNextMove() {
        
        moveCompleted = false;
        Move randomMoveBehaviour = new Move(this, myAgent, possibleMoves);
        this.behaviourManager.addSubBehaviour(randomMoveBehaviour);
    }

    public void setMoveCompleted(ArrayList<Integer> nextPossibleMoves) {

        this.moveCompleted = true;
        this.possibleMoves = nextPossibleMoves;
    }
    
    public void removeSubBehaviour(Behaviour b) {
        this.behaviourManager.removeSubBehaviour(b);
    }

    public void addSubBehaviour(Behaviour b) {
        this.behaviourManager.addSubBehaviour(b);
    }
}
