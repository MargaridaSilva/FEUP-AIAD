package behaviours.animals;

import agents.AnimalAgent;
import sajas.core.behaviours.TickerBehaviour;
import utils.Configs;

public class Live extends TickerBehaviour {

    BehaviourManager behaviourManager;

    public Live(AnimalAgent a, BehaviourManager behaviourManager) {
        super(a, Configs.TICK_PERIOD);
        this.behaviourManager = behaviourManager;
    }

    @Override
    protected void onTick() {
        AnimalAgent animal = (AnimalAgent) myAgent;
        animal.decreaseEnergy(Configs.LIVING_ENERGY);
    }
}
