package behaviours.animals.move;

import agents.AnimalAgent;
import behaviours.animals.BehaviourManager;
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

        updateState();
    }

    private void updateState() {
        
        AnimalAgent animal = (AnimalAgent)myAgent;

        if(animal.getEnergy() < Configs.MIN_ENERGY_RANDOM) {
            this.parentBehaviour.removeSubBehaviour(this);
            this.parentBehaviour.updateBehaviour();
        }
    }
}
