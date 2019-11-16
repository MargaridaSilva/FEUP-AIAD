package behaviours.animals.eat;

import agents.AnimalAgent;
import sajas.core.behaviours.TickerBehaviour;
import utils.Configs;
import utils.Position;

public class EatManager extends TickerBehaviour {
    Position food;

    public EatManager(AnimalAgent a) {
        super(a, Configs.TICK_PERIOD);
    }

    @Override
    protected void onTick() {
        // Find Food

        // Choose move according to goal

        // Validate move

        // Eat

    }
}
