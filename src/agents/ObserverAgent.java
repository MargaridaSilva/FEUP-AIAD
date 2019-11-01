package agents;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import behaviours.MoveApproval;
import jade.core.AID;
import jade.domain.FIPAException;
import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.FailureException;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import sajas.proto.ProposeResponder;
import jade.tools.sniffer.Message;
import sajas.proto.ContractNetResponder;
import launchers.EnvironmentLauncher;
import sajas.core.Agent;
import sajas.domain.DFService;

import utils.Position;

/**
 * Each world has an Observer agent which keeps track of the world's state,
 * namely the disposal of the agents in the grid. The Observer agent is not part
 * of the world, it just exchanges messages with the other agents, which can
 * request information regarding the state of the world around them.
 */
public class ObserverAgent extends GenericAgent {

    private final int BOARD_DIM;
    private final int DENSITY;
    private HashMap<Position, AID> agentsPositions;

    public ObserverAgent(EnvironmentLauncher model) {
        super(model);
        this.BOARD_DIM = model.getBoardDim();
        this.DENSITY = model.getBoardDensity();
        this.agentsPositions = new HashMap<>();
    }

    public void addAgent(AnimalAgent agent) {
        this.agentsPositions.put(agent.getPosition(), agent.getAID());
    }

    public boolean isPositionTaken(Position position) {
        return this.agentsPositions.containsKey(position);
    }

    public boolean isPositionOutLimits(Position position) {

        boolean outX = (position.x < 0) || (position.x > BOARD_DIM);
        boolean outY = (position.y < 0) || (position.y > BOARD_DIM);
        return outX || outY;
    }

    public void updateAgentPosition(AID agentAID, Position position) {
        if(!this.agentsPositions.containsValue(agentAID)) return;
        Iterator<Map.Entry<Position,AID>> it = this.agentsPositions.entrySet().iterator();
        Position key = null;
        while (it.hasNext()) {
            Map.Entry<Position, AID> agentPositionPair = (Map.Entry<Position, AID>)it.next();
            if(agentPositionPair.getValue().equals(agentAID)) {
                key = (Position)agentPositionPair.getKey();
                break;
            }
        }
        this.agentsPositions.remove(key);
        this.agentsPositions.put(position, agentAID);
    }

    @Override
    protected void setup() {
        super.setup();

        this.registerService("inform-world", "track-world", new String[]{"move-language"}, new String[]{"validate-move-ontology"});
        
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