package behaviours.die;

import agents.AnimalAgent;
import sajas.core.behaviours.SimpleBehaviour;

public class DieManager extends SimpleBehaviour {

    private boolean done;
 
    public DieManager(AnimalAgent a) {
        super(a);
        
        done = false;
    }

    @Override
    public void action() {
        
        System.out.println("Agent " + myAgent.getAID() + " is going to terminate");
        this.myAgent.doDelete();
        done = true;
    }

    @Override
    public boolean done() {
        
        return done;
    }
}
