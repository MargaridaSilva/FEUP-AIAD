package behaviours;

import agents.AnimalAgent;
import behaviours.eat.EatManager;
import behaviours.mate.MateManager;
import behaviours.mate.MateProposal;
import behaviours.random.RandomManager;
import sajas.core.Agent;
import sajas.core.behaviours.ParallelBehaviour;

public class BehaviourManager {

    AnimalAgent agent;
    long period = 1000;

    public BehaviourManager(AnimalAgent agent) {
        this.agent = (AnimalAgent) agent;
        this.updateBehaviour();
    }

    public void updateBehaviour(){
        float energy = agent.getEnergy();
        if (energy < 0.5)
            agent.addBehaviour(new EatManager(agent, period));
        else if (energy < 0.7)
            agent.addBehaviour(new RandomManager(agent, period));
        else
            agent.addBehaviour(new MateManager(agent, period));
    }

}