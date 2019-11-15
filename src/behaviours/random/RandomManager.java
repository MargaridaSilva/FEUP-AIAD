package behaviours.random;

import agents.AnimalAgent;
import behaviours.BehaviourManager;
import behaviours.Move;
import sajas.core.behaviours.TickerBehaviour;
import utils.Configs;

public class RandomManager extends TickerBehaviour {

    private BehaviourManager parentBehaviour;

    public RandomManager(AnimalAgent a, BehaviourManager parentBehaviour) {
        super(a, Configs.TICK_PERIOD);
        this.parentBehaviour = parentBehaviour;
    }

    @Override
    protected void onTick() {
        
        Move randomMoveBehaviour = new Move(myAgent);
        this.parentBehaviour.addSubBehaviour(randomMoveBehaviour);
    }
}
