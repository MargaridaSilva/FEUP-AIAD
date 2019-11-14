package behaviours.random;

import agents.AnimalAgent;
import sajas.core.behaviours.TickerBehaviour;
import utils.Configs;
import utils.Position;

public class RandomManager extends TickerBehaviour {
    Position mate;

    public RandomManager(AnimalAgent a) {
        super(a, Configs.TICK_PERIOD);
    }

    @Override
    protected void onTick() {
        
    }
}
