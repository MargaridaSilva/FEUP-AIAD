package behaviours;

import sajas.core.Agent;
import sajas.core.behaviours.ParallelBehaviour;

public class Navigate extends ParallelBehaviour {

    Move moveBehaviour;

    public Navigate(Agent agent) {
        super(agent, WHEN_ALL);

        this.moveBehaviour = new Move(agent, this);
        
        this.addSubBehaviour(moveBehaviour);
    }
    
    public void stayStill() {
        this.removeSubBehaviour(this.moveBehaviour);
    }
}