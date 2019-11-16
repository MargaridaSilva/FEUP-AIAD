package agents;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;

import behaviours.MoveApproval;
import behaviours.plant.GeneratePlant;
import elements.Plant;
import jade.core.AID;
import launchers.EnvironmentLauncher;
import sajas.core.behaviours.ParallelBehaviour;
import utils.Communication;
import utils.Position;

/**
 * Each world has an Observer agent which keeps track of the world's state,
 * namely the disposal of the agents in the grid. The Observer agent is not part
 * of the world, it just exchanges messages with the other agents, which can
 * request information regarding the state of the world around them.
 */
public class ObserverAgent extends GenericAgent {

    private final int BOARD_DIM;
    private HashMap<Position, AID> agentsPositions;
    private HashMap<AID, Position> preysPositions;
    private HashSet<Position> plantsPosition;

    public ObserverAgent(EnvironmentLauncher model) {
        super(model);
        this.BOARD_DIM = model.getBoardDim();
        this.agentsPositions = new HashMap<>();
        this.preysPositions = new HashMap<>();
        this.plantsPosition = new HashSet<>();
    }

    public void addAgent(AnimalAgent agent) {

        this.agentsPositions.put(agent.getPosition(), agent.getAID());
    }

    public void addPlant(Plant plant){
        this.plantsPosition.add(plant.getPosition());
    }

    public Position generateNewPosition(){
        Position position = new Position(0, 0);
        Random random = new Random();
        int nAttempts = 100;
        boolean invalidPosition = true;

        while(nAttempts > 0 && invalidPosition){
            position.x = random.nextInt(BOARD_DIM);
            position.y = random.nextInt(BOARD_DIM);
            invalidPosition = isPositionTaken(position) || plantsPosition.contains(position);
            nAttempts--;
        }
        
        if(nAttempts == 0){
            position = null;
        }

        return position;
    }

    public boolean isPositionTaken(Position position) {
        
        return this.agentsPositions.containsKey(position);
    }

    public boolean isPositionOutLimits(Position position) {

        boolean outX = (position.x < 0) || (position.x >= BOARD_DIM);
        boolean outY = (position.y < 0) || (position.y >= BOARD_DIM);
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
    protected void setup() {
        super.setup();

        this.registerService(Communication.ServiceType.INFORM_WORLD, 
                             Communication.ServiceName.TRACK_WORLD, new String[]{Communication.Language.MOVE},
                             new String[]{Communication.Ontology.VALIDATE_MOVE});
        
        System.out.println("Observer-agent "+ getAID().getName()+" is ready.");

        // ParallelBehaviour behaviour = new ParallelBehaviour();
        // behaviour.addSubBehaviour((new MoveApproval(this));
        this.addBehaviour(new MoveApproval(this));
        this.addBehaviour(new GeneratePlant(this));
    }

    @Override
    protected void takeDown() {
        super.takeDown();
        
        this.deRegisterServices();

        System.out.println("Observer-agent " + this.getAID() + " terminating");
    }



}