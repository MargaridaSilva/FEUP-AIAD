package agents;

import java.io.Serializable;
import java.util.*;

import behaviours.plant.GeneratePlant;
import elements.Plant;
import jade.core.AID;
import behaviours.observer.MoveApproval;
import behaviours.observer.RemoveAgent;
import behaviours.observer.TellFood;
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
    private HashMap<AID, Position> agentsPositions;
    private HashMap<AID, Position> preysPositions;
    private HashSet<Position> plantsPositions;

    public ObserverAgent(PredatorPreyModel model) {
        super(model);
        this.width = model.getWidth();
        this.height = model.getHeight();
        this.agentsPositions = new HashMap<>();
        this.preysPositions = new HashMap<>();
        this.plantsPositions = new HashSet<>();
    }

    public void addAgent(AnimalAgent agent) {

        this.agentsPositions.put(agent.getAID(), agent.getPosition());
        if (agent instanceof PreyAgent) {
            System.out.println("PREY");
            this.preysPositions.put(agent.getAID(), agent.getPosition());
        }
    }

    public void addPlant(Plant plant){
        this.plantsPositions.add(plant.getPosition());
        this.model.addElement(plant);
    }

    public void removePlant(Plant plant){
        this.plantsPositions.add(plant.getPosition());
        this.model.addElement(plant);
    }

    public Boolean isPrey(AID agent) {
        return this.preysPositions.containsKey(agent);
    }

    public Position generateNewPosition(){
        Position position = new Position(0, 0);
        Random random = new Random();
        int nAttempts = 10;
        boolean invalidPosition = true;

        while(nAttempts > 0 && invalidPosition){
            position.x = random.nextInt(this.getModel().getWidth());
            position.y = random.nextInt(this.getModel().getHeight());
            invalidPosition = isPositionTaken(position) || plantsPositions.contains(position);
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

    public AID getAID(Position agentPosition){
        Iterator<Map.Entry<AID, Position>>
                iterator = this.agentsPositions.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<AID, Position> entry = iterator.next();

            if (agentPosition.equals(entry.getValue())) {
                return entry.getKey();
            }
        }

        return null;
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

    public void updateIfPrey(AID agentId, Position position) {

        if (this.preysPositions.containsKey(agentId))
            this.preysPositions.put(agentId, position);
    }

    public Position getAgentPosition(AID agentId) {
        return this.agentsPositions.get(agentId);
    }

    @Override
    public void setup() {
        super.setup();

        this.registerService(Communication.ServiceType.INFORM_WORLD, 
                             Communication.ServiceName.TRACK_WORLD,
                            new String[]{Communication.Language.MOVE, Communication.Language.FOOD},
                             new String[]{Communication.Ontology.VALIDATE_MOVE, Communication.Ontology.TELL_FOOD});
        
        System.out.println("Observer-agent "+ getAID().getName()+" is ready.");

        this.addBehaviour(new MoveApproval(this));
        this.addBehaviour(new GeneratePlant(this));
        this.addBehaviour(new RemoveAgent(this));
        this.addBehaviour(new TellFood(this));
    }

    @Override
    protected void takeDown() {
        
        super.takeDown();
        
        this.deRegisterServices();

        System.out.println("Observer-agent " + this.getAID() + " terminating");
    }

    public HashSet<Position> getPreys() {
        return new HashSet<>(this.preysPositions.values());
    }

    public HashSet<Position> getPlants() {
        return this.plantsPositions;
    }
}