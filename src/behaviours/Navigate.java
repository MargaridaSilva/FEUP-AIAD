package behaviours;

import sajas.core.Agent;
import sajas.core.behaviours.ParallelBehaviour;

public class Navigate extends ParallelBehaviour {

    public Navigate(Agent agent, long period) {
        super(agent, WHEN_ALL);

        Move moveBehaviour = new Move(agent, this, period);
        
        this.addSubBehaviour(moveBehaviour);
    }
    
}