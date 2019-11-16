package behaviours.plant;

import agents.ObserverAgent;
import elements.Plant;
import utils.Configs;
import utils.Position;
import sajas.core.Agent;
import sajas.core.behaviours.TickerBehaviour;

public class GeneratePlant extends TickerBehaviour {

    public GeneratePlant(Agent agent) {
        super(agent, Configs.TICK_PERIOD);
    }

    @Override
    protected void onTick() {

        ObserverAgent observer = (ObserverAgent) this.myAgent;

        Position position = observer.generateNewPosition();
        if(position != null){
            observer.addPlant(Plant.generatePlant(observer.getModel(), "plant-1", position));
        }
       
    }
    
}