package agents;

import java.util.HashMap;
import java.util.Map;

import behaviours.MoveApproval;
import jade.core.AID;
import simulation.PredatorPreyModel;
import utils.Communication;
import utils.Position;

/**
 * Each world has an Observer agent which keeps track of the world's state,
 * namely the disposal of the agents in the grid. The Observer agent is not part
 * of the world, it just exchanges messages with the other agents, which can
 * request information regarding the state of the world around them.
 */
public final class ObserverAgent extends GenericAgent {

    private final int width;
    private final int height;
    private HashMap<Position, AID> agentsPositions;
    private HashMap<AID, Position> preysPositions;

    public ObserverAgent(PredatorPreyModel model) {
        super(model);
        this.width = model.getWidth();
        this.height = model.getHeight();
        this.agentsPositions = new HashMap<>();
        this.preysPositions = new HashMap<>();
    }

    public void addAgent(AnimalAgent agent) {

        this.agentsPositions.put(agent.getPosition(), agent.getAID());
    }

    public boolean isPositionTaken(Position position) {
        
        return this.agentsPositions.containsKey(position);
    }

    public boolean isPositionOutLimits(Position position) {

        boolean outX = (position.x < 0) || (position.x >= width);
        boolean outY = (position.y < 0) || (position.y >= height);
        return outX || outY;
    }

    public void updateAgentPosition(AID agentAID, Position position) {
        
        if(!this.agentsPositions.containsValue(agentAID)) return;
        
        Position key = null;
        for (Map.Entry<Position, AID> mapPosition : agentsPositions.entrySet()) {
            
            if(mapPosition.getValue().equals(agentAID)) {
                key = mapPosition.getKey();
                break;
            }
        }
        this.agentsPositions.remove(key);
        this.agentsPositions.put(position, agentAID);
    }

    @Override
    public void setup() {
        super.setup();

        this.registerService(Communication.ServiceType.INFORM_WORLD, 
                             Communication.ServiceName.TRACK_WORLD, 
                             new String[]{Communication.Language.MOVE},
                             new String[]{Communication.Ontology.VALIDATE_MOVE});
        
        System.out.println("Observer-agent "+ getAID().getName()+" is ready.");

        this.addBehaviour(new MoveApproval(this));
    }

    @Override
    protected void takeDown() {
        
        super.takeDown();
        
        this.deRegisterServices();

        System.out.println("Observer-agent " + this.getAID() + " terminating");
    }
}