package behaviours;

import java.util.ArrayList;
import java.util.Arrays;

import sajas.core.Agent;
import sajas.core.behaviours.TickerBehaviour;

public class Move extends TickerBehaviour {

    private Navigate parentBehaviour;

    public Move(Navigate parentBehaviour, Agent agent, long period) {
        super(agent, period);
        this.parentBehaviour = parentBehaviour;
    }
    @Override   
    protected void onTick() {
        
        ArrayList<Integer> possibleMoves = new ArrayList<>(Arrays.asList(0,1,2,3));
        ChooseNextMove chooseNextMove = new ChooseNextMove(this.parentBehaviour, this.myAgent, possibleMoves);
        parentBehaviour.addSubBehaviour(chooseNextMove);
    }

    // TODO: restringir movimento apenas às dimensões do board
    // TODO: garantir que dois agentes não colidem
}