package behaviours.eat;

import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import utils.Position;

public class EatManager extends TickerBehaviour {
    Position food;

    public EatManager(Agent a, long period) {
        super(a, period);
    }

    @Override
    protected void onTick() {
        // Find Food

        // Choose move according to goal

        // Validate move

        // Eat

    }
}
