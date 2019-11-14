package behaviours;

import java.util.ArrayList;
import java.util.Arrays;

import behaviours.predator.ManagePredator;
import sajas.core.Agent;
import sajas.core.behaviours.TickerBehaviour;
import utils.Configs;

public class Move extends TickerBehaviour {

    private BehaviourManager parentBehaviour;

    public Move(Agent agent, ManagePredator parentBehaviour) {
        super(agent, Configs.TICK_PERIOD);
        this.parentBehaviour = parentBehaviour;
    }

    @Override   
    protected void onTick() {
        
        ArrayList<Integer> possibleMoves = new ArrayList<>(Arrays.asList(0,1,2,3));
        ChooseNextMove chooseNextMove = new ChooseNextMove(this.myAgent, this.parentBehaviour, possibleMoves);
        parentBehaviour.addSubBehaviour(chooseNextMove);
    }
}