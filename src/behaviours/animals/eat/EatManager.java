package behaviours.animals.eat;

import agents.AnimalAgent;

import behaviours.animals.BehaviourManager;
import sajas.core.behaviours.SequentialBehaviour;
import sajas.core.behaviours.TickerBehaviour;
import utils.Configs;
import utils.Position;

public class EatManager extends TickerBehaviour {

    Position food;
    BehaviourManager parentBehaviour;

    public EatManager(AnimalAgent a, BehaviourManager parentBehaviour) {
        super(a, Configs.TICK_PERIOD);
        this.parentBehaviour = parentBehaviour;
    }

    public void setFood(Position food) {
        this.food = food;
    }

    @Override
    protected void onTick() {

        SequentialBehaviour eat = new SequentialBehaviour(myAgent);

        // Find Food
        eat.addSubBehaviour(new FindFood(myAgent, FindFood.prepareRequest(myAgent), this));

        // Move Towards Food
        //eat.addSubBehaviour(new MoveToGoal(myAgent, food));

        // Eat

        this.parentBehaviour.addSubBehaviour(eat);

    }
}
