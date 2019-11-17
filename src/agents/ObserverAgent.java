package agents;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;

import agents.AnimalAgent.Gender;
import behaviours.plant.GeneratePlant;
import elements.Plant;
import jade.core.AID;
import jade.wrapper.StaleProxyException;
import behaviours.observer.AddAgents;
import behaviours.observer.MoveApproval;
import behaviours.observer.RemoveAgent;
import simulation.PredatorPreyModel;
import utils.Communication;
import utils.Position;
import utils.PositionGenerator;
import utils.RandomPositionGenerator;

/**
 * Each world has an Observer agent which keeps track of the world's state,
 * namely the disposal of the agents in the grid. The Observer agent is not part
 * of the world, it just exchanges messages with the other agents, which can
 * request information regarding the state of the world around them.
 */
public final class ObserverAgent extends GenericAgent {

    private final int width;
    private final int height;
    private HashMap<AID, Position> agentsPositions;
    private HashMap<AID, Position> preysPositions;
    private HashSet<Position> plantsPosition;

    public ObserverAgent(PredatorPreyModel model) {
        super(model);
        this.width = model.getWidth();
        this.height = model.getHeight();
        this.agentsPositions = new HashMap<>();
        this.preysPositions = new HashMap<>();
        this.plantsPosition = new HashSet<>();
    }

    public void addAgent(AnimalAgent agent) {

        this.agentsPositions.put(agent.getAID(), agent.getPosition());
    }

    public void addPlant(Plant plant){
        this.plantsPosition.add(plant.getPosition());
        this.model.addElement(plant);
    }

    public Position generateNewPosition(){
        Position position = new Position(0, 0);
        Random random = new Random();
        int nAttempts = 10;
        boolean invalidPosition = true;

        while(nAttempts > 0 && invalidPosition){
            position.x = random.nextInt(this.getModel().getWidth());
            position.y = random.nextInt(this.getModel().getHeight());
            invalidPosition = isPositionTaken(position) || plantsPosition.contains(position);
            nAttempts--;
        }
        
        if(nAttempts == 0){
            position = null;
        }

        return position;
    }

    public void removeAgent(AID agentId) {
        
        this.agentsPositions.remove(agentId);
    }

    public boolean isPositionTaken(Position position) {
        
        return this.agentsPositions.containsValue(position);
    }

    public boolean isPositionOutLimits(Position position) {

        boolean outX = (position.x < 0) || (position.x >= width);
        boolean outY = (position.y < 0) || (position.y >= height);
        return outX || outY;
    }

    public void updateAgentPosition(AID agentId, Position position) {
        
        if(!this.agentsPositions.containsKey(agentId)) 
            return;
        this.agentsPositions.put(agentId, position);
    }

    public Position getAgentPosition(AID agentId) {
        return this.agentsPositions.get(agentId);
    }

    public void addPredators(int numPredators) {
        
        PositionGenerator positionGenerator = new RandomPositionGenerator(width, height);
        Random random = new Random();
        
        for(int i = 0 ; i < numPredators ; i++) {
            int genderIndex = random.nextInt(2); // 0 or 1
            Gender gender = Gender.FEMALE;
            Position position;
            do {
                position = positionGenerator.getPosition();
                if(genderIndex == 0)  gender = Gender.MALE;
            } while(this.agentsPositions.containsValue(position));
            
            try {
                this.model.addPredator(position, gender);
            } catch (StaleProxyException e) {
                e.printStackTrace();
            }
        }
    }
    public void addPreys(int numPreys) {
        
        PositionGenerator positionGenerator = new RandomPositionGenerator(width, height);
        Random random = new Random();
        
        for(int i = 0 ; i < numPreys ; i++) {
            int genderIndex = random.nextInt(2); // 0 or 1
            Gender gender = Gender.FEMALE;
            Position position;
            do {
                position = positionGenerator.getPosition();
                if(genderIndex == 0)  gender = Gender.MALE;
            } while(this.agentsPositions.containsValue(position));
            
            try {
                this.model.addPrey(position, gender);
            } catch (StaleProxyException e) {
                e.printStackTrace();
            }
        }
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
        this.addBehaviour(new AddAgents(this));
        this.addBehaviour(new GeneratePlant(this));
        this.addBehaviour(new RemoveAgent(this));
    }

    @Override
    protected void takeDown() {
        
        super.takeDown();
        
        this.deRegisterServices();

        System.out.println("Observer-agent " + this.getAID() + " terminating");
    }
}