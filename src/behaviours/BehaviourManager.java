package behaviours;

import agents.AnimalAgent;
import sajas.core.Agent;
import sajas.core.behaviours.ParallelBehaviour;

public class BehaviourManager {

    AnimalAgent agent;

    public BehaviourManager(Agent agent) {
        this.agent = (AnimalAgent) agent;
    }

}