package behaviours;

import java.util.Random;

import agents.AnimalAgent;
import sajas.core.Agent;
import sajas.core.behaviours.TickerBehaviour;

public class Navigate extends TickerBehaviour {

    private Random random;
    private final int[][] MOVES = {{1,0}, {-1,0}, {0,1}, {0,-1}};

    public Navigate(Agent a, long period) {
        super(a, period);
        this.random = new Random(System.currentTimeMillis());
    }

    @Override   
    protected void onTick() {
        
        AnimalAgent agent = (AnimalAgent) this.myAgent;
        int[] move = this.MOVES[random.nextInt(4)];
        agent.setX(agent.getX() + agent.getModel().DENSITY * move[0]);
        agent.setY(agent.getY() + agent.getModel().DENSITY * move[1]);
        
        agent.node.setX(agent.getX());
        agent.node.setY(agent.getY());
    }
    

    // TODO: restringir movimento apenas às dimensões do board
    // TODO: garantir que dois agentes não colidem
}