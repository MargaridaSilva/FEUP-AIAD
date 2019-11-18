package behaviours.animals.die;

import agents.AnimalAgent;
import jade.core.AID;
import sajas.core.behaviours.SimpleBehaviour;
import utils.Locator;

public class DieManager extends SimpleBehaviour {

    private boolean done;
 
    public DieManager(AnimalAgent a) {
        super(a);
        
        done = false;
    }

    @Override
    public void action() {

        this.myAgent.doDelete();
        done = true;

    }

    @Override
    public boolean done() {
        
        return done;
    }
}
