package behaviours.animals.mate;

import agents.AnimalAgent;
import sajas.core.behaviours.TickerBehaviour;
import utils.Configs;
import utils.Position;

public class MateManager extends TickerBehaviour {
    Position food;

    public MateManager(AnimalAgent a) {
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
