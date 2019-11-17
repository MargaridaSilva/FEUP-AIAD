package behaviours.animals;

import agents.AnimalAgent;
import agents.PredatorAgent;
import agents.AnimalAgent.Gender;
import behaviours.animals.die.DieManager;
import behaviours.animals.eat.EatManager;
import behaviours.animals.eat.WaitPredator;
import behaviours.animals.mate.FemaleMateManager;
import behaviours.animals.mate.MaleMateManager;
import behaviours.animals.move.RandomManager;
import sajas.core.behaviours.Behaviour;
import sajas.core.behaviours.ParallelBehaviour;
import utils.Configs;

public class BehaviourManager extends ParallelBehaviour {

    private AnimalAgent agent;

    public BehaviourManager(AnimalAgent agent) {
        super(agent, WHEN_ALL);
        this.agent = (AnimalAgent) agent;
        this.addDefaultBehaviours();
        this.updateBehaviour();
    }

    private void addDefaultBehaviours() {

        if(agent instanceof PredatorAgent)
            this.addPredatorDefaultBehaviours();
        else
            this.addPreyDefaultBehaviours(); 
    }

    private void addPredatorDefaultBehaviours() {

    }

    private void addPreyDefaultBehaviours() {
        agent.addBehaviour(new WaitPredator(agent));
    }

    public void updateBehaviour() {

        double energy = agent.getEnergy();
        
        Behaviour nextBehaviour = null;
                
        if(energy >= Configs.MIN_ENERGY_MATE)
            if(this.agent.getGender() == Gender.FEMALE)
                nextBehaviour = new FemaleMateManager(agent, this);
            else 
                nextBehaviour = new MaleMateManager(agent, this);
        else if(energy >= Configs.MIN_ENERGY_RANDOM)
            nextBehaviour = new RandomManager(agent, this);
        else if(energy > 0)
            nextBehaviour = new EatManager(agent, this);
        else    
            nextBehaviour = new DieManager(agent);
        
        this.addSubBehaviour(nextBehaviour);         
    }
}