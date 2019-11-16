package behaviours.animals.eat;

import agents.AnimalAgent;

import behaviours.animals.BehaviourManager;
import behaviours.animals.move.MoveToGoal;
import sajas.core.behaviours.TickerBehaviour;
import utils.Configs;
import utils.Position;

public class EatManager extends TickerBehaviour {
    public Position food;
    BehaviourManager parentBehaviour;

    public EatManager(AnimalAgent a, BehaviourManager parentBehaviour) {
        super(a, Configs.TICK_PERIOD);
        this.parentBehaviour = parentBehaviour;
    }

    @Override
    protected void onTick() {
        // Find Food
        parentBehaviour.addSubBehaviour(new FindFood(myAgent, FindFood.prepareRequest(myAgent), this));

        // Move Towards Food
        parentBehaviour.addSubBehaviour(new MoveToGoal(myAgent, food));

        // Validate move

        // Eat

    }
}
