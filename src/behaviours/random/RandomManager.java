package behaviours.random;

import agents.AnimalAgent;
import sajas.core.behaviours.TickerBehaviour;
import utils.Position;

public class RandomManager extends TickerBehaviour {
    Position mate;

    public RandomManager(AnimalAgent a, long period) {
        super(a, period);
    }

    @Override
    protected void onTick() {

    }
}
