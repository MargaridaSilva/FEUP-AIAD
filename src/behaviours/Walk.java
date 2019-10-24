package behaviours;

import agents.AnimalAgent;
import sajas.core.Agent;
import sajas.core.behaviours.TickerBehaviour;

public class Walk extends TickerBehaviour {

    public Walk(Agent a, long period) {
        super(a, period);
    }

    @Override
    protected void onTick() {
        AnimalAgent agent = (AnimalAgent) myAgent;
        agent.setX(agent.getX()+agent.model.DENSITY);
        agent.node.setX(agent.getX());
    }

}
