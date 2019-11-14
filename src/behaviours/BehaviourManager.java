package behaviours;

import agents.AnimalAgent;
import behaviours.die.DieManager;
import behaviours.eat.EatManager;
import behaviours.mate.MateManager;
import behaviours.random.RandomManager;
import sajas.core.behaviours.Behaviour;
import sajas.core.behaviours.ParallelBehaviour;
import utils.Configs;

public class BehaviourManager extends ParallelBehaviour {

    AnimalAgent agent;

    public BehaviourManager(AnimalAgent agent) {
        super(agent, WHEN_ALL);
        this.agent = (AnimalAgent) agent;
        this.updateBehaviour();
    }

    public void updateBehaviour() {

        double energy = agent.getEnergy();
        Behaviour nextBehaviour = null;

        if(energy >= Configs.MIN_ENERGY_MATE)
            nextBehaviour = new MateManager(agent);
        else if(energy >= Configs.MIN_ENERGY_RANDOM)
            nextBehaviour = new RandomManager(agent);
        else if(energy > 0)
            nextBehaviour = new EatManager(agent);
        else    
            nextBehaviour = new DieManager(agent);

        this.addSubBehaviour(nextBehaviour);         
    }

}