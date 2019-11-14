package behaviours.eat;

import agents.AnimalAgent;
import sajas.core.behaviours.TickerBehaviour;
import utils.Position;

public class EatManager extends TickerBehaviour {
    Position food;

    public EatManager(AnimalAgent a, long period) {
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
