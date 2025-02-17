package behaviours.plant;

import java.util.Random;

import agents.ObserverAgent;
import elements.Plant;
import jade.wrapper.StaleProxyException;
import utils.Configs;
import utils.Position;
import sajas.core.Agent;
import sajas.core.behaviours.TickerBehaviour;

public class GeneratePlant extends TickerBehaviour {

    private Random random;

    public GeneratePlant(Agent agent) {
        super(agent, Configs.TICK_PERIOD);
        this.random = new Random();
    }

    @Override
    protected void onTick() {

        if (random.nextFloat() < 0.999) {
            return;
        }

        ObserverAgent observer = (ObserverAgent) this.myAgent;
        Position position = observer.generateNewPosition();
        if (position != null) {
            try {
                observer.getModel().addPlant(position);
            } catch (StaleProxyException e) {
                e.printStackTrace();
            }

        }

    }
}