package behaviours;

import sajas.core.behaviours.TickerBehaviour;

import java.util.ArrayList;
import java.util.Arrays;
import agents.AnimalAgent;
import sajas.core.Agent;
import utils.Configs;
import utils.Position;

public class MoveTowardsFemale extends TickerBehaviour {

    private BehaviourManager parentBehaviour;
    private Position finalPosition;

    public MoveTowardsFemale(Agent agent, BehaviourManager parentBehaviour, Position finalPosition) {
        super(agent, Configs.TICK_PERIOD);
        this.parentBehaviour = parentBehaviour;
        this.finalPosition = finalPosition;
    }

    @Override   
    protected void onTick() {
        
        if(((AnimalAgent)this.myAgent).getPosition() == this.finalPosition)
            this.stop();

        ArrayList<Integer> possibleMoves = new ArrayList<>(Arrays.asList(0,1,2,3));
        ChooseNextMove chooseNextMove = new ChooseNextMove(this.myAgent, this.parentBehaviour, possibleMoves, this.finalPosition);
        parentBehaviour.addSubBehaviour(chooseNextMove);
    }
}